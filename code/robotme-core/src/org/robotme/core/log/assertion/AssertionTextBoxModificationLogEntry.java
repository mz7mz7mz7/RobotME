package org.robotme.core.log.assertion;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class AssertionTextBoxModificationLogEntry extends BaseAssertion {

    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine) {
        final Displayable d = replayingEngine.getCurrentDisplayable();
        if (null == d || !(d instanceof TextBox)) {
            return createAssertionFailure("TextBox is not current displayable");
        }
        final TextBox textBox = (TextBox) d;
        final TextBoxModificationLogEntry textBoxLogEntry = (TextBoxModificationLogEntry) logEntry;
        if (!textBoxLogEntry.getString().equals(textBox.getString())) {
            return createAssertionFailure("TextBox has different value", textBoxLogEntry.getString(), textBox
                    .getString());
        }
        return null;
    }

}
