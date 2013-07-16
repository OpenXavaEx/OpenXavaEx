package org.openxava.ex.utils;

import java.io.IOException;
import java.util.Properties;

public class VersionInfo {
	private static String oxVersion = null;
	
	/**
	 * Return the version of OpenXava
	 * @return
	 */
	public static String getOpenXavaVersion(){
		if (null==oxVersion){
			Properties p = new Properties();
			try {
				p.load(VersionInfo.class.getResourceAsStream("/version.properties"));
			} catch (IOException e) {
				Misc.throwRuntime(e);
			}
			oxVersion = p.getProperty("version");
		}
		return oxVersion;
	}
}
