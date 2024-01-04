package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Command;

import org.robotme.core.util.Assert;

/**
 * Used when you would like to indicate that some {@link Command} has been invoked by the user.
 * 
 * Supported modes: only REPLAYABLE, assertion must be set to false.
 * 
 * @author Marcin Zduniak
 */
public class CommandLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.COMMAND_LOG_ID;
    
    public static final String INTERNAL_LIST_SELECT_COMMAND_LABEL = "INTERNAL_LIST_SELECT_COMMAND";
    
    public static final String INTERNAL_ALERT_DISMISS_COMMAND_LABEL = "INTERNAL_ALERT_DISMISS_COMMAND";

    private String cmdLabel;

    private String displayableTitle;

    /* non-static init block: */{
        setReplayable(true);
        setAssertion(false);
    }

    public CommandLogEntry() {
    }

    public CommandLogEntry(byte level, String message, String cmdLabel, String displayableTitle) {
        super(level, message);
        this.cmdLabel = cmdLabel;
        this.displayableTitle = displayableTitle;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.displayableTitle = dis.readUTF();
        this.cmdLabel = dis.readUTF();
    }

    public String getCmdLabel() {
        return cmdLabel;
    }

    public String getDisplayableTitle() {
        return displayableTitle;
    }

    public void setCmdLabel(String cmdLabel) {
        this.cmdLabel = cmdLabel;
    }

    public void setDisplayableTitle(String displayableTitle) {
        this.displayableTitle = displayableTitle;
    }

    public int getLogId() {
        return LOG_ID;
    }

    protected Class getReplayerClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.replayable.ReplayableCommandLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(displayableTitle);
        // we are interested only in short labels:
        dos.writeUTF(cmdLabel);
        return baos.toByteArray();
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; displayableTitle: ").append(displayableTitle);
        sb.append("; cmdLabel: ").append(cmdLabel);
        return sb.toString();
    }

    /**
     * @see LogEntry#toXML()
     */
    public String[] toXML() {
        final String[] xml = super.toXML();
        if (Assert.ASSERT_ON) {
            Assert.notNull(xml);
            Assert.isTrue(3 == xml.length);
            Assert.notNull(cmdLabel);
            Assert.notNull(displayableTitle);
        }

        final StringBuffer sb = new StringBuffer("<command");
        sb.append(" cmdLabel=\"").append(cmdLabel).append("\"");
        sb.append(" displayableTitle=\"").append(displayableTitle).append("\"");
        sb.append("/>");

        return new String[] {xml[0], sb.toString(), xml[2]};
    }

}
