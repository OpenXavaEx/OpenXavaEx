package org.openxava.ex.editor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openxava.ex.annotation.masks.RequiredMask;
import org.openxava.model.meta.MetaModel;
import org.openxava.model.meta.MetaProperty;
import org.openxava.model.meta.MetaReference;
import org.openxava.view.View;

/**
 * Utility for {@link RequiredMask}
 */
public class RequiredMasksHelper {
	
	@SuppressWarnings("unchecked")
	public static void setRequiredMask(View view, String propertyName, boolean required){
		HttpServletRequest req = view.getRequest();
		Map<String, Boolean> requiredMasks = (Map<String, Boolean>)req.getAttribute(RequiredMasksHelper.class.getName());
		if (null==requiredMasks){
			requiredMasks = new HashMap<String, Boolean>();
			req.setAttribute(RequiredMasksHelper.class.getName(), requiredMasks);
		}
		requiredMasks.put(propertyName, required);
	}
	
	/**
	 * Check the required-mask for a property in view
	 * @param p
	 * @param v
	 * @return
	 */
	public static Boolean isRequired(MetaProperty p, View v){
		String propertyName = p.getName();
		MetaModel metaModel = p.getMetaModel();
		
		return doCheck(propertyName, metaModel, v);
	}

	/**
	 * Check the required-mask for a reference in view.
	 * FIXME: IPropertyRequiredMaskSupport is unimplemented
	 * @param r
	 * @param v
	 * @return
	 */
	public static Boolean isRequired(MetaReference r, View v){
		String propName = r.getName();
		MetaModel metaModel = r.getMetaModel();
		
		return doCheck(propName, metaModel, v);
	}


	@SuppressWarnings("unchecked")
	private static Boolean doCheck(String field, MetaModel metaModel, View view) {
		//Check required-mask defined by View level(always on IOnChangePropertyAction)
		Map<String, Boolean> requiredMasks = (Map<String, Boolean>)
				view.getRequest().getAttribute(RequiredMasksHelper.class.getName());
		if (null!=requiredMasks && requiredMasks.containsKey(field)){
			return requiredMasks.get(field);
		}
		
		//Check required-mask defined on field
		Class<?> modelClass = metaModel.getPOJOClass();
		try {
			Field f = modelClass.getDeclaredField(field);
			RequiredMask rm = f.getAnnotation(RequiredMask.class);
			if (null==rm){
				return null;
			}else{
				return rm.value();
			}
		} catch (Exception e) {
			//Ignore it
			return null;
		}
	}
	
}
