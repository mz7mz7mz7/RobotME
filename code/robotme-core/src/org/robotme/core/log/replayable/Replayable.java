package org.robotme.core.log.replayable;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public interface Replayable {
	void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException;
}
