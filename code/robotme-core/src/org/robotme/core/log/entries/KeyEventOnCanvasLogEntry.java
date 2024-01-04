package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Canvas;

import org.robotme.core.util.Assert;

/**
 * Used when you would like to indicate that user pressed some key on the {@link Canvas}.
 * 
 * Supported modes: only REPLAYABLE.
 * 
 * @author Marcin Zduniak
 */
public class KeyEventOnCanvasLogEntry extends LogEntry {

    public static final int LOG_ID = LogEntry.KEY_EVENT_ON_CANVAS_LOG_ID;

    public static final byte KEY_PRESSED_TYPE = 1;

    public static final byte KEY_RELEASED_TYPE = 2;

    public static final byte KEY_REPEATED_TYPE = 3;

    /**
     * According to {@link Canvas} JavaDoc this value is considered invalid for key codes.
     */
    public static final int NOT_SELECTED_KEY = 0;

    /**
     * One of KEY_*_TYPE values defined above.
     * 
     * Not set by default.
     * 
     * TODO: consider if this field should have {@link #KEY_PRESSED_TYPE} set by default.
     */
    private byte type = KEY_PRESSED_TYPE;

    private String displayableTitle;

    private int keyCode = NOT_SELECTED_KEY;

    private int gameAction = NOT_SELECTED_KEY;

    /* non-static init block: */{
        setReplayable(true);
        setAssertion(false);
    }

    public KeyEventOnCanvasLogEntry() {
    }

    public KeyEventOnCanvasLogEntry(byte level, int keyCodeOrGameAction, byte type, String displayableTitle,
            boolean validGameAction) {
        super(level, "");
        if (validGameAction) {
            this.gameAction = keyCodeOrGameAction;
        } else {
            this.keyCode = keyCodeOrGameAction;
        }
        this.type = type;
        this.displayableTitle = displayableTitle;
    }

    public void initFromDIS(DataInputStream dis) throws IOException {
        super.initFromDIS(dis);
        this.keyCode = dis.readInt();
        this.gameAction = dis.readInt();
        this.type = dis.readByte();
        this.displayableTitle = dis.readUTF();
    }

    /**
     * @return the gameAction
     */
    public int getGameAction() {
        return gameAction;
    }

    /**
     * @param gameAction
     *            the gameAction to set
     */
    public void setGameAction(int gameAction) {
        this.keyCode = NOT_SELECTED_KEY;
        this.gameAction = gameAction;
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

    /**
     * @return the keyCode
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * @param keyCode
     *            the keyCode to set
     */
    public void setKeyCode(int keyCode) {
        this.gameAction = NOT_SELECTED_KEY;
        this.keyCode = keyCode;
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
        dos.writeInt(keyCode);
        dos.writeInt(gameAction);
        dos.writeByte(type);
        dos.writeUTF(displayableTitle);
        return baos.toByteArray();
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer(super.toString());
        sb.append("; keyCode: ").append(keyCode);
        sb.append("; gameAction: ").append(gameAction);
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
            Assert.isFalse(NOT_SELECTED_KEY == keyCode && NOT_SELECTED_KEY == gameAction, "One of keyCode or gameAction must be set.");
            Assert.isFalse(NOT_SELECTED_KEY != keyCode && NOT_SELECTED_KEY != gameAction, "Only one of keyCode or gameAction must be set.");
        }

        final StringBuffer sb = new StringBuffer("<key-event");
        if (NOT_SELECTED_KEY != keyCode) {
            sb.append(" keyCode=\"").append(keyCode).append("\"");            
        } else if (NOT_SELECTED_KEY != gameAction) {
            sb.append(" gameAction=\"").append(gameAction).append("\"");
        } else {
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("One of keyCode or gameAction must be set.");
            }
        }
        
        sb.append(" type=\"");
        appendTypeToXML(sb);
        sb.append("\"");
        sb.append(" displayableTitle=\"").append(displayableTitle).append("\"");
        sb.append("/>");

        return new String[] { xml[0], sb.toString(), xml[2] };
    }

    private final void appendTypeToXML(StringBuffer sb) {
        switch (type) {
        case KEY_PRESSED_TYPE:
            sb.append("PRESSED");
            break;
        case KEY_RELEASED_TYPE:
            sb.append("RELEASED");
            break;
        case KEY_REPEATED_TYPE:
            sb.append("REPEATED");
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unknown type value");
            }
        }
    }

    protected Class getReplayerClass() {
        final String DELEGATE_CLASS = "org.robotme.core.log.replayable.ReplayableKeyEventOnCanvasLogEntry";
        return getReplayerClass(DELEGATE_CLASS);
    }

}
