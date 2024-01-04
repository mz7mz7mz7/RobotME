package org.robotme.core.log.replayable;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;

import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.NakedCanvas;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.Assert;
import org.robotme.core.util.MIDPUtils;

/**
 * @author Marcin Zduniak
 */
public class ReplayableKeyEventOnCanvasLogEntry implements Replayable {

    public void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException {
        final Displayable d = replayingEngine.getCurrentDisplayable();

        if (!(d instanceof Canvas)) {
            throw new RuntimeException("Ooops.. current displayable != Canvas ???");
        }
        if (!(d instanceof NakedCanvas)) {
            throw new RuntimeException("Ooops.. current displayable != NakedCanvas ??? I can't replay key event");
        }

        final NakedCanvas pc = (NakedCanvas) d;
        final KeyEventOnCanvasLogEntry ke = (KeyEventOnCanvasLogEntry) logEntry;

        if (Assert.ASSERT_ON) {
            Assert.isFalse(KeyEventOnCanvasLogEntry.NOT_SELECTED_KEY == ke.getKeyCode()
                    && KeyEventOnCanvasLogEntry.NOT_SELECTED_KEY == ke.getGameAction(),
                    "One of keyCode or gameAction must be set.");
            Assert.isFalse(KeyEventOnCanvasLogEntry.NOT_SELECTED_KEY != ke.getKeyCode()
                    && KeyEventOnCanvasLogEntry.NOT_SELECTED_KEY != ke.getGameAction(),
                    "Only one of keyCode or gameAction must be set.");
        }

        int keyCode = ke.getKeyCode();
        if (KeyEventOnCanvasLogEntry.NOT_SELECTED_KEY == keyCode) {
            keyCode = MIDPUtils.getKeyCode(ke.getGameAction());
        }

        switch (ke.getType()) {
        case KeyEventOnCanvasLogEntry.KEY_PRESSED_TYPE:
            pc.keyPressed(keyCode);
            break;
        case KeyEventOnCanvasLogEntry.KEY_RELEASED_TYPE:
            pc.keyReleased(keyCode);
            break;
        case KeyEventOnCanvasLogEntry.KEY_REPEATED_TYPE:
            pc.keyRepeated(keyCode);
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unrecognized KeyEvent type: " + ke.getType());
            }
        }
    }

}
