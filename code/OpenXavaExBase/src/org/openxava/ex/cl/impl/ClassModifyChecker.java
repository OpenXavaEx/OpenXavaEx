/*
 * (c) Copyright 2005 thinkbase.net . All Rights Reserved.
 * $ Id: ClassModifyChecker.java, v 1.1, @2005-6-24 22:26:42, thinkbase.net Exp $
 */
package org.openxava.ex.cl.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author thinkbase.net
 * @version $Revision: 1.1 $
 */
public class ClassModifyChecker {
	private final static Logger logger = LoggerFactory.getLogger( ClassModifyChecker.class );

    private Map<String, Long> memory = new HashMap<String, Long>();
    private Map<String, Long> newFileDetectMemory = new HashMap<String, Long>();
    
    public synchronized void rememberFile(final String fileUrl, Long timestamp){
        memory.put(fileUrl, timestamp);
    }
    public boolean isFileChanged(){
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
    
    public boolean isNewFiles(){
    	//Check the folder's modification mask to detect new file
        Iterator<Map.Entry<String, Long>> itr = newFileDetectMemory.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String, Long> en = itr.next();
            String key = en.getKey();
            Long value = en.getValue();
            long newMask = getModificationMask(new File(key));
            if (! value.equals(newMask)){
                logger.info("Directory " + key + " was changed.");
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Remember the total amount of all files' last modified time, as the modification mask of the folder.<br/>
     * <pre>Long.MAX_VALUE / (new Date()).getTime() = 6740748.9</pre>
     * @param dir
     * @throws IOException 
     */
    protected void rememberTopFolder(File dir) throws IOException{
    	if (! dir.isDirectory()){
    		throw new RuntimeException("["+dir+"] is not a directory!");
    	}
    	newFileDetectMemory.put(dir.getCanonicalPath(), getModificationMask(dir));    	
    }
    private long getModificationMask(File dir){
    	long result = 0L;
    	@SuppressWarnings("unchecked")
		Iterator<File> files = FileUtils.iterateFiles(dir, new String[]{"class", "properties"}, true);
    	while (files.hasNext()){
    		File f = files.next();
    		result += f.lastModified();
    	}
    	return result;
    }
}

