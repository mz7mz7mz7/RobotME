package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;
import org.robotme.core.util.IOUtils;
import org.robotme.core.util.VariousUtils;

/**
 * @author Marcin Zduniak
 */
public class ChoiceGroupWidgetState extends AbstractWidgetState {

    private boolean[] selectedFlags;

    private String[] strings;

    private String label;

    public ChoiceGroupWidgetState(String label, boolean[] selectedFlags, String[] strings) {
        this.label = label;
        this.selectedFlags = selectedFlags;
        this.strings = strings;
    }
    
    public ChoiceGroupWidgetState() {
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        if (!(abstractWidgetState instanceof ChoiceGroupWidgetState)) {
            return false;
        }

        final ChoiceGroupWidgetState cgws = (ChoiceGroupWidgetState) abstractWidgetState;
        if (!cgws.label.equals(label)) {
            return false;
        }
        final boolean selectedFlagsEqual = VariousUtils.equalBooleanTables(cgws.selectedFlags, selectedFlags);
        if (!selectedFlagsEqual) {
            return false;
        }
        final boolean stringsEqual = VariousUtils.equalStringsTables(cgws.strings, strings);
        if (!stringsEqual) {
            return false;
        }

        return true;
    }
    
    public void restoreState(DataInputStream dis) throws IOException {
        label = dis.readUTF();
        selectedFlags = IOUtils.readBooleanTable(dis);
        strings = IOUtils.readStringTable(dis);
    }

    public void saveState(DataOutputStream dos) throws IOException {
        dos.writeUTF(label);
        IOUtils.saveBooleanTable(dos, selectedFlags);
        IOUtils.saveStringTable(dos, strings); 
    }

    public String toXML() {
        if (Assert.ASSERT_ON) {
            Assert.notNull(selectedFlags);
            Assert.notNull(strings);
            Assert.notNull(label);
        }

        final StringBuffer sb = new StringBuffer("<choice-group");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" label=\"").append(label).append("\"");
        sb.append(">\n");

        sb.append("\t<selectedFlags>\n");
        for (int i = 0; i < selectedFlags.length; i++) {
            sb.append("\t\t<value>").append(selectedFlags[i] ? "true" : "false").append("</value>\n");
        }
        sb.append("\t</selectedFlags>\n");

        sb.append("\t<strings>\n");
        for (int i = 0; i < strings.length; i++) {
            sb.append("\t\t<value>").append(strings[i]).append("</value>\n");
        }
        sb.append("\t</strings>\n");

        sb.append("</choice-group>");

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
     * @return the selectedFlags
     */
    public boolean[] getSelectedFlags() {
        return selectedFlags;
    }

    /**
     * @param selectedFlags the selectedFlags to set
     */
    public void setSelectedFlags(boolean[] selectedFlags) {
        this.selectedFlags = selectedFlags;
    }

    /**
     * @return the strings
     */
    public String[] getStrings() {
        return strings;
    }

    /**
     * @param strings the strings to set
     */
    public void setStrings(String[] strings) {
        this.strings = strings;
    }
    
}

