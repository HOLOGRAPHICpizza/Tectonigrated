package org.peak15.tectonigrated;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public class TICustomEventListener extends CustomEventListener implements Listener {
	private Tectonigrated plugin; // Refrence to the main plugin class.
	
	public TICustomEventListener(Tectonigrated instance) {
		plugin = instance;
	}
	
	@Override
	public void onCustomEvent(Event event) {
		plugin.dbgOut(event.toString());
	}
}
