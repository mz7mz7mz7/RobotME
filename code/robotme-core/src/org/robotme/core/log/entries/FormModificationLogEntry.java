package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.util.Assert;
import org.robotme.core.util.IOUtils;

/**
 * @author Marcin Zduniak
 */
public class FormModificationLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.FORM_MODIFICATION_LOG_ID;

    private AbstractWidgetState[] abstractWidgetStates;

    private String title;

    private String tickerString;
    
    /* non-static init block: */{
        setReplayable(true);
        setAssertion(false);
    }

    public FormModificationLogEntry() {
    }

    public FormModificationLogEntry(byte level, String title, String tickerString,
            AbstractWidgetState[] abstractWidgetStates) {
        super(level, "");
        this.title = title;
        this.tickerString = tickerString;
        this.abstractWidgetStates = abstractWidgetStates;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.abstractWidgetStates = IOUtils.readAbstractWidgetStateTable(dis);
        this.title = dis.readUTF();
        this.tickerString = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        IOUtils.saveAbstractWidgetStateTable(dos, abstractWidgetStates);
        dos.writeUTF(title);
        if (null == tickerString) {
            tickerString = "";
        }
        dos.writeUTF(tickerString);
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
     * @param tickerString
     *            the tickerString to set
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
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    protected Class getAssertionClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.assertion.AssertionFormModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }
    
    protected Class getReplayerClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.replayable.ReplayableFormModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; title: ").append(title);
        sb.append("; tickerString: ").append(tickerString);
        sb.append("; abstractWidgetStates: [");
        if (Assert.ASSERT_ON) {
            Assert.notNull(abstractWidgetStates);
        }
        for (int i = 0, n = abstractWidgetStates.length; i < n; i++) {
            sb.append(abstractWidgetStates[i].getClass().getName()).append(",");
        }
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
            Assert.notNull(abstractWidgetStates);
            Assert.notNull(title);
            Assert.notNull(tickerString);
        }

        final StringBuffer sb = new StringBuffer("<form-modification");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" title=\"").append(title).append("\"");
        // TODO: Tickers should be handled in more general way (one way for all screens)
        if (null != tickerString) {
            sb.append(" tickerString=\"").append(tickerString).append("\"");
        }
        sb.append(">\n");

        sb.append("\t<items>\n");

        for (int i = 0, n = abstractWidgetStates.length; i < n; i++) {
            sb.append("\t\t").append(abstractWidgetStates[i].toXML()).append("\n");
        }

        sb.append("\t</items>\n");
        sb.append("</form-modification>");

        return new String[] { xml[0], sb.toString(), xml[2] };
    }

    /**
     * @return the abstractWidgetStates
     */
    public AbstractWidgetState[] getAbstractWidgetStates() {
        return abstractWidgetStates;
    }

    /**
     * @param abstractWidgetStates the abstractWidgetStates to set
     */
    public void setAbstractWidgetStates(AbstractWidgetState[] abstractWidgetStates) {
        this.abstractWidgetStates = abstractWidgetStates;
    }

}
