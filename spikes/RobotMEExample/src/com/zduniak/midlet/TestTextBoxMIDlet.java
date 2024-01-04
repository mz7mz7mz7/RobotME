package com.zduniak.midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.RobotME;
import org.robotme.core.RobotMETextBox;

/**
 * @author Marcin Zduniak
 */
public class TestTextBoxMIDlet extends MIDlet implements CommandListener {

    private boolean firstTime = true;

    private final Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);

    public TestTextBoxMIDlet() {
        super();
        System.out.println("OK");
        RobotME.getInstance().setMIDlet(this);
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            // final TextBox textBox = new TextBox("Label", "Text", 100,
            // TextField.ANY);
            final TextBox textBox = new RobotMETextBox("Label", "Text", 100,
                    TextField.ANY);

            textBox.addCommand(CMD1_EXIT);
            textBox.setCommandListener(this);

            // Display.getDisplay(this).setCurrent(textBox);
            RobotME.getInstance().setCurrentDisplayable(textBox);

            simulate();

        }
    }

    private void simulate() {
        RobotME.getInstance().simulateDelay(1000 * 3);

        // simulating typying
        RobotME.getInstance().simulateTyping("cos tam 0");
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateTyping("cos tam 1");
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateTyping("cos tam XXXXXX");
        RobotME.getInstance().simulateDelay(1000 * 2);

        RobotME.getInstance().simulateCommandExecution(CMD1_EXIT);
    }

    protected void pauseApp() {
        // TODO Auto-generated method stub
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub
    }

    public void commandAction(Command c, Displayable d) {
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        }
    }

}
