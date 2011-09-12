package org.peak15.tectonigrated.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String cmd = "java -jar plugins/Tectonigrated/Tectonicus_v2.02.jar config=\"plugins/Tecton igrated/tectonicus.xml\"";
		List<String> list = parseArgs(cmd);
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses a command line into it's arguments, respecting quotes. Does not respect escapes.
	 * @param args Command line to parse.
	 * @return The list of arguments.
	 */
	public static List<String> parseArgs(String args) {
		List<String> matchList = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(args);
		while (regexMatcher.find()) {
			if (regexMatcher.group(1) != null) {
				// Add double-quoted string without the quotes
				matchList.add(regexMatcher.group(1));
			} else if (regexMatcher.group(2) != null) {
				// Add single-quoted string without the quotes
				matchList.add(regexMatcher.group(2));
			} else {
				// Add unquoted word
				matchList.add(regexMatcher.group());
			}
		}
		
		return matchList;
	}

}
