package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.TextBox;

import org.robotme.core.util.Assert;

/**
 * Used when you would like to indicate that some property of {@link TextBox} has changed.
 * 
 * When assertion == true and replayable == false used as assertion,
 * when assertion == false and replayable == true used as event to simulate.
 * 
 * @author Marcin Zduniak
 */
public class TextBoxModificationLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.TEXTBOX_MODIFICATION_LOG_ID;

    private String string;
    
    // TODO: add other fields from TextBox (eg: title)

    /* non-static init block: */{
        setReplayable(true);
        setAssertion(false);
    }

    public TextBoxModificationLogEntry() {
    }

    public TextBoxModificationLogEntry(byte level, String string) {
        super(level, "");
        this.string = string;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.string = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(string);
        return baos.toByteArray();
    }

    public int getLogId() {
        return LOG_ID;
    }

    public String getString() {
        return string;
    }
    
    public void setString(String string) {
        this.string = string;
    }

    protected Class getReplayerClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.replayable.ReplayableTextBoxModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }
    
    protected Class getAssertionClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.assertion.AssertionTextBoxModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; string: ").append(string);
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
            Assert.notNull(string);
        }

        final StringBuffer sb = new StringBuffer("<textbox-modification");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" string=\"").append(string).append("\"");
        sb.append("/>");

        return new String[] {xml[0], sb.toString(), xml[2]};
    }

}
