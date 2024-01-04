package org.example.midlet.formlivedemo;

import java.util.Calendar;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Marcin Zduniak
 */
public class FormLiveDemoMIDlet extends MIDlet implements CommandListener {

    private boolean firstTime = true;

    private final static Command CMD1_EXIT = new Command("Exit", Command.EXIT, 1);

    private final static Command CMD2_OK = new Command("Save", Command.OK, 2);
    
    private ChoiceGroup choiceGroupMultiple;

    public FormLiveDemoMIDlet() {
        super();
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

    }

    protected void pauseApp() {

    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            final Form form = new Form("Questionnaire");
            appendChoiceGroupExclusive(form);
            appendTextField(form);
            appendDateFieldDate(form);
            appendGaugeInteractive(form);
            appendChoiceGroupMultiple(form);

            form.addCommand(CMD1_EXIT);
            form.addCommand(CMD2_OK);
            form.setCommandListener(this);

            Display.getDisplay(this).setCurrent(form);
        }
    }

    private void appendChoiceGroupMultiple(final Form form) {
        choiceGroupMultiple = new ChoiceGroup("What type of fast food do you prefer", Choice.MULTIPLE);
        choiceGroupMultiple.append("Hamburger", null);
        choiceGroupMultiple.append("Pizza", null);
        choiceGroupMultiple.append("Hotdog", null);
        choiceGroupMultiple.append("Fish & Chips", null);
        choiceGroupMultiple.append("Sandwiches", null);
        choiceGroupMultiple.append("Other", null);
        choiceGroupMultiple.append("None", null);
        choiceGroupMultiple.setSelectedFlags(new boolean[] { false, false, false, false, false, false, false });
        form.append(choiceGroupMultiple);
    }

    private void appendChoiceGroupExclusive(final Form form) {
        ChoiceGroup choiceGroupExclusive = new ChoiceGroup("Gender", Choice.EXCLUSIVE);
        choiceGroupExclusive.append("Male", null);
        choiceGroupExclusive.append("Female", null);
        choiceGroupExclusive.setSelectedIndex(1, true);
        form.append(choiceGroupExclusive);
    }

    private void appendDateFieldDate(final Form form) {
        DateField dateFieldDate = new DateField("Birth Date", DateField.DATE);
        dateFieldDate.setDate(Calendar.getInstance().getTime());
        form.append(dateFieldDate);
    }

    private void appendGaugeInteractive(final Form form) {
        Gauge gaugeInteractive = new Gauge("Level of satisfaction", true, 10, 5);
        form.append(gaugeInteractive);
    }

    private void appendTextField(final Form form) {
        TextField textField = new TextField("Full name", "", 100, TextField.ANY);
        form.append(textField);
    }

    public void commandAction(Command c, Displayable d) {
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            
            // Hack, only for live demo purpose:
            final boolean[] flags = new boolean [choiceGroupMultiple.size()];
            choiceGroupMultiple.getSelectedFlags(flags);
            boolean ok = false;
            for (int i = 0; i < flags.length; i++) {
                if (flags[i]) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    // Ignore
                }
                Alert alert = new Alert("Questionnaire", "Congratulations, your questionnaire is saved.", null, AlertType.INFO);
                Display.getDisplay(this).setCurrent(alert);
            }
        }
    }

}
