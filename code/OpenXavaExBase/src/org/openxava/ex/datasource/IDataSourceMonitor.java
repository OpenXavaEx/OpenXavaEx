package org.openxava.ex.datasource;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.openxava.ex.datasource.ConnectionTrace.TraceInfo;

/**
 * JDBC DataSource Monitor
 */
public interface IDataSourceMonitor {
	public void onConnectionCreated(Connection conn, Context context);
	public void onConnectionClosing(Connection conn, Context context);
	
	public static class Context{
		private Map<String, Object> attributes = new HashMap<String, Object>();
		private TraceInfo traceInfo;
		
		public Context(TraceInfo traceInfo){
			this.traceInfo = traceInfo;
		}
		
		public String[] getAttributeNames(){
			return this.attributes.keySet().toArray(new String[]{});
		}
		public void setAttribute(String key, Object val){
			this.attributes.put(key, val);
		}
		public Object getAttribute(String key){
			return this.attributes.get(key);
		}

		public TraceInfo getTraceInfo() {
			return traceInfo;
		}
	}
}
