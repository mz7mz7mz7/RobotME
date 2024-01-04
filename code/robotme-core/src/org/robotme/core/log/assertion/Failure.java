package org.robotme.core.log.assertion;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class Failure extends LogEntry {
    
    public static final int LOG_ID = LogEntry.FAILURE_LOG_ID;

    private String failureMessage;

    private String assertionClassName;

    private String expected = "";

    private String present = "";

    /* non-static init block: */{
        setReplayable(false);
        setAssertion(false);
    }

    public Failure() {
    }

    public Failure(long timestamp, String failureMessage, Class assertionClass) {
        setTimestamp(timestamp);
        this.failureMessage = failureMessage;
        this.assertionClassName = assertionClass.getName();
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.failureMessage = dis.readUTF();
        this.assertionClassName = dis.readUTF();
        this.expected = dis.readUTF();
        this.present = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(failureMessage);
        dos.writeUTF(assertionClassName);
        dos.writeUTF(expected);
        dos.writeUTF(present);
        return baos.toByteArray();
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; failureMessage: ").append(failureMessage);
        sb.append("; assertionClassName: ").append(assertionClassName);
        sb.append("; expected: ").append(expected);
        sb.append("; present: ").append(present);
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
            Assert.notNull(failureMessage);
            Assert.notNull(assertionClassName);
        }

        final StringBuffer sb = new StringBuffer("<failure");
        sb.append(" failureMessage=\"").append(failureMessage).append("\"");
        sb.append(" assertionClassName=\"").append(assertionClassName).append("\"");
        if (null != expected) {
            sb.append(" expected=\"").append(expected).append("\"");
        }
        if (null != present) {
            sb.append(" present=\"").append(present).append("\"");
        }
        sb.append("/>");

        return new String[] { xml[0], sb.toString(), xml[2] };
    }
    
    public int getLogId() {
        return LOG_ID;
    }

    /**
     * @return the expected
     */
    public String getExpected() {
        return expected;
    }

    /**
     * @param expected
     *            the expected to set
     */
    public void setExpected(String exptected) {
        this.expected = exptected;
    }

    /**
     * @return the present
     */
    public String getPresent() {
        return present;
    }

    /**
     * @param present
     *            the present to set
     */
    public void setPresent(String present) {
        this.present = present;
    }

    /**
     * @return the assertionClass
     */
    public String getAssertionClassName() {
        return assertionClassName;
    }

    /**
     * @return the failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }

    /**
     * @param assertionClassName
     *            the assertionClassName to set
     */
    public void setAssertionClassName(String assertionClassName) {
        this.assertionClassName = assertionClassName;
    }

    /**
     * @param failureMessage
     *            the failureMessage to set
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

}
