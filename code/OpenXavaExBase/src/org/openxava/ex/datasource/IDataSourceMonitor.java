package org.openxava.ex.datasource;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * JDBC DataSource Monitor
 */
public interface IDataSourceMonitor {
	public void onConnectionCreated(Connection conn, Context context);
	public void onConnectionClosing(Connection conn, Context context);
	
	public static class Context{
		private Map<String, Object> attributes = new HashMap<String, Object>();
		
		public String[] getAttributeNames(){
			return this.attributes.keySet().toArray(new String[]{});
		}
		public void setAttribute(String key, Object val){
			this.attributes.put(key, val);
		}
		public Object getAttribute(String key){
			return this.attributes.get(key);
		}
	}
}
