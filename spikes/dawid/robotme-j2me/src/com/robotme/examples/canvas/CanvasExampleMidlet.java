package com.robotme.examples.canvas;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * A simple midlet with a custom {@link javax.microedition.lcdui.Canvas}.
 * 
 * @author Dawid Weiss
 */
public class CanvasExampleMidlet extends MIDlet {

    protected void startApp() throws MIDletStateChangeException {
        final Display display = Display.getDisplay(this);
        display.setCurrent(new CustomCanvas());
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
