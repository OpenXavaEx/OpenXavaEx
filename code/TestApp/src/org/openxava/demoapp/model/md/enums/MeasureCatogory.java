package org.openxava.demoapp.model.md.enums;

public enum MeasureCatogory {
	L_LENGTH, V_VOLUMN, W_WEIGHT, Q_QUANTITY;
	
	/** The first letter of enums, to store into database */
	public static final String LETTERS = "LVWQ";
}