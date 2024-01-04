package com.zduniak.midlet;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 * @author Marcin Zduniak
 */
public class OryginalCanvas extends Canvas {

    private int keyDownPressed;

    public OryginalCanvas() {
        super();
    }

    protected void paint(Graphics g) {
        g.drawLine(0, 0, 100, 100);
        g.setColor(255, 0, 0);
        final int iterations = keyDownPressed;
        for (int i = 0; i < iterations; i++) {
            final int delta = i * 21;
            g.fillTriangle(0 + delta, 0 + delta, 20 + delta, 20 + delta, 0 + delta, 20 + delta);
        }
    }

    protected void keyPressed(int k) {
        System.out.println("Canvas.keyPressed()");
        if (Canvas.DOWN == getGameAction(k)) {
            keyDownPressed++;
            repaint();
        }
        super.keyPressed(k);
    }

    protected void keyReleased(int k) {
        System.out.println("Canvas.keyReleased()");
        super.keyReleased(k);
    }
    
    

}
