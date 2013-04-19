package org.openxava.ex.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ////// 1. Prepare the configuration (connection parameters to the DB, ect.)
        // Empty map. We add no additional property, everything is already in the persistence.xml
        Map<String,Object> map=new HashMap<String,Object>();   
        // Get the config from the persistence.xml file, with the unit name as parameter.
        Ejb3Configuration conf =  new Ejb3Configuration().configure("default",map);
        SchemaUpdate schemaUpdate =new SchemaUpdate(conf.getHibernateConfiguration());

        /////// 2. Get the SQL
        // Before we run the update, we start capturing the console output (to add ";" later)
        PrintStream initOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream newOut = new PrintStream(outputStream);
        System.setOut(newOut);

        //The update is executed in script mode only
        schemaUpdate.execute(true, false);

        //We reset the original out
        System.setOut(initOut);

        ////// 3. Prints that SQL at the console with a good format (adding a ";" after each line).
        System.out.println("--*******************************************Begin of SQL********************************************");
        BufferedReader ouReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
        String str = ouReader.readLine();
        while(str != null){  // For each (sometimes multiline) SQL statement
            // now, str equals "".
            str = ouReader.readLine();  // 
            while (str != null && !str.trim().equals("")) { // for each line of the same statement
                System.out.println();  // previous line is finished.
                System.out.print(str.toLowerCase());
                str = ouReader.readLine();
            }
            // Statement is now finished
            System.out.println(";");
        }
        System.out.println("--*******************************************End of SQL********************************************");

        ////// 4. Print eventual exceptions.
        //If some exception occurred we display them
        if(!schemaUpdate.getExceptions().isEmpty()){
            System.out.println();
            System.out.println("SOME EXCEPTIONS OCCURED WHILE GENERATING THE UPDATE SCRIPT:");
            for (Exception e: (List<Exception>)schemaUpdate.getExceptions()) {
                System.out.println(e.getMessage());
            }
        }
    }	

}
