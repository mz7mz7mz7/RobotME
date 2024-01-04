package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Canvas;

import org.robotme.core.util.Assert;

/**
 * Used when you would like to indicate that user pointed some position on the {@link Canvas} using pointer indicator.
 * 
 * Supported modes: only REPLAYABLE.
 * 
 * @author Marcin Zduniak
 */
public class PointerEventOnCanvasLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.POINTER_EVENT_ON_CANVAS_LOG_ID;

    public static final byte POINTER_PRESSED_TYPE = 1;

    public static final byte POINTER_RELEASED_TYPE = 2;

    public static final byte POINTER_DRAGGED_TYPE = 3;

    /**
     * One of POINTER_*_TYPE values defined above.
     * 
     * Not set by default.
     * 
     * TODO: consider if this field should have {@link #POINTER_PRESSED_TYPE} set by default.
     */
    private byte type;

    private String displayableTitle;

    private int x, y;

    /* non-static init block: */{
        setReplayable(true);
        setAssertion(false);
    }

    public PointerEventOnCanvasLogEntry() {
    }

    public PointerEventOnCanvasLogEntry(byte level, int x, int y, byte type, String displayableTitle) {
        super(level, "");
        this.x = x;
        this.y = y;
        this.type = type;
        this.displayableTitle = displayableTitle;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.x = dis.readInt();
        this.y = dis.readInt();
        this.type = dis.readByte();
        this.displayableTitle = dis.readUTF();
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the displayableTitle
     */
    public String getDisplayableTitle() {
        return displayableTitle;
    }

    /**
     * @param displayableTitle
     *            the displayableTitle to set
     */
    public void setDisplayableTitle(String displayableTitle) {
        this.displayableTitle = displayableTitle;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getLogId() {
        return LOG_ID;
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(x);
        dos.writeInt(y);
        dos.writeByte(type);
        dos.writeUTF(displayableTitle);
        return baos.toByteArray();
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; x: ").append(x);
        sb.append("; y: ").append(y);
        sb.append("; type: ").append(type);
        sb.append("; title: ").append(displayableTitle);
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
            Assert.notNull(displayableTitle);
        }

        final StringBuffer sb = new StringBuffer("<pointer-event");
        sb.append(" x=\"").append(x).append("\"");
        sb.append(" y=\"").append(y).append("\"");
        sb.append(" type=\"");
        appendTypeToXML(sb);
        sb.append("\"");
        sb.append(" displayableTitle=\"").append(displayableTitle).append("\"");
        sb.append("/>");

        return new String[] { xml[0], sb.toString(), xml[2] };
    }

    private final void appendTypeToXML(StringBuffer sb) {
        switch (type) {
        case POINTER_PRESSED_TYPE:
            sb.append("PRESSED");
            break;
        case POINTER_RELEASED_TYPE:
            sb.append("RELEASED");
            break;
        case POINTER_DRAGGED_TYPE:
            sb.append("DRAGGED");
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unknown type value");
            }
        }
    }

    protected Class getReplayerClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.replayable.ReplayablePointerEventOnCanvasLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }

}
