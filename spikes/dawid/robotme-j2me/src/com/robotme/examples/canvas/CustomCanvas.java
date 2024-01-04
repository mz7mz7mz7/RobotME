package com.robotme.examples.canvas;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * A custom class extending {@link Canvas} directly.
 */
public class CustomCanvas extends Canvas {
    private final String title;
    private int lastKeyCode;

    public CustomCanvas() {
        this.title = "Hello world.";
    }

    protected void paint(Graphics graphics) {
        final int x = graphics.getClipX();
        final int y = graphics.getClipY();
        final int w = graphics.getClipWidth();
        final int h = graphics.getClipHeight();

        graphics.setColor(0x000000);
        graphics.fillRect(x, y, w, h);

        graphics.setColor(0xa0a0a0a0);
        graphics.drawString(title, getWidth()/2, getHeight()/2, Graphics.BASELINE | Graphics.HCENTER);

        graphics.setColor(0x50a0f0);
        graphics.drawString("Last keycode: " + lastKeyCode,
                getWidth()/2, 0, Graphics.TOP | Graphics.HCENTER);
    }

    protected void keyPressed(int keyCode) {
        lastKeyCode = keyCode;
        repaint();
    }
}
