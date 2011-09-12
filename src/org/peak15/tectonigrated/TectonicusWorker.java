// This is an asynchronous thread outside the Bukkit API space.
// This interfaces with the Tectonicus JAR and does the actual rendering.

package org.peak15.tectonigrated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

public class TectonicusWorker implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public TectonicusWorker(Tectonigrated instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		// Get arguments
		List<String> args = parseArgs(plugin.tectonicusCmd);
		
		// Start process
		ProcessBuilder pb = new ProcessBuilder(args);
		//TODO: Handle stdout and stderr properly.
		pb.redirectErrorStream(true);
		Process tProcess;
		try {
			tProcess = pb.start();
			
			// Handle output
			BufferedReader tReader = new BufferedReader(new InputStreamReader(tProcess.getInputStream()));
			String line;
			while((line = tReader.readLine()) != null) {
				System.out.println(line);
			}
			
			// Wait for thread to exit.
			tProcess.waitFor();
			
			if(tProcess.exitValue() != 0) {
				throw new IOException("Rendering failed.");
			}
		} catch(Exception e) {
			String msg = "Failed to run Tectonicus.";
			plugin.errLog(msg);
			e.printStackTrace();
			plugin.broadcast(ChatColor.RED + msg);
			
			plugin.renderInProgress = false;
			return;
		}
		
		//TODO: rename current to render number
		//TODO: remove old backups to conform with numBackups
		
		plugin.logCast("Render complete.");
		if(!plugin.broadcastMessage.equals("")) {
			plugin.logCast(plugin.broadcastMessage);
		}
		
		plugin.renderInProgress = false;
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
