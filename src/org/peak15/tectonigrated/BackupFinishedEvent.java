// Sent when the backup is finished.

package org.peak15.tectonigrated;

import org.bukkit.event.Event;

public class BackupFinishedEvent extends Event {
	private static final long serialVersionUID = 8025216657697629231L;

	public BackupFinishedEvent() {
        super(Event.Type.CUSTOM_EVENT);
    }
}
