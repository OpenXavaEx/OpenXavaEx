package org.openxava.ex.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

/** Small utility to be run by a developer to identify the difference between
 * its entities and its DB schema. It produces an SQL to be copy/pasted and applied
 * on the DB manually. Each developers having its own DB, when a developer commits its
 * Java code with new entity attributes (needing new DB columns), he also commits
 * an updated SQL file with the SQL that other developers need to apply on their local DB.
 * Later, when deploying the next version of the application in production,
 * this SQL file with cumulated changes will be applied onto the production DB.  
 * 
 * Limitations: 
 * 1. the Hibernate schema update does not detect removed attributes. 
 * If you have to delete a column, you need to write the SQL manually;
 * 
 * 2. the Hibernate schema update does not detect changes on existing columns.
 * for example, if you add @Column(nullable=false), it will not generates an 
 * additional DB constraint.
 * 
 * @author C¨¦dric Fieux & John Rizzo & Aymeric Levaux
 *
 */
//http://stackoverflow.com/questions/2645255/how-to-use-hibernate-schemaupdate-class-with-a-jpa-persistence-xml/12403549#12403549
public class SchemaUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 20130420L;
	
	public static final String PERSISTENCE_UNIT_LIST = "PERSISTENCE_UNIT_LIST";

	private String[] persistenceUnitList;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config); 
		
		String units = config.getInitParameter(PERSISTENCE_UNIT_LIST);
		if (null==units){
			persistenceUnitList = new String[]{};
		}else{
			persistenceUnitList = units.split(";");
		}
	}	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		boolean doUpdate = (url.endsWith("/update"));
		

		String[] units = persistenceUnitList;
		for (int i = 0; i < units.length; i++) {
			String persistenceUnit = units[i];
	        StringWriter sw = updateSchema(persistenceUnit, doUpdate);
	        // Output to page
	        String msg = "\n********\n" + sw.toString() + "\n";
			this.log(msg);
	        resp.getWriter().write(msg);
		}
    }

	@SuppressWarnings("unchecked")
	private static StringWriter updateSchema(String persistenceUnit, boolean doUpdate) throws UnsupportedEncodingException {
		////// 1. Prepare the configuration (connection parameters to the DB, ect.)
        // Empty map. We add no additional property, everything is already in the persistence.xml
        Map<String,Object> map=new HashMap<String,Object>();   
        // Get the config from the persistence.xml file, with the unit name as parameter.
        Ejb3Configuration conf =  new Ejb3Configuration().configure(persistenceUnit, map);
        SchemaUpdate schemaUpdate =new SchemaUpdate(conf.getHibernateConfiguration());

        /////// 2. Get the SQL
        // Before we run the update, we start capturing the console output (to add ";" later)
        PrintStream initOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream newOut = new PrintStream(outputStream);
        try{
            System.setOut(newOut);
            //The update is executed in script mode only
            schemaUpdate.execute(true, doUpdate);
        }finally{
            //We reset the original out
            System.setOut(initOut);
        }

        ////// 3. Output the console output text ...
        StringWriter sw = new StringWriter();
		PrintWriter writer = new PrintWriter(sw);
        String out = outputStream.toString("UTF-8");
        if ("".equals(out)) out = "N/A";
        writer.write((doUpdate?"Update schema":"Update schema sql preview") + "["+persistenceUnit+"] ***: \n" + out);

        ////// 4. Print eventual exceptions.
        //If some exception occurred we display them
        if(!schemaUpdate.getExceptions().isEmpty()){
        	writer.write("SOME EXCEPTIONS OCCURED WHILE GENERATING THE UPDATE SCRIPT ...\n\n");
            for (Exception e: (List<Exception>)schemaUpdate.getExceptions()) {
            	e.printStackTrace(writer);
            }
        }
		return sw;
	}

}
