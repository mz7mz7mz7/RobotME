package com.zduniak.midlet;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.RobotME;
import org.robotme.core.RobotMEAlert;

/**
 * @author Marcin Zduniak
 */
public class TestAlertMIDlet extends MIDlet implements CommandListener {

    private final Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);

    public TestAlertMIDlet() {
        super();
        RobotME.getInstance().setMIDlet(this);
    }

    protected void startApp() throws MIDletStateChangeException {
        // final Alert alert = new Alert("Label", "Text", null,
        // AlertType.ERROR);
        final Alert alert = new RobotMEAlert("Label", "Text", null,
                AlertType.ERROR);

        alert.setCommandListener(this);
        alert.addCommand(CMD1_EXIT);
        alert.setTimeout(Alert.FOREVER);

        // Display.getDisplay(this).setCurrent(alert);
        RobotME.getInstance().setCurrentDisplayable(alert);

        simulate();
    }

    private void simulate() {
        RobotME.getInstance().simulateDelay(1000 * 2);

        //RobotME.getInstance().simulateCommandExecution(CMD1_EXIT);
    }

    protected void pauseApp() {
        // TODO Auto-generated method stub

    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub

    }

    public void commandAction(Command c, Displayable d) {
        System.out.println("Command: " + c.getLabel());
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else if (Alert.DISMISS_COMMAND == c) {
            System.out.println("Alert.DISMISS_COMMAND invoked");
        }
    }

}
