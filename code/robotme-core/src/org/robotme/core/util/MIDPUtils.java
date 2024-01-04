package org.robotme.core.util;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.Spacer;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.recorder.state.widget.ChoiceGroupWidgetState;
import org.robotme.core.recorder.state.widget.DateFieldWidgetState;
import org.robotme.core.recorder.state.widget.GaugeWidgetState;
import org.robotme.core.recorder.state.widget.ImageItemWidgetState;
import org.robotme.core.recorder.state.widget.SpacerWidgetState;
import org.robotme.core.recorder.state.widget.StringItemWidgetState;
import org.robotme.core.recorder.state.widget.TextFieldWidgetState;

/**
 * @author Marcin Zduniak
 */
public class MIDPUtils {
    
    final static Canvas HELPER_CANVAS = new Canvas() {
        protected void paint(Graphics g) {
            // Left empty
        }
    };
    final static int[] VALID_GAME_ACTIONS = new int[] { Canvas.FIRE, Canvas.DOWN, Canvas.LEFT, Canvas.RIGHT,
    Canvas.UP, Canvas.GAME_A, Canvas.GAME_B, Canvas.GAME_C, Canvas.GAME_D };
    final static int[] STANDARD_KEY_CODE = new int[] { Canvas.KEY_NUM0, Canvas.KEY_NUM1, Canvas.KEY_NUM2,
    Canvas.KEY_NUM3, Canvas.KEY_NUM4, Canvas.KEY_NUM5, Canvas.KEY_NUM6, Canvas.KEY_NUM7, Canvas.KEY_NUM8,
    Canvas.KEY_NUM9, Canvas.KEY_POUND, Canvas.KEY_STAR };

    private MIDPUtils() {
        // Do not instantiate
    }

    public static final boolean isGameAction(final int keyCode) {
        final int gameAction = MIDPUtils.getGameAction(keyCode);
        for (int i = 0, n = VALID_GAME_ACTIONS.length; i < n; i++) {
            if (gameAction == VALID_GAME_ACTIONS[i]) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isStandardKeyCode(final int keyCode) {
        for (int i = 0, n = STANDARD_KEY_CODE.length; i < n; i++) {
            if (keyCode == STANDARD_KEY_CODE[i]) {
                return true;
            }
        }
        return false;
    }

    public static final int getGameAction(final int keyCode) throws IllegalArgumentException {
        return HELPER_CANVAS.getGameAction(keyCode);
    }

    public static final int getKeyCode(final int gameAction) throws IllegalArgumentException {
        return HELPER_CANVAS.getKeyCode(gameAction);
    }
    

    private static TextFieldWidgetState createTextFieldWidgetState(final Item item) {
        final TextField textField = (TextField) item;
        return new TextFieldWidgetState(textField.getLabel(), textField.getString());
    }

    private static StringItemWidgetState createStringItemWidgetState(final Item item) {
        final StringItem stringItem = (StringItem) item;
        return new StringItemWidgetState(stringItem.getLabel(), stringItem.getText());
    }

    private static SpacerWidgetState createSpacerWidgetState(final Item item) {
        return new SpacerWidgetState();
    }

    private static ImageItemWidgetState createImageItemWidgetState(final Item item) {
        final ImageItem imageItem = (ImageItem) item;
        return new ImageItemWidgetState(imageItem.getLabel(), imageItem.getAltText());
    }

    private static GaugeWidgetState createGaugeWidgetState(final Item item) {
        final Gauge gauge = (Gauge) item;
        return new GaugeWidgetState(gauge.getLabel(), gauge.getValue());
    }

    private static DateFieldWidgetState createDateFieldWidgetState(final Item item) {
        final DateField dateField = (DateField) item;
        return new DateFieldWidgetState(dateField.getLabel(), dateField.getDate());
    }

    private static ChoiceGroupWidgetState createChoiceGroupWidgetState(final Item item) {
        final ChoiceGroup choiceGroup = (ChoiceGroup) item;
        final String label = choiceGroup.getLabel();

        final int size = choiceGroup.size();

        // selectedFlags:
        final boolean[] selectedFlags = new boolean[size];
        choiceGroup.getSelectedFlags(selectedFlags);
        
        // string:
        final String[] strings = new String[size];
        for (int i = 0; i < size; i++) {
            strings[i] = choiceGroup.getString(i);
        }
        
        return new ChoiceGroupWidgetState(label, selectedFlags, strings);
    }
    
    public static AbstractWidgetState[] getAbstractWidgetStatesFromForm(Form form) {
        final int size = form.size();
        final AbstractWidgetState[] abstractWidgetStates = new AbstractWidgetState[size];
        for (int i = 0; i < size; i++) {
            final Item item = form.get(i);
            if (item instanceof ChoiceGroup) {
                abstractWidgetStates[i] = createChoiceGroupWidgetState(item);
            } else if (item instanceof DateField) {
                abstractWidgetStates[i] = createDateFieldWidgetState(item);
            } else if (item instanceof Gauge) {
                abstractWidgetStates[i] = createGaugeWidgetState(item);
            } else if (item instanceof ImageItem) {
                abstractWidgetStates[i] = createImageItemWidgetState(item);
            } else if (item instanceof Spacer) {
                abstractWidgetStates[i] = createSpacerWidgetState(item);
            } else if (item instanceof StringItem) {
                abstractWidgetStates[i] = createStringItemWidgetState(item);
            } else if (item instanceof TextField) {
                abstractWidgetStates[i] = createTextFieldWidgetState(item);
            } else if (item instanceof CustomItem) {
                // TODO: implement
                throw new RuntimeException("CustomItems are not supported yet");
            } else {
                if (Assert.ASSERT_ON) {
                    Assert.unreachableCode("Unrecognized widget type");
                }
            }
        }
        
        return abstractWidgetStates;
    }

}
