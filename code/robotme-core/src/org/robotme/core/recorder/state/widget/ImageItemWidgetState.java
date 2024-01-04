package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class ImageItemWidgetState extends AbstractWidgetState {

    private String label;

    private String altText;
    
    public ImageItemWidgetState(String label, String altText) {
        this.label = label;
        this.altText = altText;
    }
    
    public ImageItemWidgetState() {
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        if (!(abstractWidgetState instanceof ImageItemWidgetState)) {
            return false;
        }

        final ImageItemWidgetState iiws = (ImageItemWidgetState) abstractWidgetState;
        if (!iiws.label.equals(label)) {
            return false;
        }
        if (!iiws.altText.equals(altText)) {
            return false;
        }
        return true;
    }
    
    public void restoreState(DataInputStream dis) throws IOException {
        label = dis.readUTF();
        altText = dis.readUTF();
    }

    public void saveState(DataOutputStream dos) throws IOException {
        dos.writeUTF(label);
        dos.writeUTF(altText);
    }

    public String toXML() {
        if (Assert.ASSERT_ON) {
            Assert.notNull(label);
            Assert.notNull(altText);
        }

        final StringBuffer sb = new StringBuffer("<image");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" label=\"").append(label).append("\"");
        sb.append(" altText=\"").append(altText).append("\"");
        sb.append("/>\n");

        return sb.toString();
    }

    /**
     * @return the altText
     */
    public String getAltText() {
        return altText;
    }

    /**
     * @param altText the altText to set
     */
    public void setAltText(String altText) {
        this.altText = altText;
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
    
}
