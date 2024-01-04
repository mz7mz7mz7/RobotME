package org.robotme.core.recorder.state;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.game.GameCanvas;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public abstract class DisplayableState {

    protected DisplayableState() {
        super();
    }

    public static DisplayableState createDisplayableState(Displayable d) {
        if (Assert.ASSERT_ON) {
            Assert.notNull(d);
        }
        if (d instanceof TextBox) {
            return new TextBoxState(d);
        } else if (d instanceof GameCanvas) {
            /*
             * Currently {@link GameCanvas} is treated as regular {@link Canvas} but 
             * in the future we should consider this aspect. 
             */
            return new CanvasState(d);
        }  else if (d instanceof Canvas) {
            return new CanvasState(d);
        } else if (d instanceof List) {
            return new ListState(d);
        } else if (d instanceof Form) {
            return new FormState(d);
        } else if (d instanceof Alert) {
            return new AlertState(d);
        } else {
            throw new RuntimeException("Unrecognized Displayable type: " + d.getClass().getName());
        }
    }

    public abstract boolean equals(DisplayableState ds);

    public abstract LogEntry createLogEntry();

}
