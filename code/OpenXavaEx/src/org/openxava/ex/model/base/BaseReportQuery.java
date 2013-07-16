package org.openxava.ex.model.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.openxava.actions.IJDBCAction;
import org.openxava.actions.ViewBaseAction;
import org.openxava.annotations.Editor;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.LabelFormat;
import org.openxava.annotations.LabelFormatType;
import org.openxava.model.MapFacade;
import org.openxava.util.IConnectionProvider;
import org.openxava.view.View;

/**
 * The base class for Report Query View Model
 * @author root
 *
 */
@MappedSuperclass
public abstract class BaseReportQuery {
	private static final String FIELD_NAME_queryResult = "queryResult";
	
	@Column
	@Hidden
	private Integer pageSize;

	@Column
	@Hidden
	private Integer pageNo;
	
	@Column
	@LabelFormat(LabelFormatType.SMALL)
	@Editor("EX_ReportQueryData_Editor")
	private QueryResult queryResult;

	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public QueryResult getQueryResult() {
		return queryResult;
	}
	public void setQueryResult(QueryResult queryResult) {
		this.queryResult = queryResult;
	}
	
	protected abstract QueryResult doQuery(_ReportQueryAction action);

	public static class QueryResult implements Serializable {
		private static final long serialVersionUID = 20130703L;
		private List<Map<String, Object>> data;
		private Class<?> tmplClass;
		private Integer recordsCount;
		private Map<String, Class<?>> fields;	//It must be a LinkedHashMap
		
		public QueryResult(){
			//Default constructor for JSON deserialize
			this.data = new ArrayList<Map<String,Object>>();
		}
		public QueryResult(Class<?> tmplClass, Map<String, Class<?>> fields, List<Map<String, Object>> data){
			this(tmplClass, fields, data, null);
		}
		public QueryResult(Class<?> tmplClass, Map<String, Class<?>> fields, List<Map<String, Object>> data, Integer recordsCount){
			this.tmplClass = tmplClass;
			this.fields = fields;
			this.data = data;
			this.recordsCount = recordsCount;
		}
		public List<Map<String, Object>> getData() {
			return data;
		}
		public Map<String, Class<?>> getFields() {
			return fields;
		}
		public Class<?> getTmplClass() {
			return tmplClass;
		}
		public Integer getRecordsCount() {
			return recordsCount;
		}
	}
	
	public static class _ReportQueryAction extends ViewBaseAction implements IJDBCAction {
		private IConnectionProvider connectionProvider;

		public void execute() throws Exception {
			View view = this.getView();
			Object obj = MapFacade.create(this.getModelName(), view.getValues());
			if (! (obj instanceof BaseReportQuery)){
				throw new RuntimeException("To run query, model must extends from ["+BaseReportQuery.class.getName()+"]");
			}
			BaseReportQuery rq = (BaseReportQuery)obj;
			QueryResult result = rq.doQuery(this);
			rq.setQueryResult(result);

			view.setValue(FIELD_NAME_queryResult, result);
			
			this.resetDescriptionsCache();
		}

		public void setConnectionProvider(IConnectionProvider provider) {
			this.connectionProvider = provider;
		}

		public IConnectionProvider getConnectionProvider() {
			return connectionProvider;
		}
	}

}
