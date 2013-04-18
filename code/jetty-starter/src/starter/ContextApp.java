package starter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.FileResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;

public class ContextApp {
	private static final int HTTP_PORT = 8080;
	private static final String CTX_PATH = "/ox";

	public static void main(String[] args) throws Exception {
		//Prepare system properties for logging
		System.setProperty("org.eclipse.jetty.LEVEL", "ALL");
		System.setProperty("org.eclipse.jetty.util.log.SOURCE", "false");
		
        Server server = new Server(HTTP_PORT);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        ServletContextHandler ctx = new ServletContextHandler(contexts, CTX_PATH, ServletContextHandler.SESSIONS);
        //FIXME: org.eclipse.jetty.servlet.ServletHolder.initJspServlet() need it - InitParameter "com.sun.appserv.jsp.classpath"
        ctx.setClassLoader(ContextApp.class.getClassLoader());
        //Allow find resource in multi-folder
        Resource res = new ResourceCollection(
        		buildFolderResource("/war-base"), buildFolderResource("/war-base2")
        );
        ctx.setBaseResource(res);
        
        //Default servlet
        ctx.addServlet(DefaultServlet.class, "/");
        //JSP servlet
        ctx.addServlet(JspServlet.class, "*.jsp");

        server.start();
        System.out.println(server.dump());
        server.join();
	}

	private static final Resource buildFolderResource(String folderName) throws IOException, URISyntaxException {
        //Detect the war's position
        URL binUrl = ContextApp.class.getResource("/");
        String bin = binUrl.getFile();
        String war = (new File(bin)).getParent() + folderName;
        File f = new File(war);
        f = new File(f.getCanonicalPath());
        Resource r = new FileResource(f.toURI().toURL());
        return r;
	}
}
