package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;

import org.robotme.core.util.Assert;

/**
 * Used when you would like to indicate that current {@link Displayable} object has changed.
 * 
 * Supported modes: only ASSERTION.
 * 
 * @author Marcin Zduniak
 */
public class DisplayableChangedLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.DISPLAYABLE_CHANGED_LOG_ID;

    public static final byte CANVAS_TYPE = 1;

    public static final byte ALERT_TYPE = 2;

    public static final byte FORM_TYPE = 3;

    public static final byte LIST_TYPE = 4;

    public static final byte TEXTBOX_TYPE = 5;

    private String title;

    /**
     * One of *_TYPE values defined above.
     * 
     * Not set by default.
     */
    private byte type;

    /* non-static init block: */{
        setReplayable(false);
        setAssertion(true);
    }

    public DisplayableChangedLogEntry() {
    }

    public DisplayableChangedLogEntry(byte level, String message, String title, Object displayableObject) {
        super(level, message);
        this.title = title;
        computeDisplayableTypeAccordingToClass(displayableObject);
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.title = dis.readUTF();
        this.type = dis.readByte();
    }

    private void computeDisplayableTypeAccordingToClass(Object o) {
        if (o instanceof Canvas) {
            type = CANVAS_TYPE;
        } else if (o instanceof Alert) {
            type = ALERT_TYPE;
        } else if (o instanceof Form) {
            type = FORM_TYPE;
        } else if (o instanceof List) {
            type = LIST_TYPE;
        } else if (o instanceof TextBox) {
            type = TEXTBOX_TYPE;
        } else {
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unsupported displayable object");
            }
        }
    }
    
    protected Class getAssertionClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.assertion.AssertionDisplayableChangedLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }

    public String getTitle() {
        return title;
    }

    public byte getType() {
        return type;
    }

    public int getLogId() {
        return LOG_ID;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte[] toByteArray() throws IOException {
        final byte[] data = super.toByteArray();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(data);
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeUTF(title);
        dos.writeByte(type);
        return baos.toByteArray();
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; title: ").append(getTitle());
        sb.append("; type: ").append(getType());
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
        }

        final StringBuffer sb = new StringBuffer("<displayable-changed");
        sb.append(" title=\"").append(title).append("\"");
        sb.append(" type=\"");
        appendTypeToXML(sb);
        sb.append("\"");
        sb.append("/>");

        return new String[] {xml[0], sb.toString(), xml[2]};
    }
    
    private final void appendTypeToXML(StringBuffer sb) {
        switch (type) {
        case CANVAS_TYPE:
            sb.append("CANVAS");
            break;
        case ALERT_TYPE:
            sb.append("ALERT");
            break;
        case FORM_TYPE:
            sb.append("FORM");
            break;
        case LIST_TYPE:
            sb.append("LIST");
            break;
        case TEXTBOX_TYPE:
            sb.append("TEXTBOX");
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unknown type value");
            }
        }
    }

}
