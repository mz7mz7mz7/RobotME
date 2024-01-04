package org.robotme.core.log.replayable;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class ReplayableTextBoxModificationLogEntry implements Replayable {

	public void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException {
		final Displayable disp = replayingEngine.getCurrentDisplayable();
		if (disp instanceof TextBox) {
			final TextBox tb = (TextBox) disp;
			final TextBoxModificationLogEntry le = (TextBoxModificationLogEntry) logEntry;
			tb.setString(le.getString());
		} else {
			throw new RuntimeException("Ooops... Current displayable != TextBox ???");
		}
	}

}
