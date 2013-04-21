/*
 * (c) Copyright 2005 thinkbase.net . All Rights Reserved.
 * $ Id: ClassModifyChecker.java, v 1.1, @2005-6-24 22:26:42, thinkbase.net Exp $
 */
package org.openxava.ex.cl.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thinkbase.net
 * @version $Revision: 1.1 $
 */
public class ClassModifyChecker {
	private final static Logger logger = LoggerFactory.getLogger( ClassModifyChecker.class );

    private Map<String, Long> memory = new HashMap<String, Long>();
    
    public synchronized void rememberFile(final String fileUrl, Long timestamp){
        memory.put(fileUrl, timestamp);
    }
    public boolean isNeedReload(){
        //Reload when file changed
        Iterator<Map.Entry<String, Long>> itr = memory.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String, Long> en = itr.next();
            String key = en.getKey();
            Long value = en.getValue();
            if (isFileModified(key, value)){
                logger.info("File " + key + " was changed.");
                return true;
            }
        }
        return false;
    }
    private boolean isFileModified(final String fileUrl, final Long prevModTime){
        try {
			File file = new File(fileUrl);
            Long prevMod = prevModTime;
			if (null==prevMod) prevMod = 0L;
			long lastMod = file.lastModified();
			if (lastMod != prevMod){
			    return true;
			}else{
			    return false;
			}
		} catch (Exception e) {
			return false;
		}
    }
}

