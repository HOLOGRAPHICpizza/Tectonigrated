// This is an asynchronous thread outside the Bukkit API space.
// This interfaces with the Tectonicus JAR and does the actual rendering.

package org.peak15.tectonigrated;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.comparator.NameFileComparator;
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
		
		// rename current to render number
		File current = new File(plugin.backupPath, "current");
		File num = new File(plugin.backupPath, Integer.toString(plugin.currentBackupCount));
		current.renameTo(num);
		
		// remove old backups to conform with numBackups
		if(plugin.numBackups > -1) {
			File backups = new File(plugin.backupPath);
			File[] files = backups.listFiles();
			Arrays.sort(files, NameFileComparator.NAME_REVERSE);
			
			int fileCount = 0;
			for(File file : files) {
				fileCount++;
				
				if(fileCount > plugin.numBackups) {
					// delete the backup
					deleteDir(file);
				}
			}
		}
		
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
	
	/**
	 * Recursively delete a directory.
	 * Ninja'd from: http://www.exampledepot.com/egs/java.io/DeleteDir.html
	 * @param dir Directory to recursively delete.
	 * @return True if successful, false otherwise.
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		
		// The directory is now empty so delete it
		return dir.delete();
	}
}
