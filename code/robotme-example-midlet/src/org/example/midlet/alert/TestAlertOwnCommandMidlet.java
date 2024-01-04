package org.example.midlet.alert;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Marcin Zduniak
 */
public class TestAlertOwnCommandMidlet extends MIDlet implements CommandListener {

    private boolean firstTime = true;
    
    private final static Command CMD2_OK = new Command("OK", Command.SCREEN, 1);

    public TestAlertOwnCommandMidlet() {
        super();
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

    }

    protected void pauseApp() {
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            final Alert alert = new Alert("Alert 1", "Msg 1", null, AlertType.INFO);
            alert.setTimeout(Alert.FOREVER);

            alert.setCommandListener(this);
            alert.addCommand(CMD2_OK);

            Display.getDisplay(this).setCurrent(alert);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (CMD2_OK == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123 list");
        }
    }

}
