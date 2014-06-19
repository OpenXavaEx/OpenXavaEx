package org.openxava.ex.model.pqgrid;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.openxava.ex.editor.shell.IShellEditor;
import org.openxava.ex.editor.shell.ShellEditorContext;
import org.openxava.ex.model.base.BaseReportQuery.QueryResult;
import org.openxava.ex.model.pqgrid.PQGridClientModel.ColModelDetail;
import org.openxava.ex.model.utils.ReportQueryUtils;
import org.openxava.ex.utils.Misc;
import org.openxava.ex.utils.StringTemplate;
import org.openxava.formatters.IFormatter;
import org.openxava.util.Labels;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.DateSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Formatter and Editor for PQGrid(http://paramquery.com)
 * @author root
 */
public class PQGrid {
	public static class UdfDateSerializer implements ObjectSerializer {
		/**
		 * Copy from {@link DateSerializer#write(JSONSerializer, Object, Object, Type)}, force return value as "new Date(...)"
		 */
		public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType) throws IOException {
	        SerializeWriter out = serializer.getWriter();
	        if (object == null) {
	            out.writeNull();
	            return;
	        }
            out.write("new Date(");
            out.writeLongAndChar(((Date) object).getTime(), ')');
		}
	}
	public static String getModelData(HttpServletRequest request){
		String edit = request.getParameter("edit");
		String id = request.getParameter("id");
		boolean isEditable = "true".equalsIgnoreCase(edit);
		PQGridClientModel o = (PQGridClientModel) request.getSession().getAttribute(id);
		if (null==o){
			return "null";
		}else{
			o.editable =isEditable;
			SerializeConfig config = new SerializeConfig();
			config.put(Date.class, new UdfDateSerializer());
			String json = JSON.toJSONString(o, config, SerializerFeature.PrettyFormat);
			return json;
		}
	}
	
	public static class _Editor implements IShellEditor {
		public String render(ShellEditorContext ctx) {
			String iframe = StringTemplate.parse(
					"<iframe id='${propertyKey}-ifmWorkspace' frameborder='0' src='${src}' style='width:100%' scrolling='no'></iframe>",
					Misc.$props(
							"propertyKey", ctx.getPropertyKey(),
							"src", ctx.getContextPath() + "/xava-ex/editors/iframe/PQGrid.jsp?edit=true&id="+ctx.getRawValue()),
					StringTemplate.REGEX_PATTERN_JAVA_STYLE);
			return iframe;
		}
		public String renderReadOnly(ShellEditorContext ctx) {
			String iframe = StringTemplate.parse(
					"<iframe id='${propertyKey}-ifmWorkspace' frameborder='0' src='${src}' style='width:100%' scrolling='no'></iframe>",
					Misc.$props(
							"propertyKey", ctx.getPropertyKey(),
							"src", ctx.getContextPath() + "/xava-ex/editors/iframe/PQGrid.jsp?edit=false&id="+ctx.getRawValue()),
					StringTemplate.REGEX_PATTERN_JAVA_STYLE);
			return iframe;
		}
	}
	
	public static class _Formatter implements IFormatter{
		private String getLabel(String id, QueryResult qr, boolean inView){
			if (null==qr){
				//Remain the id
			}else if(inView){
				id = qr.getTmplClass().getSimpleName() + ".view." + id;
			}else{
				id = qr.getTmplClass().getSimpleName() + ".tab.properties." + id;
			}
			return Labels.getQualified(id);
		}
		
		public String format(HttpServletRequest request, Object object) throws Exception {
			QueryResult qr = (QueryResult)object;
			PQGridClientModel model = null;
			if (null!=qr){
				model = new PQGridClientModel();
				model._setTmplClass(qr.getTmplClass());
				Map<String, Class<?>> qrFlds = qr.getFields();
				model._setFields(qrFlds);
				
				model.dataModel.data = qr.getData();
				//model.title = getLabel(qr.getClass().getSimpleName(), qr, true);
				model.title = "";
				model.editable = true;		//TODO: Support edit
				
				//Mix the fields from the template class
				List<String> fields = new ArrayList<String>(qrFlds.keySet());
				fields = ReportQueryUtils.mergeFields(qr.getTmplClass(), fields);
				Map<String, Class<?>> mergedFlds = new LinkedHashMap<String, Class<?>>();
				for(String fld: fields){
					Class<?> clz = qrFlds.get(fld);
					if (null==clz) clz = Object.class;
					mergedFlds.put(fld, clz);
				}
				//Create colModel annotated by Template class
				Map<String, Map<String, String>> fieldsProps = 
						ReportQueryUtils.getFieldsProperties(PQGridFieldsTmpl.class, qr.getTmplClass(), mergedFlds);
				for(String fld: fields){
					Map<String, String> props = fieldsProps.get(fld);
					if (null==props) props = new HashMap<String, String>();
					ColModelDetail dtl = new ColModelDetail();
					dtl.title = getLabel(fld, qr, false);
					dtl.dataIndx = fld;
					dtl.align = ColModelDetail.findAlign(props);
					dtl.dataType = ColModelDetail.findDataType(props);
					dtl.width = ColModelDetail.findWidth(props);
					dtl.editable = ColModelDetail.findEditable(props);
					dtl.hidden = ColModelDetail.findHidden(props);
					dtl.prototype = ColModelDetail.findPrototype(props);
					dtl.numberPrecision = ColModelDetail.findNumberPrecision(props);
					dtl.action = ColModelDetail.findAction(props);
					model.colModel.add(dtl);
				}
			}
			
			//Create uuid and register PQGridClientModel into session
			String id = UUID.randomUUID().toString();
			request.getSession().setAttribute(id, model);
			return id;
		}

		public Object parse(HttpServletRequest request, String string)throws Exception {
			if (null!=string){
				PQGridClientModel o = (PQGridClientModel) request.getSession().getAttribute(string);
				request.getSession().removeAttribute(string);	//IMPORTMENT: remove it, avoid memory leak
				//Rebuild _QueryResult from PQGridClientModel
				if (null!=o){
					return new QueryResult(o._getTmplClass(), o._getFields(), o.dataModel.data);
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
	}

}
