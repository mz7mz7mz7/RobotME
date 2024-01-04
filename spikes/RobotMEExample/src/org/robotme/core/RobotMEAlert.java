package org.robotme.core;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;

/**
 * @author Marcin Zduniak
 */
public class RobotMEAlert extends Alert implements CommandListenerSimulator {

    private CommandListener commandListener;

    public RobotMEAlert(String arg0) {
        super(arg0);
        // TODO: get from somewhere default CommandListener
        // commandListener = super.getCommandListener();
    }

    public RobotMEAlert(String arg0, String arg1, Image arg2, AlertType arg3) {
        super(arg0, arg1, arg2, arg3);
        // TODO: get from somewhere default CommandListener
        // commandListener = super.getCommandListener();
    }

    /* package scope */void ensureExistenceOfCommandListener() {
        if (null == commandListener) {
            final class SimpleCommandListener implements CommandListener {
                public void commandAction(Command c, Displayable d) {
                    System.out.println("DefalyCommandAction: " + c.getLabel());
                    // TODO: store somewhere information about invoked commandAction
                    // TODO: do nothing more ?
                }
            }
            commandListener = new SimpleCommandListener();
            super.setCommandListener(commandListener);
        }
    }

    public void setCommandListener(CommandListener l) {
        this.commandListener = l;
        super.setCommandListener(this);
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
        // TODO: store somewhere information about invoked commandAction
    }

}
