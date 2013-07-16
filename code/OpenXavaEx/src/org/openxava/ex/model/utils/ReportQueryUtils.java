package org.openxava.ex.model.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.Oracle8iDialect;
import org.openxava.annotations.Tab;
import org.openxava.ex.annotation.query.Condition;
import org.openxava.ex.annotation.query.FieldProp;
import org.openxava.ex.annotation.query.FieldTmpl;
import org.openxava.ex.annotation.query.FieldTmpls;
import org.openxava.ex.annotation.query.Sql;
import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.model.base.BaseReportQuery;
import org.openxava.ex.model.base.BaseReportQuery.QueryResult;
import org.openxava.ex.utils.Misc;
import org.openxava.ex.utils.StringTemplate;
import org.openxava.jpa.XPersistence;
import org.openxava.util.IConnectionProvider;

/**
 * Utils for {@link BaseReportQuery}
 * @author root
 *
 */
public class ReportQueryUtils {
    public static class Statement2Run {
        protected String sql4data;
        protected String sql4count;
        protected List<Object> parameters = new ArrayList<Object>();
    }
    public static class ClassPropsPair {
        protected Class<?> tmplClass;
        protected FieldProp[] props;
    }
    public static class FidlePropsPair {
        protected String fieldName;
        protected FieldProp[] props;
    }
        
    public static final QueryResult runSqlQuery(IConnectionProvider connProvider, Object condition){
        Connection conn = null;
        PreparedStatement pstmtData = null;
        PreparedStatement pstmtCnt = null;
        ResultSet rsData = null;
        ResultSet rsCnt = null;
        Integer pageNo = null;
        Integer pageSize = null;
        try {
            //Get pageNo and pageSize
            if (condition instanceof BaseReportQuery){
                BaseReportQuery bc = (BaseReportQuery)condition;
                pageNo = bc.getPageNo();
                pageSize = bc.getPageSize();
            }
            //Create sql and prepare statements
            String dn = (String)XPersistence.getPersistenceUnitProperties().get(Environment.DIALECT);
            Statement2Run s2r = createStatement(condition, dn, pageNo, pageSize);
            conn = connProvider.getConnection();
            pstmtData = conn.prepareStatement(s2r.sql4data);
            pstmtCnt = conn.prepareStatement(s2r.sql4count);
            int pIdx=0;
            for(Object o: s2r.parameters){
                pIdx++;
                pstmtData.setObject(pIdx, o);
                pstmtCnt.setObject(pIdx, o);
            }
            //Get records count
            Integer recordsCnt = null;
            if (null!=pageNo && null!=pageSize){
                rsCnt = pstmtCnt.executeQuery();
                rsCnt.first();
                recordsCnt = rsCnt.getInt(1);
            }
            //run query, get fields
            rsData = pstmtData.executeQuery();
            List<String> fields = new ArrayList<String>();
            Map<String, Class<?>> fieldClassMap = new LinkedHashMap<String, Class<?>>();
            ResultSetMetaData md = rsData.getMetaData();
            int columnCount = md.getColumnCount();
            for(int i=0; i<columnCount; i++){
                String colName = md.getColumnLabel(i+1);
                fields.add(colName);
                Class<?> colClass = getColumnClass(md, i+1);
                if (null==colClass){
                	colClass = Object.class;	//Object means can't find the Class
                }
				fieldClassMap.put(colName, colClass);
            }
            //Fill data
            List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
            rsData.beforeFirst();
            while(rsData.next()){
                Map<String,Object> line = new HashMap<String,Object>();
                for(String colName: fields){
                    line.put(colName, rsData.getObject(colName));
                }
                data.add(line);
            }
            //Return ...
            Class<?> tmplClass = condition.getClass();
            return new QueryResult(tmplClass, fieldClassMap, data, recordsCnt);
        } catch (Exception e) {
            Misc.throwRuntime(e);
            return null;
        } finally {
            if (null!=rsData){ try {rsData.close();}catch(SQLException ex){/*Ignore it*/} }
            if (null!=pstmtData){ try {pstmtData.close();}catch(SQLException ex){/*Ignore it*/} }
            if (null!=conn){ try {conn.close();}catch(SQLException ex){/*Ignore it*/} }
        }
    }
    protected static final Class<?> getColumnClass(ResultSetMetaData md, int index) throws SQLException{
    	try{
    		String clz = md.getColumnClassName(index);
    		return ClassLoaderUtil.forName(ReportQueryUtils.class, clz);
    	}catch(Exception ex){
    		//Some driver may be notImplemented
    	}
    	int jdbcType = md.getColumnType(index);
        switch (jdbcType) {
        case Types.ARRAY:         	return null;
        case Types.BIGINT:         	return Long.class;
        case Types.BINARY:         	return byte[].class;
        case Types.BIT:         	return Boolean.class;
        case Types.BLOB:         	return null;
        case Types.BOOLEAN:     	return Boolean.class;
        case Types.CHAR:         	return String.class;
        case Types.CLOB:         	return String.class;
        case Types.DATALINK:    	return null;
        case Types.DATE:        	return Date.class;
        case Types.DECIMAL:        	return BigDecimal.class;
        case Types.DISTINCT:     	return null;
        case Types.DOUBLE:        	return Double.class;
        case Types.FLOAT:        	return Double.class;
        case Types.INTEGER:        	return Integer.class;
        case Types.JAVA_OBJECT:    	return null;
        case Types.LONGNVARCHAR:	return String.class;
        case Types.LONGVARBINARY:	return byte[].class;
        case Types.LONGVARCHAR:		return String.class;
        case Types.NCHAR:			return String.class;
        case Types.NCLOB:			return String.class;
        case Types.NULL:			return null;
        case Types.NUMERIC:			return BigDecimal.class;
        case Types.NVARCHAR:		return String.class;
        case Types.OTHER:			return null;
        case Types.REAL:			return Float.class;
        case Types.REF:				return null;
        case Types.ROWID:			return null;
        case Types.SMALLINT:		return Integer.class;
        case Types.SQLXML:			return null;
        case Types.STRUCT:			return null;
        case Types.TIME:			return Time.class;
        case Types.TIMESTAMP:		return Timestamp.class;
        case Types.TINYINT:			return Integer.class;
        case Types.VARBINARY:		return byte[].class;
        case Types.VARCHAR:			return String.class;
        default: return null;
        }
    }
    protected static final Statement2Run createStatement(Object condObj, String dialectName, Integer pageNo, Integer pageSize) throws IllegalAccessException{
        //Create the whole sql by @Condition
        Class<? extends Object> cls = condObj.getClass();
        Sql sqlAnno = cls.getAnnotation(Sql.class);
        if (null==sqlAnno){
            throw new UnsupportedOperationException("Can't run sql query on class without 'Sql' definition: "+cls.getName());
        }
        String sql = sqlAnno.value();
        List<Field> flds = Misc.getClassFields(cls);
        List<String> fragments = new ArrayList<String>();
        for (Field f: flds) {
            Condition cond = f.getAnnotation(Condition.class);
            String fragment = cond.value();
            if (null==fragment || fragment.trim().length() <= 0){
                throw new UnsupportedOperationException(
                        "Can't run sql query on class with incorrect 'Condition' definition: "
                        + cls.getName() + " - " + f.getName() + ", value() is BLANK");
            }
            Object val = FieldUtils.readField(f, condObj, true);
            if (null!=val){
                fragments.add(fragment);
            }
        }
        sql = sql.replace(sqlAnno.conditionTag(), StringUtils.join(fragments, " AND "));
        //Parse sql AS prepareStatement
        Statement2Run sr = new Statement2Run();
        JXPathContext context = JXPathContext.newContext(condObj);
        StringTemplate st = new StringTemplate(sql, StringTemplate.REGEX_PATTERN_JAVA_STYLE);
        String[] vars = st.getVariablesInOrder();
        for (int i = 0; i < vars.length; i++) {
            String var = vars[i];
            if (var.startsWith("#")){    //"#" means direct replacement, else means jdbc parameter
                Object propVal = context.getValue(var.substring(1));
                if (null==propVal) propVal = "";
                st.setVariable(var, propVal.toString());
            }else{
                Object propVal = context.getValue(var);
                st.setVariable(var, "?");
                sr.parameters.add(propVal);
            }
        }
        sql = st.getParseResult();
        sr.sql4count = "SELECT COUNT(*) AS CNT FROM ("+sql+") \"$paging_countTable\"";
        //Make paging sql
        if (null!=pageNo && null!=pageSize && pageSize >= 0){
            sql = fixSql2Paging(sql, dialectName, pageSize, pageNo);
        }
        //Return
        sr.sql4data = sql;
        return sr;
    }
    private static final String fixSql2Paging(String sql, String dialectClassName, int pageSize, int pageNo){
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
    public static final List<String> mergeFields(Class<?> template, List<String> resultFields){
        List<String> tmplFlds = new ArrayList<String>();

        Tab t = template.getAnnotation(Tab.class);
        if (null==t){
            return resultFields;
        }
        //Fields depends on the annotation "@Tab" properties string
        String props = t.properties();
        props = StringUtils.replace(props, " ", "");
        String[] pList = StringUtils.split(props, ',');
        tmplFlds.addAll(Arrays.asList(pList));
        
        return mergeFields(tmplFlds, resultFields);
    }
    /**
     * Merge the result fields into template({@link ReportQueryUtilsTest#testMergeFields()})
     * @param template
     * @param resultFields
     * @return
     */
    protected static final List<String> mergeFields(List<String> template, List<String> resultFields){
        //Indexer, store 1)[from,to], 2)fieldName
        List<Object> indexer = new ArrayList<Object>();
        //Parse template to create the indexer
        int tmplSize = template.size();
        int resSize = resultFields.size();
        for (int i=0; i<tmplSize; i++){
            String cur = template.get(i);
            if ("*".equals(cur)){
                String before = i>0?template.get(i-1):null;
                String after = i<(tmplSize-1)?template.get(i+1):null;
                int indexBefore = 0;
                if (null!=before){
                    indexBefore = resultFields.indexOf(before)+1;
                }
                int indexAfter = resSize;
                if (null!=after){
                    indexAfter = resultFields.indexOf(after);
                }
                indexer.add(new int[]{indexBefore, indexAfter});
            }else{
                indexer.add(cur);
            }
        }
        //Create merged list by indexer
        List<String> merged = new ArrayList<String>();
        for(Object o: indexer){
            if (o instanceof String){
                merged.add((String)o);
            }else {
                int[] fromTo = (int[])o;
                if (fromTo[0] <= fromTo[1]){
                    merged.addAll(resultFields.subList(fromTo[0], fromTo[1]));
                }else{
                    //fromIndex > toIndex, means nothing to add
                }
            }
        }
        
        return merged;
    }
    
    public static final Map<String, Map<String, String>> getFieldsProperties(Class<?> baseTmpl, Class<?> tmplClass, Map<String, Class<?>> fields){
        Map<String, Map<String, String>> result = new HashMap<String, Map<String,String>>();
        
        //Create the field properties list of tmplClass, based on default template class
        List<ClassPropsPair> classPorps = new ArrayList<ClassPropsPair>();
        List<FidlePropsPair> fldNamePorps = new ArrayList<FidlePropsPair>();
        FieldTmpls tmpls = baseTmpl.getAnnotation(FieldTmpls.class);
        readFieldTmplClass(tmpls, classPorps, fldNamePorps);
        //Overwrite field properties defined in current template class
        tmpls = tmplClass.getAnnotation(FieldTmpls.class);
        if (null!=tmpls){
            readFieldTmplClass(tmpls, classPorps, fldNamePorps);
        }
        
        for(Entry<String, Class<?>> fld: fields.entrySet()){
            Map<String, String> tmp = readFieldProps(fld.getValue(), fld.getKey(), classPorps, fldNamePorps);
            result.put(fld.getKey(), tmp);
        }
        return result;
    }
    private static final void readFieldTmplClass(FieldTmpls tmpls, List<ClassPropsPair> classPorps, List<FidlePropsPair> fldNamePorps) {
        for(FieldTmpl ft: tmpls.value()){
            if (FieldTmpl.NAME_DEFAULT.equals(ft.fieldName())){
                ClassPropsPair cpp = new ClassPropsPair();
                cpp.tmplClass = ft.fieldClass(); cpp.props = ft.value();
                classPorps.add(cpp);
            }else {
                FidlePropsPair fpp = new FidlePropsPair();
                fpp.fieldName = ft.fieldName(); fpp.props = ft.value();
                fldNamePorps.add(fpp);
            }
        }
    }
    protected static final Map<String, String> readFieldProps(Class<?> fldClass, String field, List<ClassPropsPair> classPorps, List<FidlePropsPair> fldNamePorps){
    	Map<String, String> result = new HashMap<String, String>();
    	
    	for(ClassPropsPair cpp: classPorps){
    		Class<?> clz = cpp.tmplClass;
    		if (clz.isAssignableFrom(fldClass)){
    			for(int i=0; i<cpp.props.length; i++){
    				result.put(cpp.props[i].name(), cpp.props[i].value());
    			}
    		}
    	}
    	
    	for(FidlePropsPair fpp: fldNamePorps){
    		if (fpp.fieldName.equalsIgnoreCase(field)){
       			for(int i=0; i<fpp.props.length; i++){
    				result.put(fpp.props[i].name(), fpp.props[i].value());
    			}
    		}
    	}
    	
    	return result;
    }

}
