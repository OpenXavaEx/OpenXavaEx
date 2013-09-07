package starter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.jndi.NamingUtil;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;
import org.openxava.ex.datasource.MonitoredDataSourceFactory;
import org.openxava.ex.tools.DynamicLoaderFilter;
import org.openxava.ex.tools.TokenCookieSSOFilter;
import org.openxava.ex.utils.VersionInfo;

/**
 * The jetty server to start development workspace.<br/>
 * Run this application with following environment variables:
 * <pre>
 *  - HTTP_PORT: http port, default is 8080
 *  - CTX_PATH:  context path, default TestApp
 *  - JDBC_URL:  jdbc url
 *  - DB_USER:   username of database
 *  - DB_PASS:   password of database
 * </pre>
 * @author root
 *
 */
public class ContextApp {
    private static final String DEFAULT_HTTP_PORT = "8080";
    private static final String DEFAULT_CTX_PATH = "TestApp";        //In OpenXava, the context path is also the application name

    public static void main(String[] args) throws Exception {
        EnvSettings es = readEnv();
        
        //System properties for logging
        System.setProperty("org.eclipse.jetty.LEVEL", "ALL");
        System.setProperty("org.eclipse.jetty.util.log.SOURCE", "false");
        //JSP Compiler setting
        System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");
        
        //Try to stop the previous server instance
        URL stop = new URL("http://127.0.0.1:" + es.httpPort + "/STOP");
        try{ stop.openStream(); }catch(Exception ex){ /*Ignore it*/}
        
        final Server server = new Server(es.httpPort);
        
        prepareDataSource(server, es);
        //Properties for persistence.xml and hibernate.cfg.xml
        System.setProperty("PROP_DATASOURCE_JNDI_NAME", "java:comp/env/" + es.getJndiName());
        System.setProperty("PROP_HIBERNATE_DIALECT", es.getHibernateDialect());
        System.setProperty("PROP_HIBERNATE_DEFAULT_SCHEMA", es.getDefaultSchema());

        //The ROOT web app
        Handler root = delpoyRootCtx();
        //The "main" web app - for SSO testing
        Handler main = deployMainCtx();
        //The OpenXava web app
        Handler oxApp = deployAppCtx(es);

        //Bind contexts to server
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[] { root, main, oxApp });
        server.setHandler(contexts);

        server.start();
        System.out.println("********************************************************************************");
        System.out.println("Embeded Jetty("+Server.getVersion()+") Server started at port ["+es.httpPort+"].");
        System.out.println("********************************************************************************");
        server.join();
    }

    private static Handler deployMainCtx() throws IOException, URISyntaxException {
        ServletContextHandler main = new ServletContextHandler(ServletContextHandler.SESSIONS);
        main.setContextPath("/main");
        main.setBaseResource(buildFolderResource("jetty-starter/war-app-main"));
        main.addServlet(DefaultServlet.class, "/");    //Default servlet
        //FIXME: org.eclipse.jetty.servlet.ServletHolder.initJspServlet() need it - InitParameter "com.sun.appserv.jsp.classpath"
        main.setClassLoader(ContextApp.class.getClassLoader());
        //JSP Servlet
        main.addServlet(JspServlet.class, "*.jsp");
        //Welcome files
        main.setWelcomeFiles(new String[]{"shell_index.jsp"});
        
        return main;
    }

    @SuppressWarnings("serial")
    private static Handler delpoyRootCtx() throws IOException, URISyntaxException {
        ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
        root.setContextPath("/");
        root.setBaseResource(buildFolderResource("jetty-starter/war-root"));
        root.addServlet(DefaultServlet.class, "/");    //Default servlet
        //FIXME: org.eclipse.jetty.servlet.ServletHolder.initJspServlet() need it - InitParameter "com.sun.appserv.jsp.classpath"
        root.setClassLoader(ContextApp.class.getClassLoader());
        //JSP Servlet
        root.addServlet(JspServlet.class, "*.jsp");
        //The STOP Servlet
        root.addServlet(new ServletHolder(new HttpServlet() {
            @Override
            public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
                System.err.println(">>> Stop server request from /STOP ...");
                System.exit(0);
            }
        }), "/STOP");
        
        return root;
    }

    private static WebAppContext deployAppCtx(EnvSettings es) throws Exception {
        WebAppContext web = new WebAppContext();
        web.setDescriptor(getProjectParent()+"/"+es.ctxPath+"/web/WEB-INF/web.xml");
        //Allow find resource in multi-folder
        Resource res = new ResourceCollection(
                buildFolderResource("jetty-starter/war-patch"),
                buildFolderResource("OpenXavaEx/web"),
                (VersionInfo.isDevVersion()?buildFolderResource("OpenXavaDEV/web"):buildFolderResource("OpenXava/web")),
                buildFolderResource(es.ctxPath + "/web"),
                null
        );
        web.setBaseResource(res);
        web.setContextPath("/" + es.ctxPath);
        web.setParentLoaderPriority(true);
        //Default servlet
        web.addServlet(DefaultServlet.class, "/");
        //JSP servlet
        web.addServlet(JspServlet.class, "*.jsp");
        
        //Development only: Dynamic modify TokenCookieSSOFilter_DO_SSO Filter's init-param to match current http port
        System.setProperty(
        		TokenCookieSSOFilter.TOKEN_CHECK_URL_SYSTEM_PROPERTY,
        		"http://localhost:"+es.httpPort+"/main/bridge.jsp?token=");
        
        //Development only: Dynamic class load filter
        FilterHolder clFh = new FilterHolder(DynamicLoaderFilter.class);
        clFh.setInitParameter(DynamicLoaderFilter.INIT_PARAM_NAME_CLASSPATH, getAppClassPathList(es.ctxPath));
        clFh.setInitParameter(DynamicLoaderFilter.INIT_PARAM_NAME_RELOAD_URI_LIST, "/modules/;/schema-update/");
        web.addFilter(clFh, "*.jsp", FilterMapping.REQUEST);
        web.addFilter(clFh, "/modules/*", FilterMapping.REQUEST);
        web.addFilter(clFh, "/dwr/*", FilterMapping.REQUEST);
        web.addFilter(clFh, "/schema-update/*", FilterMapping.REQUEST);
        
        return web;
    }

    /**
     * Get the parent folder path of current project, based the directory structure
     * @return
     */
    private static final String getProjectParent() {
        URL binUrl = ContextApp.class.getResource("/");
        String bin = binUrl.getFile();
        String parent = (new File(bin)).getParent();
        parent = (new File(parent)).getParent();
        return parent;
    }
    private static final String getAppClassPathList(String... appNames){
        StringBuffer buf = new StringBuffer();
        String parent = getProjectParent();
        for(int i=0; i<appNames.length; i++){
            String app = appNames[i];
            if (i>0) buf.append(";");
            //FIXME: Now you can only complie App's class into it's /web/WEB-INF/classes folder
            buf.append(parent + "/" + app + "/web/WEB-INF/classes");
        }
        return buf.toString();
    }
    /**
     * Get the resource of specified web content folder
     * @param warFolderName
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private static final Resource buildFolderResource(String warFolderName) throws IOException, URISyntaxException {
        String parent = getProjectParent();
        String war = parent + "/" + warFolderName;
        File f = new File(war);
        f = new File(f.getCanonicalPath());
        Resource r = new FileResource(f.toURI().toURL());
        return r;
    }
    
    /**
     * ref: http://www.junlu.com/list/96/481920.html - setting up JNDI in embedded Jetty
     * @param server
     * @throws Exception
     */
    private static void prepareDataSource(Server server, EnvSettings es) throws Exception {
        Context envContext = null;
        
        ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(server.getClass().getClassLoader());
        try {
            Context context = new InitialContext();
            Context compContext = (Context) context.lookup ("java:comp");
            envContext = compContext.createSubcontext("env");
        } finally {
            Thread.currentThread().setContextClassLoader(oldLoader);
        }

        if (null != envContext){
            Properties p = new Properties();
            p.put("driverClassName", es.getJdbcDriver());
            p.put("url", es.jdbcUrl);
            p.put("username", es.dbUser);
            p.put("password", es.dbPass);
            
            //DBCP Properties for testing
            p.put("maxActive", "5");
            p.put("maxWait", "60000");
            p.put("minIdle", "0");
            p.put("maxIdle", "1");
            
            p.put("validationQuery", es.getValidationQuery());
            DataSource ds = MonitoredDataSourceFactory.createDataSource(p);
            
            NamingUtil.bind(envContext, es.getJndiName(), ds);
        }
        
        System.out.println(">>> DataSource ["+es.getJndiName()+"] created:");
        System.out.println(">>> \t url: " + es.jdbcUrl);
    }
    
    private static EnvSettings readEnv(){
        EnvSettings es = new EnvSettings();
        es.httpPort = Integer.valueOf(_readEnv("HTTP_PORT", DEFAULT_HTTP_PORT));
        es.ctxPath = _readEnv("CTX_PATH", DEFAULT_CTX_PATH);
        es.jdbcUrl = _readEnv("JDBC_URL", "Unknown_JDBC_URL");
        es.dbUser = _readEnv("DB_USER", "Unknown_DB_USER");
        es.dbPass = _readEnv("DB_PASS", "");
        return es;
    }
    private static String _readEnv(String var, String defVal){
        String v = System.getenv(var);
        if (null==v){
            v=defVal;
        }
        if (null!=v){
            System.setProperty(var, v);        //Remember the real variable value into System Properties
        }
        return v;
    }
    /**
     * Store the settings defined by environment variables
     * @author root
     *
     */
    private static class EnvSettings{
        private int httpPort;
        private String ctxPath;
        private String jdbcUrl;
        private String dbUser;
        private String dbPass;
        
        private String _jdbcUrl(){
            return (null==this.jdbcUrl)?"":this.jdbcUrl;
        }
        /** jdbc:oracle:thin:@localhost:1521:XE */
        private boolean isOracle(){
            return _jdbcUrl().startsWith("jdbc:oracle:thin:");
        }
        /** jdbc:sqlserver://localhost:1433;databaseName=orderMgr */
        private boolean isMSSQL(){
            return _jdbcUrl().startsWith("jdbc:sqlserver://");
        }
        /** jdbc:jtds:sqlserver://localhost:1433/orderMgr */
        private boolean isMSSQL_JTDS(){
            return _jdbcUrl().startsWith("jdbc:jtds:sqlserver://");
        }
        /** jdbc:mysql://localhost:3306/orderMgr?useUnicode=true&amp;characterEncoding=UTF-8 */
        private boolean isMySQL(){
            return _jdbcUrl().startsWith("jdbc:mysql://");
        }
        /** jdbc:hsqldb:hsql://localhost/TestHSQLDB */
        private boolean isHSQL(){
            return _jdbcUrl().startsWith("jdbc:hsqldb:");
        }
        
        private String getJndiName(){
            return "jdbc/"+this.ctxPath+"DS";
        }
        private String getDefaultSchema(){
            if (isHSQL()){
                return "PUBLIC";
            }else if (isMySQL()){
                return this.ctxPath;
            }else{
                return this.dbUser;
            }
        }
        private String getJdbcDriver(){
            if (isOracle()){
                return "oracle.jdbc.driver.OracleDriver";
            }else if (isMSSQL()){
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            }else if (isMSSQL_JTDS()){
                return "net.sourceforge.jtds.jdbc.Driver";
            }else if (isMySQL()){
                return "com.mysql.jdbc.Driver";
            }else if (isHSQL()){
                return "org.hsqldb.jdbcDriver";
            }else{
                throw new RuntimeException("Unknown database type ["+this.jdbcUrl+"]");
            }
        }
        private String getValidationQuery(){
            if (isOracle()){
                return "SELECT 1 From dual";
            }else if (isMSSQL()){
                return "Select 1";
            }else if (isMSSQL_JTDS()){
                return "Select 1";
            }else if (isMySQL()){
                return "Select 1";
            }else if (isHSQL()){
                return "Select COUNT(*) As X From INFORMATION_SCHEMA.SYSTEM_USERS Where 1=0";
            }else{
                throw new RuntimeException("Unknown database type ["+this.jdbcUrl+"]");
            }
        }
        private String getHibernateDialect(){
            if (isOracle()){
                return "org.hibernate.dialect.Oracle10gDialect";
            }else if (isMSSQL()){
                return "org.hibernate.dialect.SQLServer2005Dialect";
            }else if (isMSSQL_JTDS()){
                return "org.hibernate.dialect.SQLServer2005Dialect";
            }else if (isMySQL()){
                return "org.hibernate.dialect.MySQL5Dialect";
            }else if (isHSQL()){
                return "org.hibernate.dialect.HSQLDialect";
            }else{
                throw new RuntimeException("Unknown database type ["+this.jdbcUrl+"]");
            }
        }
    }
}
