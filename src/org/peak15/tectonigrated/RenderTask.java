// This is an synchronous thread inside the Bukkit API space.

package org.peak15.tectonigrated;

public class RenderTask implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	private BackupWorker backupWorker;
	
	public RenderTask(Tectonigrated instance) {
		plugin = instance;
		backupWorker = new BackupWorker(plugin);
	}
	
	@Override
	public void run() {
		plugin.log.info("Rendering teh map lulz");
		plugin.log.info("Btw bro, this thread blocks.");
		
		Thread bw = new Thread(backupWorker);
		bw.start();
	}

}
