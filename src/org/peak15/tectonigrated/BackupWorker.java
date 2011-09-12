// This is an asynchronous thread outside the Bukkit API space.

package org.peak15.tectonigrated;

import java.io.*;

import org.bukkit.ChatColor;

public class BackupWorker implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public BackupWorker(Tectonigrated instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		plugin.logCast("Backup started...");
		
		// Backup the worlds
		for(String world : plugin.renderMaps) {
			File src = new File(world);
			File dest = new File(plugin.backupPath, "current/" + world);
			try {
				dest.mkdirs();
				copyDirectory(src, dest);
				
			} catch (IOException e) {
				String msg = "Failed to backup world, aborting render.";
				plugin.errLog(msg);
				plugin.broadcast(ChatColor.RED + msg);
				e.printStackTrace();
				
				// Send event to show our backup failed
				plugin.getServer().getPluginManager().callEvent(new BackupFailedEvent("BackupFailedEvent"));
				return;
			}
		}
		
		// Send event to show our backup is done
		plugin.getServer().getPluginManager().callEvent(new BackupFinishedEvent("BackupFinishedEvent"));
	}
	
	/**
	 * Copies a directory from one location to another.
	 * Ninja'd from: http://www.techiegyan.com/2008/09/24/how-to-copy-a-directory-from-one-location-to-another-location-java/
	 * @author Aditya
	 * 
	 * @param sourceLocation Location to copy from.
	 * @param targetLocation Location to copy to.
	 * @throws IOException
	 */
	public void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			
			String[] children = sourceLocation.list();
			for (int i=0; i<children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
			}
		} else {
			InputStream in = new FileInputStream(sourceLocation);
			
			File outDir = targetLocation.getParentFile();
			if(!outDir.exists()) {
				outDir.mkdirs();
			}
			
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
