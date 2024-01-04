package org.robotme.core;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;

/**
 * @author Marcin Zduniak
 */
public interface CommandListenerSimulator extends CommandListener {
    
    public abstract boolean commandExecute(Command cmd) ;

}
