package org.peak15.tectonigrated;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class Tectonigrated extends JavaPlugin {
	// Enable debug messages.
	public static final boolean DEBUG = true;
	
	public boolean renderInProgress = false;
	
	private Configuration config;
	private RenderTask renderTask = new RenderTask(this);
	private final TICustomEventListener tICustomEventListener = new TICustomEventListener(this);
	private PluginDescriptionFile pdfFile;
	private Logger log = Logger.getLogger("Minecraft");
	
	// User defined variables:
	
	// Period in minutes between automatic runs of Tectonigrated. 0 disables auto-runs.
	public int runPeriodMins;	// Default: 720 mins (12 hrs)
	
	// Number of backups to keep. -1 keeps all backups.
	public int numBackups;			// Default: 0
	
	// Path to save backups to.
	public String backupPath;		// Default: plugins/Tectonigrated/backups
	
	// Current backup number, should not be changed by user.
	public int currentBackupCount;	// Default: 1
	
	public void onEnable() {
		pdfFile = this.getDescription();
		
		// Load configuration
		config = getConfiguration();
		runPeriodMins = config.getInt("runPeriodMins", 720);
		numBackups = config.getInt("numBackups", 0);
		backupPath = config.getString("backupPath", "plugins/Tectonigrated/backups");
		currentBackupCount = config.getInt("currentBackupCount", 1);
		config.save();
		
		// Catch custom events
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.CUSTOM_EVENT, tICustomEventListener, Event.Priority.Normal, this);
		
		// Schedule the renders
		//long period = 200;
		//this.getServer().getScheduler().scheduleSyncRepeatingTask(this, renderTask, period, period);
		
		// Show enabled message
		infoLog("Version " + pdfFile.getVersion() + " enabled.");
		dbgOut(runPeriodMins + " " + numBackups + " " + backupPath);
	}
	
	// Render the map on command.
	//TODO: onCommand seems deprecated, should be updated to use getCommand.
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {	
		if(cmd.getName().equalsIgnoreCase("rendermap")){
			String senderName;
			if(sender instanceof Player) {
				senderName = ( (Player) sender ).getName();
			}
			else {
				senderName = "Console";
			}
			
			infoLog(senderName + " has requested a map render.");
			
			if(!renderInProgress) {
				Thread rtt = new Thread(renderTask);
				rtt.start();
				return true;
			}
			else {
				warnLog("A render was attempted while another was in progres.");
				sender.sendMessage(ChatColor.RED + "A render is already in progress.");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Logs a message at level INFO.
	 * @param msg Message to log.
	 */
	public void infoLog(String msg) {
		log.info(pdfFile.getName() + ": " + msg);
	}
	
	/**
	 * Logs a message at level WARNING.
	 * @param msg Message to log.
	 */
	public void warnLog(String msg) {
		log.warning(pdfFile.getName() + ": " + msg);
	}
	
	/**
	 * Logs an error message at level SEVERE.
	 * @param msg Message to log.
	 */
	public void errLog(String msg) {
		log.severe(pdfFile.getName() + ": " + msg);
	}
	
	/**
	 * Prints a debug message if DEBUG is true.
	 * @param msg Debug message to print.
	 */
	public void dbgOut(String msg) {
		if(DEBUG) {
			log.warning("DEBUG: " + msg);
		}
	}
	
	public void onDisable() {
		infoLog("Disabled.");
	}
}
