package com.robotme.asmlib.delegates;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * This class implements all the functionality needed to capture
 * and generate events to {@link Canvas} instances.
 * 
 * All methods with regular signatures are copied verbatim to
 * the rewritten class. Methods starting with <code>robot$</code>
 * prefix are replaced with methods from the rewritten class
 * if a corresponding method without the prefix exists.
 *
 * @author Dawid Weiss
 */
final class RobotizedCanvas extends Canvas implements IRobotizedCanvas {
    /**
     * Our implementation of {@link Canvas#paint(javax.microedition.lcdui.Graphics)}
     * method. This eventually delegates control to
     * {@link #robot$paint(Graphics)}.
     */
    protected final void paint(final Graphics graphics) {
        // Invoke the delegate method (empty in this implementation, but
        // copied from the processed class in reality).
        this.robot$paint(graphics);

        // Just so that we know this canvas class has been actually
        // processed :)
        graphics.setColor(0xf05050);
        graphics.drawString("Robotized!", 0, 0, 
                Graphics.LEFT | Graphics.TOP);
    }

    /**
     * A placeholder for the implementation copied from the rewritten
     * class. By default an empty implementation. 
     */
    protected void robot$paint(Graphics graphics) {
        // Default implementation does nothing.
    }

    
    
    /**
     * Event capturing implementation of 
     * {@link Canvas#keyPressed(int)} method.
     */
    protected void keyPressed(int keyCode) {
        System.out.println("Key pressed (captured): " + keyCode);
        this.robot$keyPressed(keyCode);
    }

    /**
     * This method will be replaced by {@link #keyPressed(int)} method
     * present in the actual target code. 
     * Default implementation invokes the superclass. 
     */
    protected void robot$keyPressed(int keyCode) {
        super.keyPressed(keyCode);
    }

    /**
     * Generates {@link #keyPressed(int)} event programmatically.
     */
    public void robot$fireKeyPressed(int keyCode) {
        System.out.println("Key pressed (simulation): " + keyCode);
        this.robot$keyPressed(keyCode);
    }
}
