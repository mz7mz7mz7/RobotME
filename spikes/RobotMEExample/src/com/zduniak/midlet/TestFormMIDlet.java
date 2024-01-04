package com.zduniak.midlet;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.RobotME;
import org.robotme.core.RobotMEForm;

/**
 * Musimy miec delegacje do obiektow ktore chcemy symulowac. Moze sie
 * zdarzyc ze ktos np. extenduje Form'a - musimy delegowac odwolania do takiego
 * extendowanego forma.
 * 
 * Poprawka: delegacja obiektow odpada, bo wewnetrznie implementacja operuje na jakichs polach
 * o zakresie widocznosci "default/ package" i gryzie sie z delegacja.
 * Musimy zatem wymusic i tworzyc osobne klasy Form dla wszystkich tych ktore sa
 * extendowane. Jesli jakas nie jest extendowana (czyli zwykle "new Form(nazwa)"
 * to uzywamy jednej ogolnej implementacji).
 * 
 * Moze wystarczy tylko extendowac/ emulowac Form, Canvas itp ?
 * 
 * @author Marcin Zduniak
 */
public class TestFormMIDlet extends MIDlet implements CommandListener {
    
    private boolean firstTime = true;
    private Form form;
    
    private final Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);
    private final Command CMD_NEXT_FORM = new Command("NEXT FORM", Command.ITEM, 1);
    private final Command CMD2_OK = new Command("COMMAND", Command.OK, 2);

    public TestFormMIDlet() {
        super();
        RobotME.getInstance().setMIDlet(this);
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            // oryginal:
            //form = new Form("Form1");
            form = new RobotMEForm("Form1");
            
            final TextField tf1 = new TextField("Label1", "text1", 100, TextField.ANY);
            final TextField tf2 = new TextField("Label2-email", "text2@com", 200, TextField.EMAILADDR);
            final ChoiceGroup cg = new ChoiceGroup("CG", Choice.MULTIPLE);
            cg.append("Option 1", null);
            cg.append("Option 2", null);
            cg.append("Option 3", null);
            
            form.append(tf1);
            form.append(tf2);
            form.append("SAM STRING");
            form.append(cg);
            
            form.addCommand(CMD1_EXIT);
            form.addCommand(CMD2_OK);
            form.addCommand(CMD_NEXT_FORM);
            form.setCommandListener(this);
            
            //oryginal:
            //Display.getDisplay(this).setCurrent(form);
            RobotME.getInstance().setCurrentDisplayable(form);
            
            simulate();
        }

    }

    private void simulate() {
        RobotME.getInstance().simulateDelay(1000 * 3);
        
        // simulating typying in fild 0:
        RobotME.getInstance().simulateTyping(0, "cos tam 0");
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateTyping(1, "cos tam 1");
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateCommandExecution(CMD2_OK);
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateSettingInChoiceGroup(3, new boolean[] {true, false, true});
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateCommandExecution(CMD_NEXT_FORM);
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateTyping(0, "cos tam XXXXXX");
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateCommandExecution(CMD1_EXIT);
    }

    protected void pauseApp() {
        System.out.println("TestFormMIDlet.pauseApp()");
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        System.out.println("TestFormMIDlet.destroyApp()");
    }

    public void commandAction(Command c, Displayable arg1) {
        System.out.println("TestFormMIDlet.commandAction()");
        if (CMD1_EXIT == c) {
            this.notifyDestroyed();
        } else if (CMD_NEXT_FORM == c) {
            createNextForm();
        } else {
            form.get(0).setLabel("NEW LABEL");
            System.out.println("123");
        }
    }

    private void createNextForm() {
        // oryginal:
        //form = new Form("Form2");
        form = new RobotMEForm("Form2");
        
        final TextField tf1 = new TextField("LabelX", "textX", 100, TextField.ANY);
        final TextField tf2 = new TextField("LabelX2", "textX2", 200, TextField.EMAILADDR);
        form.append(tf1);
        form.append(tf2);
        form.append("XXXXX");
        form.addCommand(CMD1_EXIT);
        //form.addCommand(CMD2_OK);
        form.setCommandListener(this);
        
        //oryginal:
        //Display.getDisplay(this).setCurrent(form);
        Display.getDisplay(this).setCurrent(form);
        RobotME.getInstance().setCurrentDisplayable(form);
    }

}
