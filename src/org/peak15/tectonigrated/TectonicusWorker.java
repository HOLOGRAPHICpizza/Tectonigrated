// This is an asynchronous thread outside the Bukkit API space.
// This interfaces with the Tectonicus JAR and does the actual rendering.

package org.peak15.tectonigrated;

public class TectonicusWorker implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public TectonicusWorker(Tectonigrated instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		plugin.logCast("Rendering map with Tectonicus...");
		
		//TODO: Replace dummy render routine.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		plugin.logCast("Render complete.");
		if(!plugin.broadcastMessage.equals("")) {
			plugin.logCast(plugin.broadcastMessage);
		}
		
		plugin.renderInProgress = false;
	}

}
