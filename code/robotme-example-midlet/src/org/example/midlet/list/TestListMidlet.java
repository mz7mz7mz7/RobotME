package org.example.midlet.list;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Marcin Zduniak
 */
public class TestListMidlet extends MIDlet implements CommandListener {
    
    private boolean firstTime = true;

    private final static Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);
    private final static Command CMD2_OK = new Command("COMMAND", Command.OK, 2);
    
    public TestListMidlet() {
        super();
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
            list.addCommand(CMD2_OK);
            list.setCommandListener(this);
            
            list.append("Item 1", null);
            list.append("Item 2", null);
            list.append("Item 3", null);
            list.append("Item 4", null);
            
            Display.getDisplay(this).setCurrent(list);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123 list");
        }
    }

}
