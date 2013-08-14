package org.openxava.ex.model.pqgrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openxava.ex.model.base.BaseReportQueryClientModel;

/**
 * Mirror the PQGrid javascript model into java
 * @author root
 *
 */
public class PQGridClientModel extends BaseReportQueryClientModel {
	public static final String ALIGN = "align";
	public static final String DATATYPE = "dataType";
	public static final String WIDTH = "width";
	public static final String EDITBALE = "editable";
	public static final String HIDDEN = "hidden";
	public static final String PROTOTYPE = "prototype";
	public static final String NUMBER_PRECISION = "numberPrecision";
	public static final String ACTION = "action";

	public static class DataModel{
		public List<Map<String, Object>> data =  new ArrayList<Map<String,Object>>();
	}
	public static class ColModelDetail{
		public static final String DATATYPE_string = "string";
		public static final String DATATYPE_integer = "integer";
		public static final String DATATYPE_float = "float";
		public static final String ALIGN_left = "left";
		public static final String ALIGN_center = "center";
		public static final String ALIGN_right = "right";
		public static final String EDITBALE_true = "true";
		public static final String EDITBALE_false = "false";
		public static final String HIDDEN_true = "true";
		public static final String HIDDEN_false = "false";
		
		public static final String PROTOTYPE_date = "date";
		public static final String PROTOTYPE_datetime = "datetime";
		public static final String PROTOTYPE_number = "number";
		
		public static final String DEFAULT_DATATYPE = DATATYPE_string;
		public static final String DEFAULT_ALIGN = ALIGN_left;
		public static final String DEFAULT_EDITBALE = EDITBALE_false;
		public static final String DEFAULT_HIDDEN = HIDDEN_false;
		public static final String DEFAULT_WIDTH = "100";
		
		public static final String DEFAULT_PROTOTYPE = "";
		public static final String DEFAULT_NUMBER_PRECISION = "3";

		public String dataIndx;
		public String title;

		public int width;
		public String dataType;
		public String align;
		public boolean editable;
		public boolean hidden;
		
		/** A prototype is the prefix of client javascript methods: render, editor, and getEditCellData */
		public String prototype;
		/** If prototype=number, define the precision of decimal part */
		public int numberPrecision;
		
		/**
		 * The column action, support server side OpenXava Action, or client side javascript.
		 */
		public String action;
		
		public static String findAlign(Map<String, String> props){
			return _find(props, ALIGN, DEFAULT_ALIGN);
		}
		public static String findDataType(Map<String, String> props){
			return _find(props, DATATYPE, DEFAULT_DATATYPE);
		}
		public static int findWidth(Map<String, String> props){
			String w = _find(props, WIDTH, DEFAULT_WIDTH);
			return Integer.parseInt(w);
		}
		public static boolean findEditable(Map<String, String> props){
			String e = _find(props, EDITBALE, DEFAULT_EDITBALE);
			return "true".equalsIgnoreCase(e);
		}
		public static boolean findHidden(Map<String, String> props){
			String h = _find(props, HIDDEN, DEFAULT_HIDDEN);
			return "true".equalsIgnoreCase(h);
		}
		public static String findPrototype(Map<String, String> props){
			return _find(props, PROTOTYPE, DEFAULT_PROTOTYPE);
		}
		public static int findNumberPrecision(Map<String, String> props){
			String p = _find(props, NUMBER_PRECISION, DEFAULT_NUMBER_PRECISION);
			return Integer.parseInt(p);
		}
		public static String findAction(Map<String, String> props){
			return _find(props, ACTION, null);
		}
		private static String _find(Map<String, String> props, String key, String def){
			String s = props.get(key);
			if (null==s){
				return def;
			}else{
				return s;
			}
		}
	}
	
	public String title;
	public boolean editable;
	public DataModel dataModel = new DataModel();
	public List<ColModelDetail> colModel = new ArrayList<ColModelDetail>();
}
