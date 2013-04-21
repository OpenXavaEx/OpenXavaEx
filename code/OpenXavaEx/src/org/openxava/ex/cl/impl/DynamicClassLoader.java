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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private final static String PATH_SEPARATOR = ";" ;

    private String classpath;
	private List<File> baseElements = new ArrayList<File>();    			//List<File>
    private Map<String, URL> resourceCache = new HashMap<String, URL>();    //Map<String, URL>
    private ClassModifyChecker checker;

    /**
     * Create the dynamic classloader
     * @param parent
     * @param classpath	The CLASSPATH to search the class, use ";" to separate multiple path
     * @param checker
     */
	public DynamicClassLoader(ClassLoader parent, String classpath, ClassModifyChecker checker) {
		super(parent);
        this.checker = checker;
        this.classpath = classpath;
        //Parse classpaths
        String[] sa = classpath.split(PATH_SEPARATOR);
        int len = sa.length;
        for (int i = 0; i < len; i++) {
            File file = new File(sa[i].trim());
			this.baseElements.add(file);
		}
	}

	public ClassModifyChecker getClassModifyChecker(){
		return this.checker;
	}
	
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
                        "Can't find ["+className+"] in ["+this.classpath+"]");
            }
            checker.rememberFile(classFile.getSourceFile(), classFile.getTimestamp());
            InputStream is = new BufferedInputStream(classFile.getClassInputStream());
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
		} catch (IOException ex) {
			throw new ClassNotFoundException(className, ex);
		}
	}

    private ClassFileVO getClassInputStream(String className) throws IOException {
        ClassFileVO vo = null;
        String classFileName = className.replace('.', File.separatorChar) + ".class";
        String classZipName = className.replace('.', '/') + ".class";
        int len = this.baseElements.size();
        for (int i=0; i<len; i++){
            File path = (File)this.baseElements.get(i);
            if (path.isDirectory()){
                String pathRoot = path.getCanonicalPath();
                StringBuffer sb = new StringBuffer(pathRoot);
                sb.append(File.separator).append(classFileName);
                File classFile = new File(sb.toString());
                if (classFile.exists()){
                    logger.debug("Class [" + className + "] found, in ["+pathRoot+"]");
                    InputStream is = new FileInputStream(classFile);
                    vo = new ClassFileVO(classFile.getCanonicalPath(), is, classFile.lastModified());
                }
            }else{  //Jar File
                if (path.exists()){
                    ZipFile zipFile = new ZipFile(path, ZipFile.OPEN_READ);
                    ZipEntry en = zipFile.getEntry(classZipName);
                    if (null!=en){
                        String jarFilePath = path.getCanonicalPath();
                        logger.debug("Class [" + className + "] found, in ["+jarFilePath+"]");
                        InputStream is = zipFile.getInputStream(en);
                        vo = new ClassFileVO(jarFilePath, is, path.lastModified());
                    }
                    //zipFile.close(); //FIXME: CAN'T Close, because we can't close the related InputStream
                }
            }
        }
        return vo;
    }

	protected URL findResource(String name) {
		logger.debug("findResource " + name + " ...");
		try {
			URL url = super.findResource(name);
			if (null!=url){
                return url;
            }
            //Find in Cache first
            url = (URL)this.resourceCache.get(name);
            if (null!=url){
                logger.debug("Resource URL [" + name + "] was found in cache");
                return url;
            }
            //Find in file system or jar
            url = getResourceUrl(name);
            if (null==url){
                logger.error("Can't find ["+name+"] in ["+this.classpath+"]");
                return null;
            }else{
                this.resourceCache.put(name, url);
			    return url;
            }
        } catch (IOException ex) {
            logger.error("findResource error: " + ex.getMessage(), ex);
            return null;
		}
	}

    private URL getResourceUrl(String name) throws IOException {
        URL url = null;
        int len = this.baseElements.size();
        for (int i=0; i<len; i++){
            File path = (File)this.baseElements.get(i);
            if (path.isDirectory()){
                String pathRoot = path.getCanonicalPath();
                StringBuffer sb = new StringBuffer(pathRoot);
                sb.append(File.separator).append(name);
                File resourceFile = new File(sb.toString());
                if (resourceFile.exists()){
                    logger.debug("Resource [" + name + "] found, in ["+pathRoot+"]");
                    url = new URL("file:///" + resourceFile.getCanonicalPath());
                }
            }else{  //Jar File
                if (path.exists()){
                    ZipFile zipFile = new ZipFile(path, ZipFile.OPEN_READ);
                    ZipEntry en = zipFile.getEntry(name);
                    if (null!=en){
                        String jarFileUrl = "file:///" + path.getCanonicalPath();
                        String jarUrl = "jar:"+jarFileUrl+"!/"+name;
                        logger.debug("Resource [" + name + "] found, in ["+jarUrl+"]");
                        url = new URL(jarUrl);
                    }
                    zipFile.close();    //MUST close here
                }
            }
        }
        return url;
    }
}
