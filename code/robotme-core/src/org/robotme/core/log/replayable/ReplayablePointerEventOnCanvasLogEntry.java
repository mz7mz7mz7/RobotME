package org.robotme.core.log.replayable;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.replaying.NakedCanvas;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class ReplayablePointerEventOnCanvasLogEntry implements Replayable {

    public void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException {
        final Displayable d = replayingEngine.getCurrentDisplayable();
        if (!(d instanceof Canvas)) {
            throw new RuntimeException("Ooops.. current displayable != Canvas ???");
        }
        if (!(d instanceof NakedCanvas)) {
            throw new RuntimeException("Ooops.. current displayable != NakedCanvas ??? I can't replay key event");
        }

        final NakedCanvas pc = (NakedCanvas) d;
        final PointerEventOnCanvasLogEntry pe = (PointerEventOnCanvasLogEntry) logEntry;
        final int x = pe.getX();
        final int y = pe.getY();

        switch (pe.getType()) {
        case PointerEventOnCanvasLogEntry.POINTER_PRESSED_TYPE:
            pc.pointerPressed(x, y);
            break;
        case PointerEventOnCanvasLogEntry.POINTER_RELEASED_TYPE:
            pc.pointerReleased(x, y);
            break;
        case PointerEventOnCanvasLogEntry.POINTER_DRAGGED_TYPE:
            pc.pointerDragged(x, y);
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unrecognized PointerEvent type: " + pe.getType());
            }
        }
    }

}
