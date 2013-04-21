package org.openxava.ex.cl;

import java.io.File;
import java.util.List;

import org.openxava.ex.cl.impl.ClassModifyChecker;
import org.openxava.ex.cl.impl.DynamicClassLoader;

/**
 * Tool to integrate the DynamicClassLoader
 * @author root
 */
public class ClassLoaderUtil {
	/**
	 * To replace the original class and resource load method in OpenXava, use Thread Context ClassLoader first
	 * @param clazz
	 * @return
	 */
	public static ClassLoader getClassLoader(Class<?> clazz){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (null == cl){
			cl = clazz.getClassLoader();
		}
		return cl;
	}
	
	/**
	 * Load the class with specified className, use Thread Context ClassLoader first
	 * @param clazz
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> forName(Class<?> clazz, String className) throws ClassNotFoundException{
		try {
			Class<?> c = Class.forName(className, true, getClassLoader(clazz));
			return c;
		} catch (ClassNotFoundException e) {
			Class<?> c = Class.forName(className);
			return c;
		}
	}
	
	/**
	 * Inject DynamicClassLoader into ContextClassLoader
	 */
	public static void injectIntoContextClassLoader(List<File> initClassPath, ClassModifyChecker checker, WhenClassReload reloader){
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		if (null==contextClassLoader){
			return;
		}
		boolean needReload = checker.isNeedReload();
		if (needReload){
			if (null!=reloader){
				reloader.doReload();
			}
		}
		if (contextClassLoader instanceof DynamicClassLoader){
			if (needReload){
				DynamicClassLoader dcl = (DynamicClassLoader)contextClassLoader;
				ClassLoader parent = dcl.getParent();
				dcl = new DynamicClassLoader(parent, initClassPath, checker);
				Thread.currentThread().setContextClassLoader(dcl);
			}
		}else{
			DynamicClassLoader dcl = new DynamicClassLoader(contextClassLoader, initClassPath, checker);
			Thread.currentThread().setContextClassLoader(dcl);
		}
	}
	
	public static interface WhenClassReload{
		public void doReload();
	}
}
