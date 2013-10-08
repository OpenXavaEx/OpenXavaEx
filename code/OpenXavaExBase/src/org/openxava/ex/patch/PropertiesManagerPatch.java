package org.openxava.ex.patch;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Patch for PropertiesManager
 */
public class PropertiesManagerPatch {
	public static final Object tryReadPropertyFromProxyObject(Object proxied, String propName) throws IllegalAccessException{
		Object handler = null;
		Object v = null;
		try{
			handler = FieldUtils.readField(proxied, "handler", true);
		}catch(IllegalArgumentException iae){
			//Can't find handler, means not a javassist proxy object(org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer)
			handler = null;
		}
		if (null!=handler){
			try{
				v = FieldUtils.readField(handler, propName, true);
			}catch(IllegalArgumentException iae){
				//Can't find field in handler ...
			}
		}
		if (null==v){
			v = FieldUtils.readField(proxied, propName, true);
		}
		return v;
	}
}
