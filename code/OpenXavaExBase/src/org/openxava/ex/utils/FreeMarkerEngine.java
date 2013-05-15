package org.openxava.ex.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * To render the FreeeMarker template
 * @author root
 *
 */
public class FreeMarkerEngine {
	private Map<String, Object> root = new HashMap<String, Object>();

    private Configuration stringConfig;
    private StringTemplateLoader stringTemplateLoader;
    private Map<String, Configuration> classTemplateConfigs = new HashMap<String, Configuration>();
    
    public FreeMarkerEngine(){
    	//Nothing to do
    }
    
    /**
     * Set the Model Context Object
     * @param name
     * @param obj
     */
    public void setModel(String name, Object obj){
        root.put(name, obj);
    }
    /**
     * Set the Model Context Object in batch
     * @param models
     */
    public void setModels(Map<String, Object> models){
        root.putAll(models);
    }
    /**
     * Remove Model Context Object by name
     * @param name
     */
    public void removeModel(String name){
        root.remove(name);
    }
    /**
     * Clean all Model Context Object
     */
    public void removeModels(){
        root.clear();
    }
    /**
     * Remove Model Context Object in batch
     * @param modelNames The names of object to remove
     */
    public void removeModels(List<String> modelNames){
        for(int i=0; i<modelNames.size(); i++){
            root.remove(modelNames.get(i));
        }
    }
    
    private StringTemplateLoader getStringTemplateLoader(){
        if (null==this.stringTemplateLoader){
            this.stringTemplateLoader = new StringTemplateLoader();
            
            if (null==stringConfig){
                //init Configuration in first time
                stringConfig = new Configuration();
            }
            stringConfig.setTemplateLoader(this.stringTemplateLoader);
        }
        return this.stringTemplateLoader;
    }
    private String getTempCfgKey(Class<?> ownerCls, String resource){
    	return ownerCls.getName() + ": absolute=" + resource.startsWith("/");
    }
    private Configuration getClassTemplateLoaderConfig(Class<?> ownerCls, String resource){
    	String key = getTempCfgKey(ownerCls, resource);
    	Configuration cfg = 
            (Configuration)this.classTemplateConfigs.get(key);
        if (null==cfg){
        	ClassTemplateLoader loader = new ClassTemplateLoader(ownerCls, resource.startsWith("/")?"/":"");
            cfg = new Configuration();
            this.classTemplateConfigs.put(key, cfg);
            
            cfg.setTemplateLoader(loader);
            return cfg;
        }else{
            return cfg;
        }
    }
    private String getId(String template){
        return "id=["+template+"]";
    }
    
    /**
     * Parse a string template
     * @param template
     * @return
     * @throws TemplateException
     */
    public String parseString(String template) throws TemplateException{
        StringTemplateLoader stringLoader = getStringTemplateLoader();
        stringLoader.putTemplate(getId(template), template);
        
        try {
            Template tmp = stringConfig.getTemplate(getId(template));
            Writer w = new StringWriter();
            tmp.process(root, w);
            return w.toString();
        } catch (IOException e) {
            throw new TemplateException(e, Environment.getCurrentEnvironment());
        }
    }
    /**
     * Parse a string template from InputStream
     * @param input
     * @param charset
     * @return
     * @throws TemplateException
     */
    public String parseString(InputStream input, String charset) throws TemplateException{
        try {
            return parseString(IOUtils.toString(input, charset));
        } catch (IOException e) {
            throw new TemplateException(e, Environment.getCurrentEnvironment());
        }
    }
    /**
     * Parse a string template from InputStream, use the system default charset
     * @param input
     * @return
     * @throws TemplateException
     */
    public String parseString(InputStream input) throws TemplateException{
        return parseString(input, null);
    }
    
    /**
     * Parse a string template from Resource
     * @param ownerCls The Java Class(Resource is loaded by It's Class Loader)
     * @param resource Resource path, should be absolute, or related to "ownerCls"
     * @param encoding
     * @return
     * @throws TemplateException
     */
    public String parseResource(Class<?> ownerCls, String resource, String encoding) throws TemplateException{
    	try{
        	Template tmp;
        	if (null==encoding){
        		tmp = getClassTemplateLoaderConfig(ownerCls, resource).getTemplate(resource);
        	}else{
        		tmp = getClassTemplateLoaderConfig(ownerCls, resource).getTemplate(resource, encoding);
        	}
            Writer w = new StringWriter();
            tmp.process(root, w);
            return w.toString();
    	}catch(IOException ex){
    		throw new TemplateException(ex, Environment.getCurrentEnvironment());
    	}
    }
    /**
     * Parse a string template from Resource, with system default charset
     * @param ownerCls The Java Class(Resource is loaded by It's Class Loader)
     * @param resource Resource path, should be absolute, or related to ownerCls
     * @return
     * @throws TemplateException
     */
    public String parseResource(Class<?> ownerCls, String resource) throws TemplateException{
    	return parseResource(ownerCls, resource, null);
    }
    /**
     * Parse a string template from Resource
     * @param owner The Java Object(Resource is loaded by It's Class Loader)
     * @param resource Resource path, should be absolute, or related to "owner"
     * @param encoding
     * @return
     * @throws TemplateException
     */
    public String parseResource(Object owner, String resource, String encoding) throws TemplateException{
        return parseResource(owner.getClass(), resource, encoding);
    }
    /**
     * Parse a string template from Resource, with system default charset
     * @param owner The Java Object(Resource is loaded by It's Class Loader)
     * @param resource Resource path, should be absolute, or related to "owner"
     * @return
     * @throws TemplateException
     */
    public String parseResource(Object owner, String resource) throws TemplateException{
        return parseResource(owner.getClass(), resource);
    }

    /**
     * Clone a new instance of FreeMarkerEngine
     * @return
     */
    public FreeMarkerEngine getClone(){
    	FreeMarkerEngine clone = new FreeMarkerEngine();
        clone.root = new HashMap<String, Object>(this.root);
        return clone;
    }

}
