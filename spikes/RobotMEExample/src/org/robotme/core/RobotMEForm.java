package org.robotme.core;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;

/**
 * @author Marcin Zduniak
 */
public class RobotMEForm extends Form implements CommandListenerSimulator {

    private CommandListener commandListener; // type: CommandListener

    private int currentlySelectedField;

    public RobotMEForm(String name) {
        super(name);
        currentlySelectedField = 0;
    }

    public RobotMEForm(String arg0, Item[] arg1) {
        super(arg0, arg1);
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

    /* package scope */int getCurrentlySelectedField() {
        return currentlySelectedField;
    }

    /* package scope */void setCurrentlySelectedField(
            int currentlySelectedField) {
        this.currentlySelectedField = currentlySelectedField;
    }

    public void commandAction(Command c, Displayable d) {
        this.commandListener.commandAction(c, d);
        // TODO: store somewhere information about invoked commandAction
    }

}
