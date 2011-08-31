// This is an asynchronous thread outside the Bukkit API space.

package org.peak15.tectonigrated;

import java.io.*;

public class BackupWorker implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public BackupWorker(Tectonigrated instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		plugin.logCast("Backup started...");
		
		
		
		// Send event to show our backup is done
		plugin.getServer().getPluginManager().callEvent(new BackupFinishedEvent("BackupFinishedEvent"));
	}
	
	/**
	 * Copies a directory from one location to another.
	 * Ninja'd from: http://www.techiegyan.com/2008/09/24/how-to-copy-a-directory-from-one-location-to-another-location-java/
	 * @author Aditya
	 * 
	 * @param sourceLocation Location to copy from.
	 * @param targetLocation Location to copy to. Will be created if it does not exist.
	 * @throws IOException
	 */
	public void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}
			
			String[] children = sourceLocation.list();
			for (int i=0; i<children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
			}
		} else {
			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);
			
			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}
}
