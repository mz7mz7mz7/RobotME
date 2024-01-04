package org.robotme.core.log.assertion;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;

import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class AssertionDisplayableChangedLogEntry extends BaseAssertion {

    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine) {
        final Displayable d = replayingEngine.getCurrentDisplayable();

        if (null == d) {
            return createAssertionFailure("CurrentDisplayable must be set");
        }

        final DisplayableChangedLogEntry dispLogEntry = (DisplayableChangedLogEntry) logEntry;
        final byte expectedType = dispLogEntry.getType();
        if (DisplayableChangedLogEntry.ALERT_TYPE == expectedType) {
            if (!(d instanceof Alert)) {
                return createAssertionFailure("CurrentDisplayable is not Alert type");
            }
        } else if (DisplayableChangedLogEntry.CANVAS_TYPE == expectedType) {
            if (!(d instanceof Canvas)) {
                return createAssertionFailure("CurrentDisplayable is not Canvas type");
            }
        } else if (DisplayableChangedLogEntry.FORM_TYPE == expectedType) {
            if (!(d instanceof Form)) {
                return createAssertionFailure("CurrentDisplayable is not Form type");
            }
        } else if (DisplayableChangedLogEntry.LIST_TYPE == expectedType) {
            if (!(d instanceof List)) {
                return createAssertionFailure("CurrentDisplayable is not List type");
            }
        } else if (DisplayableChangedLogEntry.TEXTBOX_TYPE == expectedType) {
            if (!(d instanceof TextBox)) {
                return createAssertionFailure("CurrentDisplayable is not TextBox type");
            }
        } else {
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unrecognized expected displayable type");
            }
        }

        if (!d.getTitle().equals(dispLogEntry.getTitle())) {
            return createAssertionFailure("Title of current displayable differ from expected one", dispLogEntry
                    .getTitle(), d.getTitle());
        }

        return null;
    }

}
