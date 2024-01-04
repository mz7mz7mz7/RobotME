package org.robotme.core.log.replayable;

import java.util.Hashtable;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import org.robotme.core.log.entries.CommandLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class ReplayableCommandLogEntry implements Replayable {

    public void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException {
        final CommandLogEntry le = (CommandLogEntry) logEntry;
        final Displayable currDisp = replayingEngine.getCurrentDisplayable();

        if (!currDisp.getTitle().equals(le.getDisplayableTitle())) {
            throw new RuntimeException("Not suitable current displayable. Expected: " + le.getDisplayableTitle()
                    + ", but was: " + currDisp.getTitle());
        }

        // ignore commands invoked as a result of timeout from Alert displayables:
        final Displayable disp = replayingEngine.getCurrentDisplayable();
        if (disp instanceof Alert) {
            final Alert alert = (Alert) disp;
            if (Alert.FOREVER != alert.getTimeout()) {
                // TODO: situation when timeout command is invoked by user is undifferentiable
                // from situation when the same command is invoked as a result of timeout event. This should be solved later.
                return;
            }
        }

        final String label = le.getCmdLabel();
        final Command command;
        if (CommandLogEntry.INTERNAL_LIST_SELECT_COMMAND_LABEL.equals(label)) {
            command = List.SELECT_COMMAND;
        } else if (CommandLogEntry.INTERNAL_ALERT_DISMISS_COMMAND_LABEL.equals(label)) {
            command = Alert.DISMISS_COMMAND;
        } else {
            command = getCommandFromCurrentDisplayable(label, replayingEngine);
        }

        final CommandListener listener = getCommandListenerByDisplayableTitle(le.getDisplayableTitle(), replayingEngine);

        // simulate command action invocation:
        listener.commandAction(command, currDisp);
    }

    protected Command getCommandFromCurrentDisplayable(String cmdLabel, RobotMEReplaying replayingEngine) {
        final String currentDisplayableTitle = replayingEngine.getCurrentDisplayable().getTitle();
        final Hashtable h = (Hashtable) replayingEngine.getDisplayablesWithCommands().get(currentDisplayableTitle);
        if (null != h) {
            return (Command) h.get(cmdLabel);
        } else {
            throw new RuntimeException("Command not found in current displayable: " + cmdLabel);
        }
    }

    protected CommandListener getCommandListenerByDisplayableTitle(String displayableTitle,
            RobotMEReplaying replayingEngine) {
        final CommandListener listener = (CommandListener) replayingEngine.getDisplayablesWithCommandListener().get(
                displayableTitle);
        if (null != listener) {
            return listener;
        } else {
            throw new RuntimeException("CommandListener not associated with given displayable: " + displayableTitle);
        }
    }

}
