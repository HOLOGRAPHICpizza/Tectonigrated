// This is an asynchronous thread outside the Bukkit API space.

package org.peak15.tectonigrated;

public class BackupWorker implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public BackupWorker(Tectonigrated instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		plugin.renderInProgress = true;
		plugin.log.info("Backup started");
		
		//TODO: Replace dummy backup routine.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		plugin.log.info("Backup ended");
		plugin.getServer().getPluginManager().callEvent(new BackupFinishedEvent());
	}

}
