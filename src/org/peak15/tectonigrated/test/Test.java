package org.peak15.tectonigrated.test;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.io.comparator.NameFileComparator;

public class Test {
	public static void main(String[] args) {
		// rename current to render number
		File current = new File("E:\\minecraft_server\\plugins\\Tectonigrated\\backups", "current");
		File num = new File("E:\\minecraft_server\\plugins\\Tectonigrated\\backups", Integer.toString(30));
		current.renameTo(num);
		
		// remove old backups to conform with numBackups
		if(2 > -1) {
			File backups = new File("E:\\minecraft_server\\plugins\\Tectonigrated\\backups");
			File[] files = backups.listFiles();
			Arrays.sort(files, NameFileComparator.NAME_REVERSE);
			
			int fileCount = 0;
			for(File file : files) {
				fileCount++;
				
				if(fileCount > 2) {
					// delete the backup
					deleteDir(file);
				}
			}
		}
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
