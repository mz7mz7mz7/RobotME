package org.robotme.core.replaying;

import javax.microedition.lcdui.Canvas;

/**
 * Interface that has some method from {@link Canvas} class but with public access scope.
 * 
 * @author Marcin Zduniak
 */
public interface NakedCanvas {
    void keyPressed(int keyCode);

    void keyReleased(int keyCode);

    void keyRepeated(int keyCode);

    void pointerDragged(int x, int y);

    void pointerPressed(int x, int y);

    void pointerReleased(int x, int y);
}
