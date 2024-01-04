package com.robotme.asmlib.delegates;

import javax.microedition.lcdui.Canvas;

/**
 * A public interface all processed subclasses of {@link Canvas}
 * will implement. 
 * 
 * @author Dawid Weiss
 */
public interface IRobotizedCanvas extends IRobotized {
    /**
     * Fires key pressed event programmatically. 
     */
    public void robot$fireKeyPressed(int keyCode);
}
