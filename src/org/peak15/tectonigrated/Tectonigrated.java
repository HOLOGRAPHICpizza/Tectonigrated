package org.peak15.tectonigrated;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Tectonigrated extends JavaPlugin {
	public Logger log = Logger.getLogger("Minecraft");
	public Configuration config;
	
	// User defined variables:
	
	// Frequency in minutes to run Tectonigrated. 0 disables auto-runs.
	public int runFrequencyMins;		// Default: 720 mins (12 hrs)
	
	// Number of backups to keep. -1 keeps all backups.
	public int numBackups;			// Default: 0
	
	// Path to save backups to.	// Default: plugins/Tectonigrated/backups
	public String backupPath;
	
	public void onEnable() {
		// Load configuration
		config = getConfiguration();
		runFrequencyMins = config.getInt("runFrequencyMins", 720);
		numBackups = config.getInt("numBackups", 0);
		backupPath = config.getString("backupPath", "plugins/Tectonigrated/backups");
		config.save();
		
		log.info("Tectonigrated enabled.");
		log.info(runFrequencyMins + " " + numBackups + " " + backupPath);
	}
	
	public void onDisable() {
		log.info("Tectonigrated disabled.");
	}
}
