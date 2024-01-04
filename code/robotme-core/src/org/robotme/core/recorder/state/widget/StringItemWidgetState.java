package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class StringItemWidgetState extends AbstractWidgetState {

    private String label;

    private String text;

    public StringItemWidgetState(String label, String text) {
        this.label = label;
        this.text = text;
    }

    public StringItemWidgetState() {
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        if (!(abstractWidgetState instanceof StringItemWidgetState)) {
            return false;
        }

        final StringItemWidgetState siws = (StringItemWidgetState) abstractWidgetState;
        if (!siws.label.equals(label)) {
            return false;
        }
        if (!siws.text.equals(text)) {
            return false;
        }
        return true;
    }

    public void restoreState(DataInputStream dis) throws IOException {
        label = dis.readUTF();
        text = dis.readUTF();
    }

    public void saveState(DataOutputStream dos) throws IOException {
        dos.writeUTF(label);
        dos.writeUTF(text);
    }

    public String toXML() {
        if (Assert.ASSERT_ON) {
            Assert.notNull(label);
            Assert.notNull(text);
        }

        final StringBuffer sb = new StringBuffer("<string-item");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" label=\"").append(label).append("\"");
        sb.append(" text=\"").append(text).append("\"");
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
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

}
