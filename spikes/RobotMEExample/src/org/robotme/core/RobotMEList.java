package org.robotme.core;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;

/**
 * @author Marcin Zduniak
 */
public class RobotMEList extends List implements CommandListenerSimulator {

    private CommandListener commandListener; // type: CommandListener

    public RobotMEList(String arg0, int arg1) {
        super(arg0, arg1);
    }

    public RobotMEList(String arg0, int arg1, String[] arg2, Image[] arg3) {
        super(arg0, arg1, arg2, arg3);
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
