// Sent if the backup failed.

package org.peak15.tectonigrated;

import org.bukkit.event.Event;

public class BackupFailedEvent extends Event {
	private static final long serialVersionUID = 5063607442927912520L;

	public BackupFailedEvent(String event) {
        super(event);
    }
}
