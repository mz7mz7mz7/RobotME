package org.robotme.core.recorder.state;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.CanvasModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.util.VariousUtils;

/**
 * @author Marcin Zduniak
 */
/* package scope */class CanvasState extends DisplayableState {

    private String title;

    private String tickerString;

    public CanvasState(Displayable d) {
        storeInternalState(d);
    }

    private void storeInternalState(Displayable d) {
        final Canvas c = (Canvas) d;
        title = c.getTitle();
        if (null != c.getTicker()) {
            tickerString = c.getTicker().getString();
        }
    }

    public boolean equals(DisplayableState ds) {
        if (null == ds || false == (ds instanceof CanvasState)) {
            return false;
        }

        final CanvasState cs = (CanvasState) ds;
        if (VariousUtils.equalObjects(title, cs.title)) {
            return VariousUtils.equalObjects(tickerString, cs.tickerString);
        } else {
            return false;
        }

    }

    public LogEntry createLogEntry() {
        return new CanvasModificationLogEntry(LogHandler.INTERNAL_LEVEL, title, tickerString);
    }

}
