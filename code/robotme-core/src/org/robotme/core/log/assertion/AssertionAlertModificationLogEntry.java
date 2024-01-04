package org.robotme.core.log.assertion;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Displayable;

import org.robotme.core.log.entries.AlertModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class AssertionAlertModificationLogEntry extends BaseAssertion {

    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine) {
        final Displayable d = replayingEngine.getCurrentDisplayable();
        if (null == d || !(d instanceof Alert)) {
            return createAssertionFailure("Alert is not current displayable");
        }
        final Alert a = (Alert) d;
        final AlertModificationLogEntry aLogEntry = (AlertModificationLogEntry) logEntry;
        if (!aLogEntry.getString().equals(a.getString())) {
            return createAssertionFailure("Alert string has different value", aLogEntry.getString(), a.getString());
        }
        if (!aLogEntry.getTitle().equals(a.getTitle())) {
            return createAssertionFailure("Alert title has different value", aLogEntry.getTitle(), a.getTitle());
        }
        // TODO: consider if other values should be also taken into consideration when assertion is verified
        return null;
    }

}
