package com.zduniak.midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.robotme.core.RobotME;
import org.robotme.core.RobotMEList;

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
public class TestListMIDlet extends MIDlet implements CommandListener {
    
    private boolean firstTime = true;
    private List list;
    
    private final Command CMD1_EXIT = new Command("EXIT", Command.EXIT, 1);
    private final Command CMD2_OK = new Command("COMMAND", Command.OK, 2);

    public TestListMIDlet() {
        super();
        RobotME.getInstance().setMIDlet(this);
    }

    protected void startApp() throws MIDletStateChangeException {
        if (true == firstTime) {
            firstTime = false;
            // oryginal:
            //form = new Form("Form1");
            list = new RobotMEList("List1", RobotMEList.IMPLICIT);
            
            list.append("Option 1", null);
            list.append("Option 2", null);
            list.append("Option 3", null);
            
            list.addCommand(CMD1_EXIT);
            list.addCommand(CMD2_OK);
            list.setCommandListener(this);
            
            //oryginal:
            //Display.getDisplay(this).setCurrent(form);
            RobotME.getInstance().setCurrentDisplayable(list);
            
            simulate();
        }

    }

    private void simulate() {
        RobotME.getInstance().simulateDelay(1000 * 3);
        
        RobotME.getInstance().simulateSettingInList(1);
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateButtonDown();
        RobotME.getInstance().simulateDelay(1000 * 2);
        
        RobotME.getInstance().simulateButtonDown();
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
        } else {
            System.out.println("123");
        }
    }

    
}
