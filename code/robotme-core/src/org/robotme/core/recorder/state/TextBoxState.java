package org.robotme.core.recorder.state;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;

/**
 * @author Marcin Zduniak
 */
/* package scope */class TextBoxState extends DisplayableState {

    // TODO: consider adding also title field (and possibly others) as a TextBoxState significant field
    private String string;

    public TextBoxState(Displayable d) {
        storeInternalState(d);
    }

    private void storeInternalState(Displayable d) {
        final TextBox tb = (TextBox) d;
        string = tb.getString();
    }

    public boolean equals(DisplayableState ds) {
        if (null == ds || false == (ds instanceof TextBoxState)) {
            return false;
        }

        final TextBoxState tbs = (TextBoxState) ds;
        return string.equals(tbs.string);
    }

	public LogEntry createLogEntry() {
		return new TextBoxModificationLogEntry(LogHandler.INTERNAL_LEVEL, string);
	}

}
