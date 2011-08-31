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
		plugin.renderInProgress = true;
		
		//TODO: Get actual backup count.
		int backupCount = 1337;
		plugin.logCast("Render number " + backupCount + " started.");
		
		//TODO: save all and disable level saving
		
		Thread bw = new Thread(backupWorker);
		bw.start();
	}

}
