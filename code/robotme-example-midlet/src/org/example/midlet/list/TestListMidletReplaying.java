package org.example.midlet.list;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class TestListMidletReplaying extends MIDlet implements CommandListener {
    
    private boolean firstTime = true;

    private final static Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);
    private final static Command CMD2_OK = new Command("COMMAND", Command.OK, 2);
    
    public TestListMidletReplaying() {
        super();
        RobotMEReplaying.getReplayingInstance().setMIDlet(this);
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

    }

    protected void pauseApp() {
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            final List list = new List("List screen", List.IMPLICIT);

            list.addCommand(CMD1_EXIT);
            RobotMEReplaying.getReplayingInstance().commandAddedToDisplayable(CMD1_EXIT,
                    list);
            list.addCommand(CMD2_OK);
            RobotMEReplaying.getReplayingInstance().commandAddedToDisplayable(CMD2_OK,
                    list);
            list.setCommandListener(this);
            RobotMEReplaying.getReplayingInstance().commandListenerSetOnDisplayable(this, list);
            
            Display.getDisplay(this).setCurrent(list);
            RobotMEReplaying.getReplayingInstance().setCurrentDisplayable(list);
        }
        RobotMEReplaying.getReplayingInstance().startReplaying();
    }

    public void commandAction(Command c, Displayable d) {
        // TODO: what for this event catching in replaying mode ? I think it is useless.
        RobotMEReplaying.getReplayingInstance().commandIvoked(c, d);
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123 list");
        }
    }

}
