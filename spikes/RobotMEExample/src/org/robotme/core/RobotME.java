package org.robotme.core;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

/**
 * @author Marcin Zduniak
 */
public class RobotME {
    
    // static:
    private static RobotME instance;
    private MIDlet midlet;
    
    // non-static:
    private Displayable currentDisplayable;

    private RobotME() {
        // Nothing here
    }
    
    public void setMIDlet(MIDlet midlet) {
        this.midlet = midlet;
    }
    
    public static synchronized RobotME getInstance() {
        if (null == instance) {
            instance = new RobotME();
        }
        return instance;
    }
    
    public void setCurrentDisplayable(Displayable disp) {
        this.currentDisplayable = disp;
        if (disp instanceof RobotMEAlert) {
            final RobotMEAlert alert = (RobotMEAlert) disp;
            alert.ensureExistenceOfCommandListener();
        }
        Display.getDisplay(midlet).setCurrent(disp);
    }
    
// simulations:
    
    public void simulateDelay(long milis) {
        System.out.println("RobotME.simulateDelay()");
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    
    public void simulateTyping(int fieldIndex, String text) {
        System.out.println("RobotME.simulateTypingInField()");
        if (currentDisplayable instanceof Form) {
            final Form form = (Form) currentDisplayable;
            final Item item = form.get(fieldIndex);
            if (item instanceof TextField) {
                final TextField tf = (TextField) item;
                tf.setString(text);
            }
        } else if (currentDisplayable instanceof RobotMETextBox) {
            final RobotMETextBox textBox = (RobotMETextBox) currentDisplayable;
            textBox.setString(text);
        }
    }
    
    /**
     * Typing in currently selected field.
     */
    public void simulateTyping(String text) {
        if (currentDisplayable instanceof RobotMEForm) {
            final RobotMEForm form = (RobotMEForm) currentDisplayable;
            final int selectedIndex = form.getCurrentlySelectedField();
            simulateTyping(selectedIndex, text);
        } else if (currentDisplayable instanceof RobotMETextBox) {
            simulateTyping(0, text);
        }
    }
    
    public void simulateCommandExecution(Command cmd) {
        System.out.println("RobotME.simulateCommandExecution()");
        if (currentDisplayable instanceof CommandListenerSimulator) {
            final CommandListenerSimulator cmdSym = (CommandListenerSimulator) currentDisplayable;
            cmdSym.commandExecute(cmd);
        }
    }
    
    public void simulateSettingInChoiceGroup(int fieldIndex, boolean[] settings) {
        System.out.println("RobotME.simulateSettingInChoiceGroup()");
        if (currentDisplayable instanceof Form) {
            final Form form = (Form) currentDisplayable;
            final Item item = form.get(fieldIndex);
            if (item instanceof ChoiceGroup) {
                final ChoiceGroup cg = (ChoiceGroup) item;
                cg.setSelectedFlags(settings);
            }
        }
    }
    
    public void simulateSettingInList(int indexToSet) {
        System.out.println("RobotME.simulateSettingInlist()");
        if (currentDisplayable instanceof List) {
            final List list = (List) currentDisplayable;
            list.setSelectedIndex(indexToSet, true);
        }
    }

    public void simulateButtonDown() {
        System.out.println("RobotME.simulateButtonDown()");
        if (currentDisplayable instanceof List) {
            final List list = (List) currentDisplayable;
            final int selectedIndex = list.getSelectedIndex();
            final int nextIndex = (selectedIndex + 1) % list.size();
            list.setSelectedIndex(nextIndex, true);
        } else if (currentDisplayable instanceof RobotMEForm) {
            final RobotMEForm form = (RobotMEForm) currentDisplayable;
            final int selectedIndex = form.getCurrentlySelectedField();
            final int nextIndex = (selectedIndex + 1) % form.size();
            Display.getDisplay(midlet).setCurrentItem(form.get(nextIndex));
            form.setCurrentlySelectedField(nextIndex);
        } else if (currentDisplayable instanceof RobotMESimulatedCanvas) {
            final RobotMESimulatedCanvas canvas = (RobotMESimulatedCanvas) currentDisplayable;
            final int keyCode = ((Canvas) canvas).getKeyCode(Canvas.DOWN);
            canvas.keyPressed(keyCode);
            canvas.keyReleased(keyCode);
        }
    }

}
