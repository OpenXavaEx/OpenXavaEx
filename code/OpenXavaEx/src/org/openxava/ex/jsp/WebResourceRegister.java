package org.openxava.ex.jsp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * To register the Javascript and CSS links
 * @author root
 */
public class WebResourceRegister {
	private static ThreadLocal<List<String>> links = new ThreadLocal<List<String>>();
	private static ThreadLocal<List<String>> skipedLinks = new ThreadLocal<List<String>>();
	static {
		links.set(new ArrayList<String>());
		skipedLinks.set(new ArrayList<String>());
	}
	public static void reset(){
		links.remove();
		links.set(new ArrayList<String>());
		skipedLinks.remove();
		skipedLinks.set(new ArrayList<String>());
	}
	
	private static void _register(String prefix, String link){
		if (null==link) return;
		if (! link.startsWith("/")) link = "/" + link;
		
		link = prefix + link;
		
		List<String> lst = links.get();
		if (! lst.contains(link)){
			lst.add(link);
		}
	}
	public static void registerJs(String js){
		_register("JS:", js);
	}
	public static void registerCss(String css){
		_register("CSS:", css);
	}

	public static String outHtml(String contextPath){
		if (null==contextPath) contextPath = "/";
		if (! contextPath.endsWith("/")) contextPath = contextPath + "/";
		
		//<link rel="stylesheet" type="text/css" media="all" href="<%=contextPath%>/*.css"/>
		//<script type="text/javascript" src="<%=contextPath%>/*.js"></script>
		List<String> result = new ArrayList<String>();
		List<String> lst = links.get();
		List<String> skiped = skipedLinks.get();
		for(String link: lst){
			if (! skiped.contains(link)){
				if (link.startsWith("JS:")){
					link = link.substring(3);
					if (link.startsWith("/")) link = link.substring(1);
					result.add("<script type=\"text/javascript\" src=\""+contextPath+link+"\"></script>");
				}else if(link.startsWith("CSS:")){
					link = link.substring(4);
					if (link.startsWith("/")) link = link.substring(1);
					result.add("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\""+contextPath+link+"\"/>");
				}
			}
		}
		
		//Remember the output links into skiped
		skipedLinks.get().addAll(lst);
		links.set(new ArrayList<String>());
		
		return StringUtils.join(result, '\n');
	}
}
