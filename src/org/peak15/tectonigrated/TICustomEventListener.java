package org.peak15.tectonigrated;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class TICustomEventListener extends CustomEventListener implements Listener {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	private TectonicusWorker tectonicusWorker;
	
	public TICustomEventListener(Tectonigrated instance) {
		plugin = instance;
		tectonicusWorker = new TectonicusWorker(plugin);
	}
	
	@Override
	public void onCustomEvent(Event event) {
		if(event instanceof BackupFinishedEvent) {
			// enable level saving
			ConsoleCommandSender sender = new ConsoleCommandSender(plugin.getServer());
			plugin.getServer().dispatchCommand(sender, "save-on");
			
			plugin.logCast("Backup finshed, rendering with Tectonicus...");
			
			// Now we can render! :D
			Thread tw = new Thread(tectonicusWorker);
			tw.start();
		}
	}
}
