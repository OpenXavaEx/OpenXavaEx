package org.openxava.ex.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringTemplateTest {

	@Test
	public void testGetMatches() {
		StringTemplate st;
		String tmpl;
		String[] vars, inOrder;

		tmpl = "The greeting from ${Teacher}: Hello, ${Student}! I'm ${Teacher}.";
		st = new StringTemplate(tmpl, StringTemplate.REGEX_PATTERN_JAVA_STYLE);
		vars = st.getVariables();
		assertEquals("Teacher", vars[0]);
		assertEquals("Student", vars[1]);
		assertEquals(2, vars.length);
		inOrder = st.getVariablesInOrder();
		assertEquals("Teacher", inOrder[0]);
		assertEquals("Student", inOrder[1]);
		assertEquals("Teacher", inOrder[2]);
		assertEquals(3, inOrder.length);

		tmpl = "The greeting from $Teacher: Hello, $Student! I'm $Teacher.";
		st = new StringTemplate(tmpl, StringTemplate.REGEX_PATTERN_UNIX_SHELL_STYLE);
		vars = st.getVariables();
		assertEquals("Teacher", vars[0]);
		assertEquals("Student", vars[1]);
		assertEquals(2, vars.length);
		inOrder = st.getVariablesInOrder();
		assertEquals("Teacher", inOrder[0]);
		assertEquals("Student", inOrder[1]);
		assertEquals("Teacher", inOrder[2]);
		assertEquals(3, inOrder.length);
	}

	@Test
	public void testSetVariable_and_GetParseResult() {
		StringTemplate st;
		String tmpl, replaced;

		tmpl = "The greeting from ${Teacher}: Hello, ${Student}! I'm ${Teacher}, Are you ${OK}?";
		st = new StringTemplate(tmpl, StringTemplate.REGEX_PATTERN_JAVA_STYLE);
		
		st.setVariable("Student", "Tom");
		st.setVariable("Teacher", "Brown");
		replaced = st.getParseResult();

		assertEquals("The greeting from Brown: Hello, Tom! I'm Brown, Are you ${OK}?", replaced);
	}

	@Test
	public void testReset() throws Exception{
		String s, s2;
		StringTemplate st;

		s = "The greeting from ${Teacher}: Hello, ${Student}! I'm ${Teacher}.";
		st = new StringTemplate(s, StringTemplate.REGEX_PATTERN_JAVA_STYLE);
		
		st.setVariable("Student", "Tom");
		st.setVariable("Teacher", "Brown");
		s2 = st.getParseResult();
		assertEquals("The greeting from Brown: Hello, Tom! I'm Brown.", s2);
		
		//Remember value before Reset
		st.setVariable("Student", "Jack");
		s2 = st.getParseResult();
		assertEquals("The greeting from Brown: Hello, Jack! I'm Brown.", s2);
		
		//Clean values after Reset 之后
		st.reset();
		st.setVariable("Student", "Jack");
		s2 = st.getParseResult();
		assertEquals("The greeting from ${Teacher}: Hello, Jack! I'm ${Teacher}.", s2);
	}

}
