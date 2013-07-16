package org.openxava.ex.model.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.openxava.ex.utils.Misc;

public class ReportQueryUtilsTest extends TestCase {
	public void testMergeFields() {
		List<String> result;
		List<String> fields = Misc.$list("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
		
		result = ReportQueryUtils.mergeFields(Misc.$list("*", "E", "F", "*"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("*", "D", "*"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("*"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("A", "*"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("*", "K"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I, J, K]", result.toString());
		
		result = ReportQueryUtils.mergeFields(Misc.$list("*", "D", "G", "*"), fields);
		assertEquals("[A, B, C, D, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("*", "D", "G"), fields);
		assertEquals("[A, B, C, D, G]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("D", "G", "*"), fields);
		assertEquals("[D, G, H, I, J, K]", result.toString());

		result = ReportQueryUtils.mergeFields(Misc.$list("*", "C", "*" , "I", "*"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("C", "*" , "I", "*"), fields);
		assertEquals("[C, D, E, F, G, H, I, J, K]", result.toString());
		result = ReportQueryUtils.mergeFields(Misc.$list("*", "C", "*" , "I"), fields);
		assertEquals("[A, B, C, D, E, F, G, H, I]", result.toString());

		result = ReportQueryUtils.mergeFields(Misc.$list("A", "C", "*" , "I", "K"), fields);
		assertEquals("[A, C, D, E, F, G, H, I, K]", result.toString());

		result = ReportQueryUtils.mergeFields(Misc.$list("*", "C", "*" , "I", "*"), new ArrayList<String>());
		assertEquals("[C, I]", result.toString());
	}

}
