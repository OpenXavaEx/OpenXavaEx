package org.openxava.demoapp.model.md.enums;

public enum MeasureCategory {
	L_LENGTH, V_VOLUME, W_WEIGHT, Q_QUANTITY;
	
	/** The first letter of enums, to store into database */
	public static final String LETTERS = "LVWQ";
}