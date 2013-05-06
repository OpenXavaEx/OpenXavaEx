package org.openxava.ex.json;

import java.util.HashMap;
import java.util.Map;

public class ModuleInfo {
	private String app;
	private String module;
	private String version;
	private String title;
	private String language;
	private Map<String, Object> properties = new HashMap<String, Object>();

	public ModuleInfo(String app, String module, String version){
		this.app = app;
		this.module = module;
		this.version = version;
	}
	public void setProperty(String p, Object v){
		properties.put(p, v);
	}
	public void setTitle(String title){
		this.title = title;
	}
	public void setLanguage(String language){
		this.language = language;
	}
	
	public String getApp() {
		return app;
	}
	public String getModule() {
		return module;
	}
	public String getVersion() {
		return version;
	}
	public String getTitle() {
		return title;
	}
	public String getLanguage() {
		return language;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
}
