// This is an synchronous thread inside the Bukkit API space.

package org.peak15.tectonigrated;

import org.bukkit.command.ConsoleCommandSender;

public class RenderTask implements Runnable {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	private BackupWorker backupWorker;
	
	public RenderTask(Tectonigrated instance) {
		plugin = instance;
		backupWorker = new BackupWorker(plugin);
	}
	
	@Override
	public void run() {
		if(plugin.renderInProgress) {
			plugin.warnLog("A render was attempted while another was in progres.");
			return;
		}
		
		plugin.renderInProgress = true;
		
		int backupCount = plugin.allocRenderNumber();
		plugin.logCast("Render number " + backupCount + " started.");
		
		// save all and disable level saving
		ConsoleCommandSender sender = new ConsoleCommandSender(plugin.getServer());
		plugin.getServer().dispatchCommand(sender, "save-all");
		plugin.getServer().dispatchCommand(sender, "save-off");
		
		Thread bw = new Thread(backupWorker);
		bw.start();
	}

}
