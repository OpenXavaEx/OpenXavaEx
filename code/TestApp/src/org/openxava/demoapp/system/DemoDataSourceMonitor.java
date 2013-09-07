package org.openxava.demoapp.system;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.ex.datasource.IDataSourceMonitor;
import org.openxava.ex.utils.Misc;
import org.openxava.util.Users;

/**
 * The demo of DataSourceMonitor, always used for inject current user's info into database session,
 * to support some row-level access control such as Oracle VPD(Virtual Private Database).
 */
public class DemoDataSourceMonitor implements IDataSourceMonitor {
	private static final Log log = LogFactory.getLog(DemoDataSourceMonitor.class);
	
	public void onConnectionCreated(Connection conn, Context context) {
		try{
			String drvName = conn.getMetaData().getDriverName();
			log.warn(">>> [CREATED]"+drvName+" - Connection "+System.identityHashCode(conn)+": ["+conn+"] created .");
			
			setDBSessionContextUserInfo(conn, Users.getCurrent());
		}catch(SQLException e){
			Misc.throwRuntime(e);
		}
	}

	private void setDBSessionContextUserInfo(Connection conn, String userName) throws SQLException {
		String drvName = conn.getMetaData().getDriverName();
		/*
		-- MUST Create following CONTEXT and PROCEDURE before run this demo
		 
		CREATE CONTEXT DEMO_APP_CTX USING SET_DEMO_APP_CTX;

		CREATE OR REPLACE PROCEDURE SET_DEMO_APP_CTX (
		   P_KEY IN VARCHAR2,
		   P_VAL IN VARCHAR2
		) IS
		BEGIN
		   DBMS_SESSION.SET_CONTEXT('DEMO_APP_CTX', P_KEY, P_VAL);
		END;
		*/
		if (drvName.toUpperCase().indexOf("ORACLE") >= 0){
			CallableStatement cs = null;
			try{
				cs = conn.prepareCall("{ call SET_DEMO_APP_CTX('USER', ?) }");
				cs.setString(1, userName);
				cs.execute();
			}finally{
				if (null!=cs) cs.close();
			}
		}else{
			//Support oracle only
		}
	}

	public void onConnectionClosing(Connection conn, Context context) {
		try{
			String drvName = conn.getMetaData().getDriverName();
			log.warn(">>> [CLOSING]"+drvName+" - Connection "+System.identityHashCode(conn)+": ["+conn+"] closing .");
			
			setDBSessionContextUserInfo(conn, "guest");
		}catch(SQLException e){
			Misc.throwRuntime(e);
		}		
	}
}
