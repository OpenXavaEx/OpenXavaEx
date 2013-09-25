package org.openxava.ex.model.utils;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.utils.Misc;

/**
 * The database specified functions
 */
public class DBSpecifiedUtils {

	/**
	 * Create paging SQL for different database 
	 * @param sql
	 * @param dialectClassName
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public static final String fixSql2Paging(String sql, String dialectClassName, int pageSize, int pageNo){
	    Object dialect;
	    try {
	        dialect = ClassLoaderUtil.forName(ReportQueryUtils.class, dialectClassName).newInstance();
	    } catch (Exception e) {
	        Misc.throwRuntime(e);
	        return sql;
	    }
	    int offset = (pageNo * pageSize) + 1;
	    int limit = (pageNo * pageSize) + pageSize;
	    if (dialect instanceof Oracle8iDialect){
	        return "SELECT * FROM (" +
	                "   SELECT \"$paging_rawTable\".*, ROWNUM \"$paging_rowNo\"" + 
	                "     FROM ("+sql+") \"$paging_rawTable\" WHERE ROWNUM <= " + limit +
	                ") WHERE \"$paging_rowNo\" >= " + offset;
	    }else if (dialect instanceof MySQLDialect){
	        return sql + " LIMIT "+ offset + ", " + limit;
	    }else{
	        throw new UnsupportedOperationException("Unsupported Dialect: "+dialect.getClass().getName());
	    }
	}
	
	/**
	 * Unwrap database specified ResultSet data, for example: oracle.sql.TIMESTAMP
	 * @param obj
	 * @return
	 */
	public static Object unwrapResultSetObject(Object obj){
		if (null==obj){
			return obj;
		}
		String name = obj.getClass().getName();
		try {
			if ("oracle.sql.TIMESTAMP".equalsIgnoreCase(name)) {
				obj = MethodUtils.invokeMethod(obj, "timestampValue");	//java.sql.Timestamp
			}else if ("oracle.sql.DATE".equalsIgnoreCase(name)) {
				obj = MethodUtils.invokeMethod(obj, "dateValue");		//java.sql.Date
			}
			return obj;
		} catch (Exception e) {
			Misc.throwRuntime(e);
			return null;
		}
	}

	/**
	 * Unwrap database specified ResultSet column class, for example: oracle.sql.TIMESTAMP
	 * @param obj
	 * @return
	 */
	public static Class<?> unwrapResultSetColumnClass(Class<?> clazz){
		String name = clazz.getName();
		try {
			if ("oracle.sql.TIMESTAMP".equalsIgnoreCase(name)) {
				return Timestamp.class;
			}else if ("oracle.sql.DATE".equalsIgnoreCase(name)) {
				return Date.class;
			}
			return clazz;
		} catch (Exception e) {
			Misc.throwRuntime(e);
			return null;
		}
	}

}
