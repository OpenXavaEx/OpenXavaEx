package org.openxava.ex.model.base;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.openxava.ex.cl.ClassLoaderUtil;
import org.openxava.ex.model.base.BaseReportQuery.QueryResult;
import org.openxava.ex.utils.Misc;

/**
 * The root class for all kinds of "ClientModel" - which mapping {@link QueryResult} to single string for client
 * @author root
 *
 */
public class BaseReportQueryClientModel {
	/** The template class name, which should be used when mapping back from string to {@link QueryResult} */
	private String tmplClassName;
	/** The field name and their Data Type ClassName mapping, which should be used when mapping back from string to {@link QueryResult} */
	private Map<String, String> fieldNames = new LinkedHashMap<String, String>();
	
	public Class<?> _getTmplClass() {
		return loadClass(tmplClassName);
	}
	public void _setTmplClass(Class<?> tmplClass) {
		this.tmplClassName = tmplClass.getName();
	}
	public Map<String, Class<?>> _getFields() {
		Map<String, Class<?>> flds = new LinkedHashMap<String, Class<?>>();
		for(Entry<String, String> en: this.fieldNames.entrySet()){
			flds.put(en.getKey(), loadClass(en.getValue()));
		}
		return flds;
	}
	public void _setFields(Map<String, Class<?>> fields) {
		for(Entry<String, Class<?>> en: fields.entrySet()){
			this.fieldNames.put(en.getKey(), en.getValue().getName());
		}
	}

	private Class<?> loadClass(String clName) {
		try {
			return ClassLoaderUtil.forName(this.getClass(), clName);
		} catch (ClassNotFoundException e) {
			Misc.throwRuntime(e);
			return Object.class;
		}
	}
	
	public String getTmplClassName() {
		return tmplClassName;
	}
	public void setTmplClassName(String tmplClassName) {
		this.tmplClassName = tmplClassName;
	}
	public Map<String, String> getFieldNames() {
		return fieldNames;
	}
	public void setFieldNames(Map<String, String> fieldNames) {
		this.fieldNames = fieldNames;
	}
}
