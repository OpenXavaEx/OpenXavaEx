package org.openxava.ex.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Misc {
	/**
	 * Catch Exception and wrap as RuntimeException to throw
	 * 
	 * @param t
	 * @throws RuntimeException
	 */
	public static void throwRuntime(Throwable t) throws RuntimeException {
		try {
			throw t;
		} catch (RuntimeException re) {
			throw re;
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Object to String
	 * 
	 * @param o
	 * @return
	 */
	public static String toStr(Object o) {
		if (null == o) {
			return null;
		} else if (o instanceof String) {
			return (String) o;
		} else {
			return o.toString();
		}
	}

	/**
	 * Object to integer
	 * 
	 * @param o
	 * @return
	 */
	public static Integer toInt(Object o) {
		if (null == o) {
			return null;
		} else if (o instanceof Number) {
			Number n = (Number) o;
			return n.intValue();
		} else {
			return Integer.valueOf(o.toString());
		}
	}

	/**
	 * Use some Key-Value arguments to create an attributes(Map<String, Object>) object
	 * 
	 * @param keyAndValue
	 * @return
	 */
	public static Map<String, Object> $attrs(Object... keyAndValue) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < keyAndValue.length; i++) {
			if (i % 2 == 1) {
				result.put((String) keyAndValue[i - 1], keyAndValue[i]);
			}
		}
		return result;
	}

	/**
	 * Use some Key-Value arguments to create an properties(Map<String, String>) object
	 * 
	 * @param keyAndValue
	 * @return
	 */
	public static Map<String, String> $props(String... keyAndValue) {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < keyAndValue.length; i++) {
			if (i % 2 == 1) {
				result.put(keyAndValue[i - 1], keyAndValue[i]);
			}
		}
		return result;
	}

	/**
	 * Create list of arguments
	 * @param <T>
	 * 
	 * @param value
	 * @return
	 */
	public static <T> List<T> $list(T... value) {
		List<T> lst = new ArrayList<T>();
		for (int i = 0; i < value.length; i++) {
			lst.add(value[i]);
		}
		return lst;
	}
	
	public static List<Field> getClassFields(Class<?> cls){
		List<Field> result = new ArrayList<Field>();
		Set<String> fNames = new HashSet<String>();
		Field[] fs = cls.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			if (! fNames.contains(f.getName())){
				fNames.add(f.getName());
				result.add(f);
			}
		}
		Class<?> sc = cls.getSuperclass();
		if (null!=sc){
			List<Field> sfs = getClassFields(sc);
			for(Field sf: sfs){
				if (! fNames.contains(sf.getName())){
					fNames.add(sf.getName());
					result.add(sf);
				}
			}
		}
		return result;
	}
}
