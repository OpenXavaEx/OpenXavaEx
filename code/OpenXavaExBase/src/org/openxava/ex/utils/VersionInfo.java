package org.openxava.ex.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class VersionInfo {
	private static String oxVersion = null;
	private static Boolean isDevVersion = null;
	
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
	
	public static boolean isDevVersion(){
		if (null==isDevVersion){
			URL res = VersionInfo.class.getResource("/version.properties");
			int devIdx = res.getPath().indexOf("OpenXavaDEV");
			isDevVersion = (devIdx > 0);
		}
		return isDevVersion;
	}
}
