package org.openxava.ex.datasource;

import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.utils.Misc;

public class MonitoredDataSourceFactory extends BasicDataSourceFactory {
	public static final String PROP_MONITOR_CLASS_NAME = MonitoredDataSourceFactory.class.getName()+"_MONITOR_CLASSNAME";

	private static final Log log = LogFactory.getLog(MonitoredDataSourceFactory.class);

	@SuppressWarnings("rawtypes")
	@Override
	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
		DataSource ds = (DataSource)super.getObjectInstance(obj, name, nameCtx, environment);
		return new InnerDataSource(ds);
	}

	public static DataSource createDataSource(Properties properties) throws Exception {
		DataSource ds = BasicDataSourceFactory.createDataSource(properties);
		return new InnerDataSource(ds);
	}
	
	public static class ConnectionWrapper implements Connection{
		private IDataSourceMonitor.Context context;
		private IDataSourceMonitor monitor;
		private Connection inner;
		public ConnectionWrapper(Connection inner, IDataSourceMonitor monitor, IDataSourceMonitor.Context context) {
			this.inner = inner;
			this.monitor = monitor;
			this.context = context;
		}
		public void close() throws SQLException {
			if (null!=this.monitor){
				this.monitor.onConnectionClosing(this.inner, this.context);
			}
			inner.close();
		}

		public <T> T unwrap(Class<T> iface) throws SQLException {
			return inner.unwrap(iface);
		}
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return inner.isWrapperFor(iface);
		}
		public Statement createStatement() throws SQLException {
			return inner.createStatement();
		}
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return inner.prepareStatement(sql);
		}
		public CallableStatement prepareCall(String sql) throws SQLException {
			return inner.prepareCall(sql);
		}
		public String nativeSQL(String sql) throws SQLException {
			return inner.nativeSQL(sql);
		}
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			inner.setAutoCommit(autoCommit);
		}
		public boolean getAutoCommit() throws SQLException {
			return inner.getAutoCommit();
		}
		public void commit() throws SQLException {
			inner.commit();
		}
		public void rollback() throws SQLException {
			inner.rollback();
		}
		public boolean isClosed() throws SQLException {
			return inner.isClosed();
		}
		public DatabaseMetaData getMetaData() throws SQLException {
			return inner.getMetaData();
		}
		public void setReadOnly(boolean readOnly) throws SQLException {
			inner.setReadOnly(readOnly);
		}
		public boolean isReadOnly() throws SQLException {
			return inner.isReadOnly();
		}
		public void setCatalog(String catalog) throws SQLException {
			inner.setCatalog(catalog);
		}
		public String getCatalog() throws SQLException {
			return inner.getCatalog();
		}
		public void setTransactionIsolation(int level) throws SQLException {
			inner.setTransactionIsolation(level);
		}
		public int getTransactionIsolation() throws SQLException {
			return inner.getTransactionIsolation();
		}
		public SQLWarning getWarnings() throws SQLException {
			return inner.getWarnings();
		}
		public void clearWarnings() throws SQLException {
			inner.clearWarnings();
		}
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return inner.createStatement(resultSetType, resultSetConcurrency);
		}
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
			return inner.prepareStatement(sql, resultSetType,
					resultSetConcurrency);
		}
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
			return inner.prepareCall(sql, resultSetType, resultSetConcurrency);
		}
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return inner.getTypeMap();
		}
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			inner.setTypeMap(map);
		}
		public void setHoldability(int holdability) throws SQLException {
			inner.setHoldability(holdability);
		}
		public int getHoldability() throws SQLException {
			return inner.getHoldability();
		}
		public Savepoint setSavepoint() throws SQLException {
			return inner.setSavepoint();
		}
		public Savepoint setSavepoint(String name) throws SQLException {
			return inner.setSavepoint(name);
		}
		public void rollback(Savepoint savepoint) throws SQLException {
			inner.rollback(savepoint);
		}
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			inner.releaseSavepoint(savepoint);
		}
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return inner.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
			return inner.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		public CallableStatement prepareCall(String sql, int resultSetType,
				int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return inner.prepareCall(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
		}
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return inner.prepareStatement(sql, autoGeneratedKeys);
		}
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return inner.prepareStatement(sql, columnIndexes);
		}
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return inner.prepareStatement(sql, columnNames);
		}
		public Clob createClob() throws SQLException {
			return inner.createClob();
		}
		public Blob createBlob() throws SQLException {
			return inner.createBlob();
		}
		public NClob createNClob() throws SQLException {
			return inner.createNClob();
		}
		public SQLXML createSQLXML() throws SQLException {
			return inner.createSQLXML();
		}
		public boolean isValid(int timeout) throws SQLException {
			return inner.isValid(timeout);
		}
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			inner.setClientInfo(name, value);
		}
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			inner.setClientInfo(properties);
		}
		public String getClientInfo(String name) throws SQLException {
			return inner.getClientInfo(name);
		}
		public Properties getClientInfo() throws SQLException {
			return inner.getClientInfo();
		}
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return inner.createArrayOf(typeName, elements);
		}
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return inner.createStruct(typeName, attributes);
		}

		
	}
	
	public static class InnerDataSource implements DataSource{
		private DataSource inner;
		private String monitorClassName;
		private IDataSourceMonitor monitor;

		public InnerDataSource(DataSource inner){
			this.inner = inner;
			initMonitor();
		}
		
		private void initMonitor(){
			if (null==this.monitorClassName){
				this.monitorClassName = System.getProperty(PROP_MONITOR_CLASS_NAME);
			}
			if (null==this.monitorClassName){
				this.monitor = null;
				return;
			}
			try {
				@SuppressWarnings("unchecked")
				Class<IDataSourceMonitor>  mon = (Class<IDataSourceMonitor>
						) ClassLoaderUtil.forName(this.getClass(), this.monitorClassName);
				this.monitor = mon.newInstance();
			} catch (ClassNotFoundException e) {
				log.warn("Can't load DataSource Monitor ["+this.monitorClassName+"], monitoring disabled temporary");
				this.monitor = null;	//If can't local Monitor, keep silence
			} catch (InstantiationException e) {
				Misc.throwRuntime(e);
			} catch (IllegalAccessException e) {
				Misc.throwRuntime(e);
			}
		}
		
		public PrintWriter getLogWriter() throws SQLException {
			return inner.getLogWriter();
		}

		public void setLogWriter(PrintWriter out) throws SQLException {
			inner.setLogWriter(out);
		}

		public void setLoginTimeout(int seconds) throws SQLException {
			inner.setLoginTimeout(seconds);
		}

		public int getLoginTimeout() throws SQLException {
			return inner.getLoginTimeout();
		}

		public <T> T unwrap(Class<T> iface) throws SQLException {
			return inner.unwrap(iface);
		}

		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return inner.isWrapperFor(iface);
		}

		public Connection getConnection() throws SQLException {
			Connection conn = inner.getConnection();
			conn = handleCreated(conn);
			return conn;
		}

		public Connection getConnection(String username, String password) throws SQLException {
			Connection conn = inner.getConnection(username, password);
			conn = handleCreated(conn);
			return conn;
		}
		
		private Connection handleCreated(Connection conn){
			if (null==this.monitor){
				initMonitor();
			}
			if (null!=this.monitor){
				IDataSourceMonitor.Context context = new IDataSourceMonitor.Context();
				this.monitor.onConnectionCreated(conn, context);
				return new ConnectionWrapper(conn, this.monitor, context);
			}else{
				return conn;
			}
		}
	}
}
