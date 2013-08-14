package org.openxava.ex.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class DBUtils {
	/**
	 * Run SQL query and store result into Beans
	 * @param entityClass Bean 的 class
	 * @param conn Database connection
	 * @param sql SQL Statement
	 * @param params SQL Parameters
	 * @return
	 * @throws SQLException
	 */
	public static final <T> List<T> find(Class<T> entityClass, Connection conn, String sql, Object[] params) throws SQLException {
		QueryRunner qr = new QueryRunner();
        List<T> list;
        if (null == params) {
            list = (List<T>) qr.query(conn, sql, new BeanListHandler<T>(entityClass));
        } else {
            list = (List<T>) qr.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        }
        return list;
	}

	/**
	 * Run SQL query and store result into collection
	 * @param conn Database connection
	 * @param sql SQL Statement
	 * @param params SQL Parameters
	 * @return
	 * @throws SQLException
	 */
    public static List<Map<String, Object>> find(Connection conn, String sql, Object[] params) throws SQLException {
        QueryRunner qr = new QueryRunner();
        List<Map<String, Object>> list;
        if (params == null) {
            list = (List<Map<String, Object>>) qr.query(conn, sql, new MapListHandler());
        } else {
            list = (List<Map<String, Object>>) qr.query(conn, sql, new MapListHandler(), params);
        }
        return list;
    }

    private static final void _closeConn(Connection conn){
		if (null!=conn){
			try{
				conn.close();
			}catch(Exception ignore){
				/*Ignore it*/
			}
		}
    }
	/**
	 * Same as {@link #find(Class, Connection, String, Object[])}, but close connection automatically
	 */
	public static final <T> List<T> findAndClose(Class<T> entityClass, Connection conn, String sql, Object[] params) throws SQLException {
		try{
			return find(entityClass, conn, sql, params);
		}finally{
			_closeConn(conn);
		}
	}

	/**
	 * Same as {@link #find(Connection, String, Object[])}, but close connection automatically
	 */
    public static List<Map<String, Object>> findAndClose(Connection conn, String sql, Object[] params) throws SQLException {
		try{
			return find(conn, sql, params);
		}finally{
			_closeConn(conn);
		}
    }
}
