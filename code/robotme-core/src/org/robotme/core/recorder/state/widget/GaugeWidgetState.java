package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class GaugeWidgetState extends AbstractWidgetState {

    private String label;

    private int value;
    
//    private int maxValue;

    public GaugeWidgetState(String label, int value) {
        this.label = label;
        this.value = value;
    }
    
    public GaugeWidgetState() {
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        if (!(abstractWidgetState instanceof GaugeWidgetState)) {
            return false;
        }

        final GaugeWidgetState gws = (GaugeWidgetState) abstractWidgetState;
        if (!gws.label.equals(label)) {
            return false;
        }
        if (gws.value != value) {
            return false;
        }
        return true;
    }
    
    public void restoreState(DataInputStream dis) throws IOException {
        label = dis.readUTF();
        value = dis.readInt();
    }

    public void saveState(DataOutputStream dos) throws IOException {
        dos.writeUTF(label);
        dos.writeInt(value);
    }

    public String toXML() {
        if (Assert.ASSERT_ON) {
            Assert.notNull(label);
        }

        final StringBuffer sb = new StringBuffer("<gauge");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" label=\"").append(label).append("\"");
        sb.append(" value=\"").append(value).append("\"");
        sb.append("/>\n");

        return sb.toString();
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
}
