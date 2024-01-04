package org.robotme.core.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
public class IOUtils {
    
    private IOUtils() {
        // Do not instantiate
    }
    
    public static void saveAbstractWidgetStateTable(DataOutputStream dos, AbstractWidgetState[] abstractWidgetStates) throws IOException {
        if (Assert.ASSERT_ON) {
            Assert.notNull(abstractWidgetStates);
        }
        dos.writeShort(abstractWidgetStates.length);
        
        for (int i = 0; i < abstractWidgetStates.length; i++) {
            if (Assert.ASSERT_ON) {
                Assert.notNull(abstractWidgetStates[i]);
            }
            if (abstractWidgetStates[i] instanceof ChoiceGroupWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_CHOICE_GROUP);
            } else if (abstractWidgetStates[i] instanceof DateFieldWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_DATE_FIELD);
            } else if (abstractWidgetStates[i] instanceof GaugeWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_GAUGE);
            } else if (abstractWidgetStates[i] instanceof ImageItemWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_IMAGE_ITEM);
            } else if (abstractWidgetStates[i] instanceof SpacerWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_SPACER);
            } else if (abstractWidgetStates[i] instanceof StringItemWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_STRING_ITEM);
            } else if (abstractWidgetStates[i] instanceof TextFieldWidgetState) {
                dos.writeByte(AbstractWidgetState.WIDGET_STATE_TEXT_FIELD);
            } /*else if (abstractWidgetStates[i] instanceof CustomItemWidgetState) {
                // TODO: implement
                throw new RuntimeException("CustomItems are not supported yet");
            }*/ else {
                if (Assert.ASSERT_ON) {
                    Assert.unreachableCode("Unrecognized widget type: " + abstractWidgetStates[i]);
                }
            }
            
            abstractWidgetStates[i].saveState(dos);
        }
    }
    
    public static AbstractWidgetState[] readAbstractWidgetStateTable(DataInputStream dis) throws IOException {
        final short size = dis.readShort();
        final AbstractWidgetState[] table = new AbstractWidgetState[size];
        for (int i = 0; i < size; i++) {
            final byte type = dis.readByte();
            final AbstractWidgetState abstractWidgetState;
            if (type == AbstractWidgetState.WIDGET_STATE_CHOICE_GROUP) {
                abstractWidgetState = new ChoiceGroupWidgetState();
            } else if (type == AbstractWidgetState.WIDGET_STATE_DATE_FIELD) {
                abstractWidgetState = new DateFieldWidgetState();
            } else if (type == AbstractWidgetState.WIDGET_STATE_GAUGE) {
                abstractWidgetState = new GaugeWidgetState();
            } else if (type == AbstractWidgetState.WIDGET_STATE_IMAGE_ITEM) {
                abstractWidgetState = new ImageItemWidgetState();
            } else if (type == AbstractWidgetState.WIDGET_STATE_SPACER) {
                abstractWidgetState = new SpacerWidgetState();
            } else if (type == AbstractWidgetState.WIDGET_STATE_STRING_ITEM) {
                abstractWidgetState = new StringItemWidgetState();
            } else if (type == AbstractWidgetState.WIDGET_STATE_TEXT_FIELD) {
                abstractWidgetState = new TextFieldWidgetState();
            } /*else if (abstractWidgetStates[i] instanceof CustomItemWidgetState) {
                // TODO: implement
                throw new RuntimeException("CustomItems are not supported yet");
            }*/ else {
                if (Assert.ASSERT_ON) {
                    Assert.unreachableCode("Unrecognized widget type");
                }
                throw new RuntimeException("Unrecognized widget type");
            }
            abstractWidgetState.restoreState(dis);
            table[i] = abstractWidgetState;
        }
        return table;
    }
    
    public static String[] readStringTable(DataInputStream dis) throws IOException {
        final short size = dis.readShort();
        final String[] table = new String[size];
        for (int i = 0; i < size; i++) {
            table[i] = dis.readUTF();
        }
        return table;
    }
    
    public static boolean[] readBooleanTable(DataInputStream dis) throws IOException {
        final short size = dis.readShort();
        final boolean[] table = new boolean[size];
        for (int i = 0; i < size; i++) {
            table[i] = dis.readBoolean();
        }
        return table;
    }
    
    public static void saveStringTable(DataOutputStream dos, String[] table) throws IOException {
        if (Assert.ASSERT_ON) {
            Assert.notNull(table);
        }
        dos.writeShort(table.length);
        for (int i = 0; i < table.length; i++) {
            dos.writeUTF(table[i]);
        }
    }
    
    public static void saveBooleanTable(DataOutputStream dos, boolean[] table) throws IOException {
        if (Assert.ASSERT_ON) {
            Assert.notNull(table);
        }
        dos.writeShort(table.length);
        for (int i = 0; i < table.length; i++) {
            dos.writeBoolean(table[i]);
        }
    }

}
