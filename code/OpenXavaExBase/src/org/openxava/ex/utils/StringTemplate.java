package org.openxava.ex.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Processor for String Template with "Variable"(PlaceHolder)
 * @author root
 *
 */
public class StringTemplate {
	/**Java style variable definition: ${...}*/
	public static final String REGEX_PATTERN_JAVA_STYLE = "\\$\\{([^\\}]*)\\}";
	/**Unix shell style variable definition: $...*/
	public static final String REGEX_PATTERN_UNIX_SHELL_STYLE = "\\$([a-zA-Z_]+)";
	
	private Map<String, String> varExpMap = new LinkedHashMap<String, String>();//Map <varName:String, varExpression:String>
	private Map<String, String> varValueMap = new HashMap<String, String>();	//Map <varName:String, varValue:String>
	private List<String> varExpList = new ArrayList<String>();					//List <varName:String>
	
	private String templateString;
	
	public static final String parse(String tmpl, Map<String, String> vars, String templateRegex){
		if (null==vars) return tmpl;
		StringTemplate st = new StringTemplate(tmpl, templateRegex);
		for (Entry<String, String> en: vars.entrySet()){
			st.setVariable(en.getKey(), en.getValue());
		}
		return st.getParseResult();
	}

	/**
	 * Build a String Template Processor
	 * @param templateString Template with variable definition
	 * @param templateRegex template style(Regex), see {@link #REGEX_PATTERN_JAVA_STYLE} 和 {@link #REGEX_PATTERN_UNIX_SHELL_STYLE}
	 */
	public StringTemplate(String templateString, String templateRegex){
    	Pattern p = Pattern.compile(templateRegex);
    	Matcher m = p.matcher(templateString);
    	while(m.find()){
    		if (m.groupCount()==1){
    			//group[0]: the all match string, group[1]: the group
    			varExpMap.put(m.group(1), m.group(0));
    			//The position of variable
    			varExpList.add(m.group(1));
    		}else{
    			throw new RuntimeException(
    					"Pattern '"+templateRegex+"' is not valid, it must contain and only contain one regex group.");
    		}
    	}
    	this.templateString = templateString;
	}
	
	/**
	 * Get all variables in a String Template
	 * @return
	 */
	public String[] getVariables(){
		return (String[])varExpMap.keySet().toArray(new String[]{});
	}
	
	/**
	 *  Get all variables in order, one variable should have more then one position.
	 * @return
	 */
	public String[] getVariablesInOrder(){
		return varExpList.toArray(new String[0]);
	}
	
	/**
	 * Set value by variable name
	 * @param varName
	 * @param varValue
	 */
	public void setVariable(String varName, String varValue){
		varValueMap.put(varName, varValue);
	}
	
	/**
	 * After {@link #setVariable(String, String)}, get the result string(replaced variable with value)
	 * @return
	 */
	public String getParseResult(){
		Iterator<Entry<String, String>> itr = varExpMap.entrySet().iterator();
		String tmp = this.templateString;
		while (itr.hasNext()){
			Entry<String, String> en = itr.next();
			String varName = (String)en.getKey();
			String varExp = (String)en.getValue();
			String val = (String)varValueMap.get(varName);
			if (null!=val){
				tmp = tmp.replace(varExp, val);
			}else{
				//Keep original
			}
		}
		return tmp;
	}
	
	/**
	 * Clean variable's value(see {@link #setVariable(String, String)})
	 */
	public void reset(){
		this.varValueMap.clear();
	}
}
