package org.example.midlet.form;

import java.io.IOException;
import java.util.Calendar;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Marcin Zduniak
 */
public class FormMIDlet extends MIDlet implements CommandListener {

    private boolean firstTime = true;

    private final static Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);

    private final static Command CMD2_OK = new Command("COMMAND", Command.OK, 2);

    public FormMIDlet() {
        super();
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

    }

    protected void pauseApp() {

    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            final Form form = new Form("Form screen 1");

            appendTextField(form);
//
            appendStringItemPlain(form);
//
            appendStringItemButton(form);

            appendStringItemHyperLink(form);

            appendSpacer(form);

            appendImageItemPlain(form);

            appendImageItemButton(form);

            appendGaugeInteractive(form);

            appendGaugeNonInteractive(form);

            appendDateFieldTime(form);

            appendDateFieldDate(form);

            appendDateFieldAll(form);

            appendChoiceGroupExclusive(form);

            appendChoiceGroupMultiple(form);

            appendChoiceGroupPopup(form);

            // TODO: add support for ItemStateListeners

            // TODO: CustomItems are not supported currently

            form.addCommand(CMD1_EXIT);
            form.addCommand(CMD2_OK);
            form.setCommandListener(this);

            Display.getDisplay(this).setCurrent(form);
        }
    }

    private void appendChoiceGroupPopup(final Form form) {
        ChoiceGroup choiceGroupPopup = new ChoiceGroup("choiceGroupLabelPopup", Choice.POPUP);
        choiceGroupPopup.append("Item 1 P", null);
        choiceGroupPopup.append("Item 2 P", null);
        choiceGroupPopup.append("Item 3 P", null);
        choiceGroupPopup.append("Item 4 P", null);
        form.append(choiceGroupPopup);
    }

    private void appendChoiceGroupMultiple(final Form form) {
        ChoiceGroup choiceGroupMultiple = new ChoiceGroup("choiceGroupLabelMultiple", Choice.MULTIPLE);
        choiceGroupMultiple.append("Item 1 M", null);
        choiceGroupMultiple.append("Item 2 M", null);
        choiceGroupMultiple.append("Item 3 M", null);
        choiceGroupMultiple.append("Item 4 M", null);
        choiceGroupMultiple.setSelectedFlags(new boolean[] { true, false, true, false });
        form.append(choiceGroupMultiple);
    }

    private void appendChoiceGroupExclusive(final Form form) {
        ChoiceGroup choiceGroupExclusive = new ChoiceGroup("choiceGroupLabelExclusive", Choice.EXCLUSIVE);
        choiceGroupExclusive.append("Item 1 E", null);
        choiceGroupExclusive.append("Item 2 E", null);
        choiceGroupExclusive.append("Item 3 E", null);
        choiceGroupExclusive.append("Item 4 E", null);
        choiceGroupExclusive.setSelectedIndex(1, true);
        form.append(choiceGroupExclusive);
    }

    private void appendDateFieldAll(final Form form) {
        DateField dateFieldAll = new DateField("dateFieldDate", DateField.DATE_TIME);
        dateFieldAll.setDate(Calendar.getInstance().getTime());
        form.append(dateFieldAll);
    }

    private void appendDateFieldDate(final Form form) {
        DateField dateFieldDate = new DateField("dateFieldDate", DateField.DATE);
        dateFieldDate.setDate(Calendar.getInstance().getTime());
        form.append(dateFieldDate);
    }

    private void appendDateFieldTime(final Form form) {
        DateField dateFieldTime = new DateField("dateFieldTime", DateField.TIME);
        form.append(dateFieldTime);
    }

    private void appendGaugeNonInteractive(final Form form) {
        Gauge gaugeNonInteractive = new Gauge("gaugeLabelNonInteractive", false, Gauge.INDEFINITE, 2);
        form.append(gaugeNonInteractive);
    }

    private void appendGaugeInteractive(final Form form) {
        Gauge gaugeInteractive = new Gauge("gaugeLabelInteractive", true, 10, 5);
        form.append(gaugeInteractive);
    }

    private void appendImageItemButton(final Form form) {
        try {
            final Image image = Image.createImage(getClass().getResourceAsStream("/res/car.png"));
            ImageItem imageItemButton = new ImageItem("imageItemLabelButton", image, Item.LAYOUT_CENTER,
                    "altTextButton", Item.BUTTON);
            imageItemButton.setItemCommandListener(new ItemCommandListener() {
                public void commandAction(Command c, Item item) {
                    System.out.println("Img Button, command: " + c + ", item: " + item);
                }
            });
            form.append(imageItemButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendImageItemPlain(final Form form) {
        try {
            final Image image = Image.createImage(getClass().getResourceAsStream("/res/car.png"));
            ImageItem imageItemPlain = new ImageItem("imageItemLabelPlain", image, Item.LAYOUT_CENTER, "altTextPlain",
                    Item.PLAIN);
            form.append(imageItemPlain);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendSpacer(final Form form) {
        Spacer spacer = new Spacer(50, 5);
        form.append(spacer);
    }

    private void appendStringItemHyperLink(final Form form) {
        StringItem stringItemHyperLink = new StringItem("stringItemHyperLinkLabel", "defStringItemHyperLinkValue",
                Item.HYPERLINK);
        stringItemHyperLink.setDefaultCommand(new Command("PressHyperLink", Command.ITEM, 1));
        stringItemHyperLink.setItemCommandListener(new ItemCommandListener() {
            public void commandAction(Command c, Item item) {
                System.out.println("stringItem HyperLink, command: " + c + ", item: " + item);
            }
        });
        form.append(stringItemHyperLink);
    }

    private void appendStringItemButton(final Form form) {
        StringItem stringItemButton = new StringItem("stringItemButtonLabel", "defStringItemButtonValue", Item.BUTTON);
        stringItemButton.setDefaultCommand(new Command("PressButton", Command.ITEM, 1));
        stringItemButton.setItemCommandListener(new ItemCommandListener() {
            public void commandAction(Command c, Item item) {
                System.out.println("stringItem Button, command: " + c + ", item: " + item);
            }
        });
        form.append(stringItemButton);
    }

    private void appendStringItemPlain(final Form form) {
        StringItem stringItemPlain = new StringItem("stringItemPlainLabel", "defStringItemPlainValue", Item.PLAIN);
        form.append(stringItemPlain);
    }

    private void appendTextField(final Form form) {
        TextField textField = new TextField("textFieldLabel", "defTextFieldValue", 100, TextField.ANY);
        form.append(textField);
    }

    public void commandAction(Command c, Displayable d) {
        System.out.println("commandAction");
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else {
            System.out.println("123");
        }
    }

}
