package org.example.midlet.textbox;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class TextBoxMIDletReplaying extends MIDlet implements
        CommandListener {

    private boolean firstTime = true;

    // na razie zakladam ze wszystki obiekty typu Command sa deklarowane jako
    // statyczne
    // w obrebie klasy
    private final static Command CMD1_EXIT = new Command("EXIT", Command.EXIT,
            1);

    private final static Command CMD2_OK = new Command("COMMAND", Command.OK, 2);

    public TextBoxMIDletReplaying() {
        super();
        RobotMEReplaying.getReplayingInstance().setMIDlet(this);
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            String a1 = "1";
            final TextBox textBox = new TextBox("Label", "Text", 100,
                    TextField.ANY);

            /*
             * class jakas extends TextBox { jakas() { super("Label", "Text",
             * 100, TextField.ANY); }
             * 
             * public void addCommand(Command c) { super.addCommand(c); } }
             */

            // textBox = new jakas();
            textBox.addCommand(CMD1_EXIT);
            RobotMEReplaying.getReplayingInstance().commandAddedToDisplayable(CMD1_EXIT,
                    textBox);
             textBox.addCommand(CMD2_OK);
             RobotMEReplaying.getReplayingInstance().commandAddedToDisplayable(CMD2_OK,
             textBox);
            textBox.setCommandListener(this);
            RobotMEReplaying.getReplayingInstance().commandListenerSetOnDisplayable(this, textBox);

            String a = "1";
            
            // na razie zakladamy ze zmiany obiektu Displayable odbywaja sie za
            // pomoca takiej wlasnie dlugasnej operacji (bez przechowywania
            // tymczasowych wartosci z obiektem Display)
            Display.getDisplay(this).setCurrent(textBox);
            RobotMEReplaying.getReplayingInstance().setCurrentDisplayable(textBox);
        }
        RobotMEReplaying.getReplayingInstance().startReplaying();
    }

    protected void pauseApp() {
        // TODO Auto-generated method stub
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // TODO Auto-generated method stub
    }

    public void commandAction(Command c, Displayable d) {
        // TODO: what for this event catching in replaying mode ? I think it is useless.
        RobotMEReplaying.getReplayingInstance().commandIvoked(c, d);
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123");
        }
    }

}
