/*
 * (c) Copyright 2005 thinkbase.net . All Rights Reserved.
 * $ Id: ClassFileVO.java, v 1.1, @2005-6-26 13:30:27, thinkbase.net Exp $
 */
package org.openxava.ex.cl.impl;



/**
 * @author thinkbase.net
 * @version $Revision: 1.1 $
 */
public class ClassFileVO {
    private String sourceFile;	//Always, source file should be a *.class file or *.jar file
    private byte[] classBytes;
	private Long timestamp;
    
    public ClassFileVO(String sourceFile, byte[] classBytes, Long timestamp){
        this.sourceFile = sourceFile;
        this.timestamp = timestamp;
        this.classBytes = classBytes;
    }

    public byte[] getClassBytes() {
		return classBytes;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public Long getTimestamp() {
		return timestamp;
	}
}
