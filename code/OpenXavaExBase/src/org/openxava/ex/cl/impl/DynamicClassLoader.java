/*
 * (c) Copyright 2005 thinkbase.net . All Rights Reserved.
 * $ Id: MyClassLoader.java, v 1.1, @2005-6-23 23:26:44, thinkbase.net Exp $
 */

package org.openxava.ex.cl.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thinkbase.net
 * @version $Revision: 1.1 $
 */
public class DynamicClassLoader extends ClassLoader {
	private final static Logger logger = LoggerFactory.getLogger( DynamicClassLoader.class );
	
	private List<File> classpath = new ArrayList<File>();    							//List<File>
    private Map<String, List<URL>> resourcesCache = new HashMap<String, List<URL>>();   //Map<String, URL>
    private Set<File> syncedTopFolder = new HashSet<File>();	//Remember the TopFolder which is called by rememberTopFolder(...)
    private ClassModifyChecker checker;
    private String classpath4Disp;		//Just for display in log

    /**
     * Create the dynamic classloader
     * @param parent
     * @param classpath	The CLASSPATH to search the class
     * @param checker
     */
	public DynamicClassLoader(ClassLoader parent, List<File> classpath, ClassModifyChecker checker) {
		super(parent);
        this.checker = checker;
		this.checker.reset();
        this.classpath = classpath;
        
        refreshClassPath4Display(classpath);
	}

	private void refreshClassPath4Display(List<File> classpath) {
		StringBuffer buf = new StringBuffer();
        for(File f: classpath){
        	try {
				buf.append(f.getCanonicalPath()).append(";");
			} catch (IOException e) {
				logger.error("getCanonicalPath error", e);
			}
        }
        this.classpath4Disp = buf.toString();
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		logger.debug("findClass " + name + " ...");
		byte[] bytes = loadClassBytes(name);
		Class<?> theClass = defineClass(name, bytes, 0, bytes.length);//A
		if (theClass == null)
			throw new ClassFormatError();
		return theClass;
	}

	private byte[] loadClassBytes(String className) throws ClassNotFoundException {
		try {
			ClassFileVO classFile = getClassInputStream(className);
            if (null==classFile){
                throw new ClassNotFoundException(
                        "Can't find ["+className+"] in ["+this.classpath4Disp+"]");
            }
            this.checker.rememberFile(classFile.getSourceFile(), classFile.getTimestamp());
            return classFile.getClassBytes();
		} catch (IOException ex) {
			throw new ClassNotFoundException(className, ex);
		}
	}

	private byte[] readBytes(InputStream in) throws IOException{
        InputStream is = new BufferedInputStream(in);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while (true) {
			int i = is.read();
			if (i == -1) {
				break;
			}
            baos.write(i);
		}
		is.close();
		return baos.toByteArray();
	}
    private ClassFileVO getClassInputStream(String className) throws IOException {
        ClassFileVO vo = null;
        String classFileName = className.replace('.', File.separatorChar) + ".class";
        String classZipName = className.replace('.', '/') + ".class";
        int len = this.classpath.size();
        for (int i=0; i<len; i++){
            File path = (File)this.classpath.get(i);
            
            //Remember the directory's mask
            if ( (!syncedTopFolder.contains(path)) && path.isDirectory()){
            	this.checker.rememberTopFolder(path);
            	syncedTopFolder.add(path);
            }
            
            if (null!=vo){
            	continue;	//If class found, other classpath needn't search
            }
            if (path.isDirectory()){
                String pathRoot = path.getCanonicalPath();
                StringBuffer sb = new StringBuffer(pathRoot);
                sb.append(File.separator).append(classFileName);
                File classFile = new File(sb.toString());
                if (classFile.exists()){
                    logger.debug("Class [" + className + "] found, in ["+pathRoot+"]");
                    InputStream is = new FileInputStream(classFile);
                    vo = new ClassFileVO(classFile.getCanonicalPath(), readBytes(is), classFile.lastModified());
                    is.close();
                }
            }else{  //Jar File
                if (path.exists()){
                    ZipFile zipFile = new ZipFile(path, ZipFile.OPEN_READ);
                    ZipEntry en = zipFile.getEntry(classZipName);
                    if (null!=en){
                        String jarFilePath = path.getCanonicalPath();
                        logger.debug("Class [" + className + "] found, in ["+jarFilePath+"]");
                        InputStream is = zipFile.getInputStream(en);
                        vo = new ClassFileVO(jarFilePath, readBytes(is), path.lastModified());		//Use the last modified time of jar file
                        is.close();
                    }
                    zipFile.close();
                }
            }
        }
        return vo;
    }


	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		logger.debug("findResources " + name + " ...");
		
		//The result of default implementation
		Enumeration<URL> baseRes = super.findResources(name);
		
		List<URL> foundRes;
		//Find in cache
		foundRes = resourcesCache.get(name);
		if (null==foundRes){
			//Find in file system or jar
			foundRes = getResourceUrls(name);
			resourcesCache.put(name, foundRes);
		}
		
		//Merge all result
		List<URL> result = new ArrayList<URL>(foundRes);
		while(baseRes.hasMoreElements()){
			URL u = baseRes.nextElement();
			result.add(u);
		}
		
		if (result.size()>0){
			logger.debug("Resource URL [" + name + "] was found with " + result.size() + " instances.");
		}else{
			logger.error("Can't find ["+name+"] in ["+this.classpath4Disp+"]");
		}

		return Collections.enumeration(result);
	}
    
	@Override
	protected URL findResource(String name) {
		try {
			Enumeration<URL> urls = this.findResources(name);
			if (urls.hasMoreElements()){
				return urls.nextElement();
			}else{
				return null;
			}
        } catch (IOException ex) {
            logger.error("findResource error: " + ex.getMessage(), ex);
            return null;
		}
	}

    private List<URL> getResourceUrls(String name) throws IOException {
        List<URL> urls = new ArrayList<URL>();
        int len = this.classpath.size();
        for (int i=0; i<len; i++){
            File path = (File)this.classpath.get(i);
            if (path.isDirectory()){
                String pathRoot = path.getCanonicalPath();
                StringBuffer sb = new StringBuffer(pathRoot);
                sb.append(File.separator).append(name);
                File resourceFile = new File(sb.toString());
                if (resourceFile.exists()){
                    logger.debug("Resource [" + name + "] found, in ["+pathRoot+"]");
                    URL url = new URL("file:///" + resourceFile.getCanonicalPath());
                    urls.add(url);
                }
            }else{  //Jar File
                if (path.exists()){
                    ZipFile zipFile = new ZipFile(path, ZipFile.OPEN_READ);
                    ZipEntry en = zipFile.getEntry(name);
                    if (null!=en){
                        String jarFileUrl = "file:///" + path.getCanonicalPath();
                        String jarUrl = "jar:"+jarFileUrl+"!/"+name;
                        logger.debug("Resource [" + name + "] found, in ["+jarUrl+"]");
                        URL url = new URL(jarUrl);
                        urls.add(url);
                    }
                    zipFile.close();
                }
            }
        }
        return urls;
    }
}
