package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class CanvasModificationLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.CANVAS_MODIFICATION_LOG_ID;

    private String title;

    private String tickerString;

    /* non-static init block: */{
        setReplayable(false);
        setAssertion(true);
    }

    public CanvasModificationLogEntry() {
    }

    public CanvasModificationLogEntry(byte level, String title, String tickerString) {
        super(level, "");
        this.title = title;
        this.tickerString = tickerString;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.title = dis.readUTF();
        this.tickerString = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(title);
        dos.writeUTF(null != tickerString ? tickerString : "");
        return baos.toByteArray();
    }

    public int getLogId() {
        return LOG_ID;
    }
    
    /**
     * @return the tickerString
     */
    public String getTickerString() {
        return tickerString;
    }

    /**
     * @param tickerString the tickerString to set
     */
    public void setTickerString(String tickerString) {
        this.tickerString = tickerString;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    protected Class getAssertionClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.assertion.AssertionCanvasModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; title: ").append(title);
        sb.append("; tickerString: ").append(tickerString);
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
            Assert.notNull(title);
            Assert.notNull(tickerString);
        }

        final StringBuffer sb = new StringBuffer("<canvas-modification");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" title=\"").append(title).append("\"");
        sb.append(" tickerString=\"").append(tickerString).append("\"");
        sb.append("/>");

        return new String[] {xml[0], sb.toString(), xml[2]};
    }

}
