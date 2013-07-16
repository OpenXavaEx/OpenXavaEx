package org.openxava.web.dwr;

import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.openxava.util.*;

/**
 * For accessing to the Tab from DWR. <p>
 * 
 * @author Javier Paniza
 */

public class Tab extends DWRBase {
	
	private static Log log = LogFactory.getLog(Tab.class);

	public static void setFilterVisible(HttpServletRequest request, String application, String module, boolean filterVisible, String tabObject) {
		checkSecurity(request, application, module);
		Users.setCurrent(request);
		org.openxava.tab.Tab tab = getTab(request, application, module, tabObject); 
		tab.setFilterVisible(filterVisible);
	}
	
	public static void setColumnWidth(HttpServletRequest request, String columnId, int width) { 
		try {
			String [] id = columnId.split("_+");
			if (!"ox".equals(id[0])) {
				// Bad format. This method relies in the id format by Ids class
				log.warn(XavaResources.getString("impossible_store_column_width")); 
				return;
			}
			String application = id[1];
			String module = id[2];
			StringBuffer collectionSB = new StringBuffer();
			for (int i=3; i<id.length-1; i++) { // To work with collections inside @AsEmbedded references 
				if (i>3) collectionSB.append("_");
				collectionSB.append(id[i]);				 
			}
			String collection = collectionSB.toString();
			String column=id[id.length-1];
			int columnIndex = Integer.parseInt(column.substring(3));
			checkSecurity(request, application, module);
			Users.setCurrent(request);
			String tabObject = "list".equals(collection)?"xava_tab":"xava_collectionTab_" + collection;
			try {
				org.openxava.tab.Tab tab = getTab(request, application, module, tabObject); 
				tab.setColumnWidth(columnIndex, width);
			}
			catch (ElementNotFoundException ex) { 
				// If it has not tab maybe it's a calculated collection
				org.openxava.view.View view = (org.openxava.view.View) getContext(request).get(application, module, "xava_view");
				view.getSubview(collection).setCollectionColumnWidth(columnIndex, width);
			}
		}
		catch (Exception ex) {
			log.warn(XavaResources.getString("impossible_store_column_width"), ex);
		}
	}
 
	private static org.openxava.tab.Tab getTab(HttpServletRequest request, String application, String module, String tabObject) {
		org.openxava.tab.Tab tab = (org.openxava.tab.Tab)		
		  getContext(request).get(application, module, tabObject);
		request.setAttribute("xava.application", application);
		request.setAttribute("xava.module", module);
		tab.setRequest(request);
		return tab;
	}

	
	
}
