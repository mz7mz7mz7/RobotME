package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.util.Assert;
import org.robotme.core.util.IOUtils;

/**
 * @author Marcin Zduniak
 */
public class ListModificationLogEntry extends LogEntry {
    public static final int LOG_ID = LogEntry.LIST_MODIFICATION_LOG_ID;

    private boolean[] selectedFlags;

    private String[] strings;

    private String title;

    private String tickerString;

    /* non-static init block: */{
        setReplayable(true);
        setAssertion(false);
    }

    public ListModificationLogEntry() {
    }

    public ListModificationLogEntry(byte level, boolean[] selectedFlags, String[] strings, String title,
            String tickerString) {
        super(level, "");
        this.selectedFlags = selectedFlags;
        this.strings = strings;
        this.title = title;
        this.tickerString = tickerString;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.selectedFlags = IOUtils.readBooleanTable(dis);
        this.strings = IOUtils.readStringTable(dis);
        this.title = dis.readUTF();
        this.tickerString = dis.readUTF();
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        if (null == selectedFlags) {
            selectedFlags = new boolean[0];
        }
        IOUtils.saveBooleanTable(dos, selectedFlags);
        if (null == strings) {
            strings = new String[0];
        }
        IOUtils.saveStringTable(dos, strings);
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

    /**
     * @return the selectedFlags
     */
    public boolean[] getSelectedFlags() {
        return selectedFlags;
    }

    /**
     * @param selectedFlags
     *            the selectedFlags to set
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
     * @param strings
     *            the strings to set
     */
    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    protected Class getReplayerClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.replayable.ReplayableListModificationLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; selectedFlags: [");
        if (null != selectedFlags) {
            for (int i = 0; i < selectedFlags.length; i++) {
                sb.append(selectedFlags[i] ? 1 : 0).append(",");
            }
        } else {
            sb.append("null");
        }
        sb.append("]; strings: [");
        if (null != selectedFlags) {
            for (int i = 0; i < strings.length; i++) {
                sb.append(strings[i]).append(",");
            }
        } else {
            sb.append("null");
        }

        sb.append("]; title: ").append(title);
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
            Assert.notNull(selectedFlags);
            Assert.notNull(strings);
            Assert.notNull(title);
            Assert.notNull(tickerString);
        }

        final StringBuffer sb = new StringBuffer("<list-modification");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" title=\"").append(title).append("\"");
        // TODO: Tickers should be handled in more general way (one way for all screens)
        sb.append(" tickerString=\"").append(tickerString).append("\"");
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

        sb.append("</list-modification>");

        return new String[] { xml[0], sb.toString(), xml[2] };
    }
}
