package org.peak15.tectonigrated;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

public class TICustomEventListener extends CustomEventListener {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public TICustomEventListener(Tectonigrated instance) {
		plugin = instance;
	}
	
	public void onCustomEvent(Event event) {
		plugin.log.info(event.toString());
	}
}
