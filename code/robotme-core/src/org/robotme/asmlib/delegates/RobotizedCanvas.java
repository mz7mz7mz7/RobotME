package org.robotme.asmlib.delegates;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.core.replaying.NakedCanvas;

/**
 * This class implements all the functionality needed to capture and generate events to {@link Canvas} instances.
 * 
 * All methods with regular signatures are copied verbatim to the rewritten class. Methods starting with
 * <code>$robotized$</code> prefix are replaced with methods from the rewritten class if a corresponding method
 * without the prefix exists.
 * 
 * This method is abstract because we are not interested in implementing {@link Canvas#paint(Graphics)} method.
 * 
 * @author Dawid Weiss
 * @author Marcin Zduniak
 */
abstract class RobotizedCanvas extends Canvas implements NakedCanvas {
    /**
     * Our implementation of {@link Canvas#keyPressed(int)} method. This eventually delegates control to
     * {@link #$robotized$keyPressed(int)}.
     */
    public void keyPressed(int keyCode) {
        RobotMERecorder.getRecorderInstance().keyEventOnCanvas(KeyEventOnCanvasLogEntry.KEY_PRESSED_TYPE, keyCode);
        $robotized$keyPressed(keyCode);
    }

    /**
     * This method will be replaced by {@link #keyPressed(int)} method present in the actual target code. Default
     * implementation invokes the superclass.
     */
    protected final void $robotized$keyPressed(int keyCode) {
        super.keyPressed(keyCode);
    }

    public void keyReleased(int keyCode) {
        RobotMERecorder.getRecorderInstance().keyEventOnCanvas(KeyEventOnCanvasLogEntry.KEY_RELEASED_TYPE, keyCode);
        $robotized$keyReleased(keyCode);
    }

    protected final void $robotized$keyReleased(int keyCode) {
        super.keyReleased(keyCode);
    }

    public void keyRepeated(int keyCode) {
        RobotMERecorder.getRecorderInstance().keyEventOnCanvas(KeyEventOnCanvasLogEntry.KEY_REPEATED_TYPE, keyCode);
        $robotized$keyRepeated(keyCode);
    }

    protected final void $robotized$keyRepeated(int keyCode) {
        super.keyRepeated(keyCode);
    }

    public void pointerDragged(int x, int y) {
        RobotMERecorder.getRecorderInstance().pointerEventOnCanvas(PointerEventOnCanvasLogEntry.POINTER_DRAGGED_TYPE,
                x, y);
        $robotized$pointerDragged(x, y);
    }

    protected final void $robotized$pointerDragged(int x, int y) {
        super.pointerDragged(x, y);
    }

    public void pointerPressed(int x, int y) {
        RobotMERecorder.getRecorderInstance().pointerEventOnCanvas(PointerEventOnCanvasLogEntry.POINTER_PRESSED_TYPE,
                x, y);
        $robotized$pointerPressed(x, y);
    }

    protected final void $robotized$pointerPressed(int x, int y) {
        super.pointerPressed(x, y);
    }

    public void pointerReleased(int x, int y) {
        RobotMERecorder.getRecorderInstance().pointerEventOnCanvas(PointerEventOnCanvasLogEntry.POINTER_RELEASED_TYPE,
                x, y);
        $robotized$pointerReleased(x, y);
    }

    protected final void $robotized$pointerReleased(int x, int y) {
        super.pointerReleased(x, y);
    }
}
