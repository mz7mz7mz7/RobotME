package org.robotme.core.recorder;

import java.util.Hashtable;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.CommandLogEntry;
import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.recorder.state.DisplayableState;
import org.robotme.core.util.MIDPUtils;

/**
 * @author  Marcin Zduniak
 */
public class RobotMERecorder {

    // static:
    protected static RobotMERecorder INSTANCE;
    
    public final static LogHandler LOG = LogHandler.getInstance();

    /**
     * Commands added to displayable objects.
     */
    protected final Hashtable displayablesWithCommands = new Hashtable();

    protected MIDlet midlet;

    protected Displayable currentDisplayable;

    protected DisplayableState displayableState;

    protected RobotMERecorder() {
        // Instantiation only from this class and subclasses
    }

    public void setMIDlet(MIDlet midlet) {
        reset();
        this.midlet = midlet;
        LOG.log(new LogEntry(LogHandler.DEBUG_LEVEL, "MIDlet set to: " + midlet));
    }

    public void setCurrentDisplayable(Displayable disp) {
        this.currentDisplayable = disp;
        this.displayableState = DisplayableState.createDisplayableState(disp);
        LOG.log(new DisplayableChangedLogEntry(LogHandler.INTERNAL_LEVEL, "Displayable set to: " + disp, disp
                .getTitle(), disp));
    }

    // TODO:
    public static synchronized RobotMERecorder getRecorderInstance() {
        if (null == INSTANCE) {
            INSTANCE = new RobotMERecorder();
        }
        return INSTANCE;
    }

    // TODO: consider possible memory leaks
    public synchronized void commandAddedToDisplayable(Command cmd,
            Displayable disp) {
        LOG.log(new LogEntry(LogHandler.DEBUG_LEVEL, "Command added to displayable: "
                + cmd));
        
        // REVIEW: should we force programmers to set title in every case ?
        // It is not common to set title on Canvas displayables.
        final String dispKey = null != disp.getTitle() ? disp.getTitle() : disp.toString();
        final String cmdKey = cmd.getLabel();
        if (!displayablesWithCommands.containsKey(dispKey)) {
            displayablesWithCommands.put(dispKey, new Hashtable());
        }
        final Hashtable commands = (Hashtable) displayablesWithCommands
                .get(dispKey);
        if (!commands.contains(cmdKey)) {
            commands.put(cmdKey, cmd);
        }
    }
    
    public synchronized void commandIvoked(final Command cmd, Displayable disp) {
        final DisplayableState ds = DisplayableState
                .createDisplayableState(disp);
        if (!ds.equals(displayableState)) {
            displayableState = ds;
            LOG.log(ds.createLogEntry());
        }
        
        // Command's label:
        final String label;
        if (List.SELECT_COMMAND == cmd) {
            label = CommandLogEntry.INTERNAL_LIST_SELECT_COMMAND_LABEL;
        } else if (Alert.DISMISS_COMMAND == cmd) {
            label = CommandLogEntry.INTERNAL_ALERT_DISMISS_COMMAND_LABEL;
        } else {
            label = cmd.getLabel();
        }
        
        // log event:
        LOG.log(new CommandLogEntry(LogHandler.INTERNAL_LEVEL, "Command invoked: "
                + label, label, disp.getTitle()));
    }

    protected final void reset() {
        midlet = null;
        currentDisplayable = null;
        displayableState = null;
        displayablesWithCommands.clear();
    }

    public final Displayable getCurrentDisplayable() {
        return currentDisplayable;
    }

    public final DisplayableState getDisplayableState() {
        return displayableState;
    }

    public final Hashtable getDisplayablesWithCommands() {
        return displayablesWithCommands;
    }

    public final MIDlet getMidlet() {
        return midlet;
    }

    public void keyEventOnCanvas(byte keyEventType, int keyCode) {
        final Displayable disp = getCurrentDisplayable();
        if (!(disp instanceof Canvas)) {
            throw new RuntimeException("Ooops.. current displayable != Canvas ???");
        }

        boolean validGameAction = false;
        int keyCodeOrGameAction;
        if (MIDPUtils.isStandardKeyCode(keyCode)) {
            keyCodeOrGameAction = keyCode;
        } else if (MIDPUtils.isGameAction(keyCode)) {
            validGameAction = true;
            keyCodeOrGameAction = MIDPUtils.getGameAction(keyCode);
        } else {
            LOG.log(new LogEntry(LogHandler.WARN_LEVEL, "The following key-event has device-specific keyCode = "
                    + keyCode));
            keyCodeOrGameAction = keyCode;
        }

        LOG.log(new KeyEventOnCanvasLogEntry(LogHandler.INTERNAL_LEVEL, keyCodeOrGameAction, keyEventType, disp
                .getTitle(), validGameAction));

    }

    public void pointerEventOnCanvas(byte pointerEventType, int x, int y) {
        final Displayable disp = getCurrentDisplayable();
        if (!(disp instanceof Canvas)) {
            throw new RuntimeException("Ooops.. current displayable != Canvas ???");
        }
        LOG.log(new PointerEventOnCanvasLogEntry(LogHandler.INTERNAL_LEVEL, x, y, pointerEventType, disp.getTitle()));
    }
}
