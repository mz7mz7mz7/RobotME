package org.robotme.core.log.replayable;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import org.robotme.core.log.entries.ListModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class ReplayableListModificationLogEntry implements Replayable {

    public void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException {
        final Displayable disp = replayingEngine.getCurrentDisplayable();
        if (disp instanceof List) {
            final List l = (List) disp;
            final ListModificationLogEntry le = (ListModificationLogEntry) logEntry;
            l.setSelectedFlags(le.getSelectedFlags());
        } else {
            throw new RuntimeException("Ooops... Current displayable != List ???");
        }
    }

}
