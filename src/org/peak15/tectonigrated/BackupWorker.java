// This is an asynchronous thread outside the Bukkit API space.

package org.peak15.tectonigrated;

public class BackupWorker implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public BackupWorker(Tectonigrated instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		plugin.logCast("Backup started...");
		
		//TODO: Replace dummy backup routine.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Send event to show our backup is done
		plugin.getServer().getPluginManager().callEvent(new BackupFinishedEvent("BackupFinishedEvent"));
		plugin.logCast("Backup finished.");
	}

}
