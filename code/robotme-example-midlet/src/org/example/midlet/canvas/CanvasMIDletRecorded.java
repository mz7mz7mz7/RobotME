package org.example.midlet.canvas;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Ticker;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.recorder.RobotMERecorder;

/**
 * @author Marcin Zduniak
 */
public class CanvasMIDletRecorded extends MIDlet implements CommandListener {

    private final static Command CMD1_EXIT = new Command("Wyjscie", Command.BACK, 2);

    private final static Command CMD2_OK = new Command("OK", Command.SCREEN, 1);

    private static class MyCanvas extends Canvas {
        protected void paint(final Graphics g) {
            g.setColor(0xFF0000);
            g.fillRect(1, 1, 100, 100);
        }

        protected void keyPressed(int keyCode) {
            RobotMERecorder.getRecorderInstance().keyEventOnCanvas(KeyEventOnCanvasLogEntry.KEY_PRESSED_TYPE, keyCode);
            $robotized$keyPressed(keyCode);
        }

        // NOTE: method signature becomes private and final !
        private final void $robotized$keyPressed(int keyCode) {
            System.out.println("KeyPressed: " + keyCode);
        }

        protected void keyReleased(int keyCode) {
            RobotMERecorder.getRecorderInstance().keyEventOnCanvas(KeyEventOnCanvasLogEntry.KEY_RELEASED_TYPE, keyCode);
            super.keyReleased(keyCode);
        }

        protected void keyRepeated(int keyCode) {
            RobotMERecorder.getRecorderInstance().keyEventOnCanvas(KeyEventOnCanvasLogEntry.KEY_REPEATED_TYPE, keyCode);
            super.keyRepeated(keyCode);
        }

        protected void pointerDragged(int x, int y) {
            RobotMERecorder.getRecorderInstance().pointerEventOnCanvas(PointerEventOnCanvasLogEntry.POINTER_DRAGGED_TYPE, x, y);
            super.pointerDragged(x, y);
        }

        protected void pointerPressed(int x, int y) {
            RobotMERecorder.getRecorderInstance().pointerEventOnCanvas(PointerEventOnCanvasLogEntry.POINTER_PRESSED_TYPE, x, y);
            super.pointerPressed(x, y);
        }

        protected void pointerReleased(int x, int y) {
            RobotMERecorder.getRecorderInstance().pointerEventOnCanvas(PointerEventOnCanvasLogEntry.POINTER_RELEASED_TYPE, x, y);
            super.pointerReleased(x, y);
        }
    }
    
    public CanvasMIDletRecorded() {
        RobotMERecorder.getRecorderInstance().setMIDlet(this);
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
    }

    protected void pauseApp() {
    }

    protected void startApp() throws MIDletStateChangeException {
        MyCanvas canvas = new MyCanvas();
        canvas.setTitle("moj canvas");
        canvas.setTicker(new Ticker("moj ticker"));
        canvas.setCommandListener(this);
        canvas.addCommand(CMD2_OK);
        RobotMERecorder.getRecorderInstance().commandAddedToDisplayable(CMD2_OK, canvas);
        canvas.addCommand(CMD1_EXIT);
        RobotMERecorder.getRecorderInstance().commandAddedToDisplayable(CMD1_EXIT, canvas);;
        Display.getDisplay(this).setCurrent(canvas);
        RobotMERecorder.getRecorderInstance().setCurrentDisplayable(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        RobotMERecorder.getRecorderInstance().commandIvoked(c, d);
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("Wcisnieto commanda: " + c + " na obiekcie: " + d);
        }
    }

}
