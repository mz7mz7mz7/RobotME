package com.zduniak.midlet;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.RobotME;
import org.robotme.core.RobotMESimulatedCanvas;

/**
 * @author Marcin Zduniak
 */
public class TestCanvasMIDlet extends MIDlet implements CommandListener {

    private boolean firstTime = true;

    private Canvas canvas;

    private final Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);

    private final Command CMD2_OK = new Command("COMMAND", Command.OK, 2);

    public TestCanvasMIDlet() {
        super();
        RobotME.getInstance().setMIDlet(this);
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            // oryginal:
            // canvas = new OryginalCanvas();

            class RobotMeInnerCanvas extends OryginalCanvas implements
                    RobotMESimulatedCanvas {

                private CommandListener commandListener;

                /*
                 * "public" scope instead of "protected"
                 */
                public void keyPressed(int k) {
                    super.keyPressed(k);
                }

                public void keyReleased(int k) {
                    super.keyReleased(k);
                }

                public void keyRepeated(int arg0) {
                    super.keyRepeated(arg0);
                }

                public void pointerDragged(int arg0, int arg1) {
                    super.pointerDragged(arg0, arg1);
                }

                public void pointerPressed(int arg0, int arg1) {
                    super.pointerPressed(arg0, arg1);
                }

                public void pointerReleased(int arg0, int arg1) {
                    super.pointerReleased(arg0, arg1);
                }

                public void setCommandListener(CommandListener l) {
                    this.commandListener = l;
                    super.setCommandListener(RobotMeInnerCanvas.this);
                }

                public boolean commandExecute(Command cmd) {
                    if (null != commandListener) {
                        commandListener.commandAction(cmd, this);
                        return true;
                    }
                    return false;
                }

                public void commandAction(Command c, Displayable d) {
                    this.commandListener.commandAction(c, d);
                    // TODO: store somewhere information about invoked
                    // commandAction
                }
            }

            canvas = new RobotMeInnerCanvas();

            canvas.addCommand(CMD1_EXIT);
            canvas.addCommand(CMD2_OK);
            canvas.setCommandListener(this);

            // oryginal:
            // Display.getDisplay(this).setCurrent(canvas);
            RobotME.getInstance().setCurrentDisplayable(canvas);

            simulate();
        }

    }

    private void simulate() {
        RobotME.getInstance().simulateDelay(1000 * 3);

        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 1);

        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 1);

        RobotME.getInstance().simulateCommandExecution(CMD2_OK);
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateCommandExecution(CMD1_EXIT);
    }

    protected void pauseApp() {
        System.out.println("TestFormMIDlet.pauseApp()");
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        System.out.println("TestFormMIDlet.destroyApp()");
    }

    public void commandAction(Command c, Displayable arg1) {
        System.out.println("TestFormMIDlet.commandAction()");
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123");
        }
    }

}
