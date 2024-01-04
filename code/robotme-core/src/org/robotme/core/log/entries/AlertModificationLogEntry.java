package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.recorder.state.AlertState;
import org.robotme.core.util.Assert;

/**
 * Used when you would like to indicate that some property of {@link Alert} has changed.
 * 
 * When assertion == true and replayable == false used as assertion,
 * when assertion == false and replayable == true used as event to simulate.
 * 
 * @author Marcin Zduniak
 */
public class AlertModificationLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.ALERT_MODIFICATION_LOG_ID;
    
    private String string;

    private String tickerString;

    private String title;

    private int timeout;

    private int gaugeValue;

    private byte alertType;

    /* non-static init block: */{
        // You are not able to modify Alert screen, this kind of screens 
        // could have only assertions verified
        setReplayable(false);
        setAssertion(true);
    }

    public AlertModificationLogEntry() {
    }

    public AlertModificationLogEntry(byte level, String string, String tickerString, String title, int timeout, int gaugeValue, byte alertType) {
        super(level, "");
        this.string = string;
        this.tickerString = tickerString;
        this.title = title;
        this.timeout = timeout;
        this.gaugeValue = gaugeValue;
        this.alertType = alertType;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.string = dis.readUTF();
        this.tickerString = dis.readUTF();
        this.title = dis.readUTF();
        this.timeout = dis.readInt();
        this.gaugeValue = dis.readInt();
        this.alertType = dis.readByte();
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(string);
        if (null == tickerString) {
            tickerString = "";
        }
        dos.writeUTF(tickerString);
        dos.writeUTF(title);
        dos.writeInt(timeout);
        dos.writeInt(gaugeValue);
        dos.writeByte(alertType);
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
    
    /**
     * @return the alertType
     */
    public byte getAlertType() {
        return alertType;
    }

    /**
     * @param alertType the alertType to set
     */
    public void setAlertType(byte alertType) {
        this.alertType = alertType;
    }

    /**
     * @return the gaugeValue
     */
    public int getGaugeValue() {
        return gaugeValue;
    }

    /**
     * @param gaugeValue the gaugeValue to set
     */
    public void setGaugeValue(int gaugeValue) {
        this.gaugeValue = gaugeValue;
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
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
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
        // TODO: implement below class
        final String DELEGATE_CLASS = "org.robotme.core.log.assertion.AssertionAlertModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; string: ").append(string);
        sb.append("; tickerString: ").append(tickerString);
        sb.append("; title: ").append(title);
        sb.append("; timeout: ").append(timeout);
        sb.append("; gaugeValue: ").append(gaugeValue);
        sb.append("; alertType: ").append(alertType);
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

        final StringBuffer sb = new StringBuffer("<alert-modification");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" string=\"").append(string).append("\"");
        sb.append(" title=\"").append(title).append("\"");
        if (null != tickerString) {
            sb.append(" tickerString=\"").append(tickerString).append("\"");
        }
        sb.append(" timeout=\"").append(timeout).append("\"");
        sb.append(" gaugeValue=\"").append(gaugeValue).append("\"");
        
        sb.append(" alertType=\"");
        appendAlertTypeToXML(sb);
        sb.append("\"");
        
        sb.append("/>");

        return new String[] {xml[0], sb.toString(), xml[2]};
    }
    
    private final void appendAlertTypeToXML(final StringBuffer sb) {
        switch (alertType) {
        case AlertState.ALERT_TYPE_ALARM:
            sb.append("ALARM");
            break;
        case AlertState.ALERT_TYPE_CONFIRMATION:
            sb.append("CONFIRMATION");
            break;
        case AlertState.ALERT_TYPE_ERROR:
            sb.append("ERROR");
            break;
        case AlertState.ALERT_TYPE_INFO:
            sb.append("INFO");
            break;
        case AlertState.ALERT_TYPE_WARNING:
            sb.append("WARNING");
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unknown alertType value");
            }
        }
    }

}
