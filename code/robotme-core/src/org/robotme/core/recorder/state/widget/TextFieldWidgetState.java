package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;

/**
 * Currently this class is very similar to {@link StringItemWidgetState}.
 * 
 * @author Marcin Zduniak
 */
public class TextFieldWidgetState extends AbstractWidgetState {

    private String label;

    private String string;
    
    public TextFieldWidgetState(String label, String string) {
        this.label = label;
        this.string = string;
    }
    
    public TextFieldWidgetState() {
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        if (!(abstractWidgetState instanceof TextFieldWidgetState)) {
            return false;
        }

        final TextFieldWidgetState tfws = (TextFieldWidgetState) abstractWidgetState;
        if (!tfws.label.equals(label)) {
            return false;
        }
        if (!tfws.string.equals(string)) {
            return false;
        }
        return true;
    }

    public void restoreState(DataInputStream dis) throws IOException {
        label = dis.readUTF();
        string = dis.readUTF();
    }

    public void saveState(DataOutputStream dos) throws IOException {
        dos.writeUTF(label);
        dos.writeUTF(string);
    }
    
    public String toXML() {
        if (Assert.ASSERT_ON) {
            Assert.notNull(label);
            Assert.notNull(string);
        }

        final StringBuffer sb = new StringBuffer("<text-field");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" label=\"").append(label).append("\"");
        sb.append(" string=\"").append(string).append("\"");
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
     * @return the string
     */
    public String getString() {
        return string;
    }

    /**
     * @param string the string to set
     */
    public void setString(String string) {
        this.string = string;
    }
    
}
