package org.robotme.core.log.assertion;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;

import org.robotme.core.log.entries.CanvasModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.VariousUtils;

/**
 * @author Marcin Zduniak
 */
public class AssertionCanvasModificationLogEntry extends BaseAssertion {

    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine) {
        final Displayable d = replayingEngine.getCurrentDisplayable();
        if (null == d || !(d instanceof Canvas)) {
            return createAssertionFailure("Canvas is not current displayable");
        }
        final Canvas canvas = (Canvas) d;
        final CanvasModificationLogEntry canvasLogEntry = (CanvasModificationLogEntry) logEntry;
        if (!VariousUtils.equalObjects(canvasLogEntry.getTitle(), canvas.getTitle())) {
            return createAssertionFailure("Canvas has different title", canvasLogEntry.getTitle(), canvas.getTitle());
        }
        if (null == canvas.getTicker() && null == canvasLogEntry.getTickerString()) {
            return null;
        } else if (null == canvas.getTicker() || null == canvasLogEntry.getTickerString()) {
            return createAssertionFailure("Canvas has different tickers", canvasLogEntry.getTickerString(),
                    (null == canvas.getTicker() ? null : canvas.getTicker().getString()));
        } else {
            if (!VariousUtils.equalObjects(canvasLogEntry.getTickerString(), canvas.getTicker().getString())) {
                return createAssertionFailure("Canvas has different ticker string", canvasLogEntry.getTickerString(),
                        canvas.getTicker().getString());
            }
        }

        return null;
    }

}
