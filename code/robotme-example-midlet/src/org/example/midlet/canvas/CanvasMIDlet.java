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

/**
 * @author Marcin Zduniak
 */
public class CanvasMIDlet extends MIDlet implements CommandListener {

    private final static Command CMD1_EXIT = new Command("Wyjscie", Command.BACK, 2);

    private final static Command CMD2_OK = new Command("OK", Command.SCREEN, 1);

    private static class MyCanvas extends Canvas {
        private int x, y;
        private static final int DELTA = 10;
        
        private static final int[] COLORS = new int []{0xff0000, 0x00ff00, 0x0000ff};
        private int colorIndex = 0;

        protected void paint(final Graphics g) {
            g.setColor(0xFFFFFF);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(COLORS[colorIndex]);
            g.fillRect(x, y, 30, 30);
        }

        protected void keyPressed(int k) {
            System.out.println("KeyPressed: " + k);
            final int action = getGameAction(k);
            switch (action) {
            case Canvas.LEFT:
                x -= DELTA;
                break;
            case Canvas.RIGHT:
                x += DELTA;
                break;
            case Canvas.UP:
                y -= DELTA;
                break;
            case Canvas.DOWN:
                y += DELTA;
                break;
            case Canvas.FIRE:
                colorIndex = (colorIndex + 1) % COLORS.length;
                break;
            }
            
            repaint();
        }

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
        canvas.addCommand(CMD1_EXIT);

        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("Wcisnieto commanda: " + c + " na obiekcie: " + d);
        }
    }

}
