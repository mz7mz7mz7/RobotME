package org.example.midlet.textbox;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Marcin Zduniak
 */
public class TextBoxMIDlet extends MIDlet implements CommandListener {

    private boolean firstTime = true;

    private final static Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);
    private final static Command CMD2_OK = new Command("COMMAND", Command.OK, 2);

    public TextBoxMIDlet() {
        super();
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            // final TextBox textBox = new TextBox("Label", "Text", 100,
            // TextField.ANY);
            final TextBox textBox = new TextBox("Label", "Text", 100,
                    TextField.ANY);

            textBox.addCommand(CMD1_EXIT);
            textBox.addCommand(CMD2_OK);
            textBox.setCommandListener(this);
            
            Display.getDisplay(this).setCurrent(textBox);
//            this.commandAction(CMD2_OK, textBox);
        }
    }

    protected void pauseApp() {
        // TODO Auto-generated method stub
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub
    }

    public void commandAction(Command c, Displayable d) {
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123");
        }
    }

}
