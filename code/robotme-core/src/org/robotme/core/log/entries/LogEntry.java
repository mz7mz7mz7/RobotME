package org.robotme.core.log.entries;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.assertion.Assertion;
import org.robotme.core.log.replayable.Replayable;
import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class LogEntry {

    /**
     * Mask for all RobotME-internal {@link #LOG_ID}s.
     */
    private static final int ROBOTME_LOGENTRY_MASK = 0;

    /**
     * Mask for all {@link #LOG_ID}s created outside of RobotME framework.
     */
    public static final int USERDEFINED_LOGENTRY_MASK = 0x01 << 31;

    /**
     * All subclasses <b>MUST</b> provide their own unique {@link #LOG_ID}.
     */
    public static final int LOG_ID = ROBOTME_LOGENTRY_MASK | 1;

    /* All LogEntries IDs located in one place: */
    public static final int FAILURE_LOG_ID = ROBOTME_LOGENTRY_MASK | 2;

    public static final int DISPLAYABLE_CHANGED_LOG_ID = ROBOTME_LOGENTRY_MASK | 3;

    public static final int TEXTBOX_MODIFICATION_LOG_ID = ROBOTME_LOGENTRY_MASK | 4;

    public static final int COMMAND_LOG_ID = ROBOTME_LOGENTRY_MASK | 5;

    public static final int KEY_EVENT_ON_CANVAS_LOG_ID = ROBOTME_LOGENTRY_MASK | 6;

    public static final int POINTER_EVENT_ON_CANVAS_LOG_ID = ROBOTME_LOGENTRY_MASK | 7;
    
    public static final int CANVAS_MODIFICATION_LOG_ID = ROBOTME_LOGENTRY_MASK | 8;
    
    public static final int LIST_MODIFICATION_LOG_ID = ROBOTME_LOGENTRY_MASK | 9;
    
    public static final int FORM_MODIFICATION_LOG_ID = ROBOTME_LOGENTRY_MASK | 10;
    
    public static final int ALERT_MODIFICATION_LOG_ID = ROBOTME_LOGENTRY_MASK | 11;

    /* End of LogEntries IDs */

    private byte level;

    private long timestamp;

    private String message;

    private String exception;

    // private byte[] data;

    /**
     * <p>
     * <code>true</code> if this entry can be replayed in replaying state (eg. pressing a button), <code>false</code>
     * otherwise.
     * <p>
     * <code>false</code> by default.
     */
    private boolean replayable;

    /**
     * <p>
     * Indicate whether this entry should be treated as assertion. If set to <code>true</code> takes precedence over
     * {@link #replayable} field -- only those {@link LogEntry}'ies that are not assertions could be replayed.
     * 
     * <p>
     * <code>false</code> by default.
     */
    private boolean assertion;

    /**
     * Empty constructor. Needs to be initiated later on.
     */
    public LogEntry() {
        this(LogHandler.INTERNAL_LEVEL, "");
    }

    public LogEntry(byte level, String message) {
        this(level, message, null);
    }

    public LogEntry(byte level, String message, Throwable throwable) {
        super();
        this.level = level;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        if (null != throwable) {
            final String errMsg = throwable.getMessage();
            final StringBuffer sb = new StringBuffer();
            sb.append("[").append(throwable.getClass().getName()).append("] : ").append(null != errMsg ? errMsg : "");
            exception = sb.toString();
        } else {
            exception = "";
        }
    }

    /**
     * Initiates {@link LogEntry} from given {@link DataInputStream} object.
     * 
     * @param dis
     * @throws IOException
     */
    public void initFromDIS(DataInputStream dis) throws IOException {
        this.level = dis.readByte();
        this.timestamp = dis.readLong();
        this.replayable = dis.readBoolean();
        this.assertion = dis.readBoolean();
        this.message = dis.readUTF();
        this.exception = dis.readUTF();
    }

    /**
     * Writes the data into a byte buffer.
     * 
     * @return a byte array containing the data
     * @throws IOException
     *             when the data could not be written
     */
    public byte[] toByteArray() throws IOException {
        // if (this.data == null) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(getLogId());
        dos.writeByte(this.level);
        dos.writeLong(this.timestamp);
        dos.writeBoolean(this.replayable);
        dos.writeBoolean(this.assertion);
        dos.writeUTF(this.message);
        dos.writeUTF(this.exception);
        // this.data = baos.toByteArray();
        // }
        // return this.data;
        return baos.toByteArray();
    }

    public final String getException() {
        return exception;
    }

    public final long getTimestamp() {
        return timestamp;
    }

    public final byte getLevel() {
        return level;
    }

    public final String getMessage() {
        return message;
    }

    /**
     * This method MUST be overridden in every classes that has different LOG_ID.
     */
    public int getLogId() {
        return LOG_ID;
    }

    public final boolean isReplayable() {
        return replayable;
    }

    public final void setReplayable(boolean replayable) {
        this.replayable = replayable;
    }

    public final boolean isAssertion() {
        return assertion;
    }

    public final void setAssertion(boolean assertion) {
        this.assertion = assertion;
    }

    public final void setException(String exception) {
        this.exception = exception;
    }

    public final void setLevel(byte level) {
        this.level = level;
    }

    public final void setMessage(String message) {
        this.message = message;
    }

    public final void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Replayable log entries should override this method and provide their specific implementation.
     * 
     * @throws IllegalStateException
     *             if replaying is not supported by this log entry
     */
    protected Class getReplayerClass() {
        throw new IllegalStateException();
    }

    /**
     * Assertionable log entries should override this method and provide their specific implementation.
     * 
     * @throws IllegalStateException
     *             if checking assertions is not supported by this log entry
     */
    protected Class getAssertionClass() {
        throw new IllegalStateException();
    }

    /**
     * Create object that is able to replay (simulate) this log in runtime.
     * 
     * @return Replayable object
     * @throws IllegalStateException
     *             if replaying is not supported by this log entry
     */
    public final Replayable createReplayer() {
        Object o;
        try {
            o = getReplayerClass().newInstance();
            if (o instanceof Replayable) {
                return (Replayable) o;
            } else {
                throw new IllegalStateException("ReplayerClass is not of Replayer type");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Create object that is able to check assertion of this LogEntry in runtime.
     * 
     * @return Assertion object
     * @throws IllegalStateException
     *             if checking assertion is not supported by this log entry
     */
    public final Assertion createAssertion() {
        Object o;
        try {
            o = getAssertionClass().newInstance();
            if (o instanceof Assertion) {
                return (Assertion) o;
            } else {
                throw new IllegalStateException("AssertionClass is not of Assertion type");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Returns {@link Class} object that corresponds to given <code>className</code>.
     * 
     * @param className
     * @return
     */
    protected final Class getReplayerClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("class: ").append(getClass().getName());
        sb.append("; id: ").append(getLogId());
        sb.append("; level: ").append(getLevel());
        sb.append("; timestamp: ").append(getTimestamp());
        sb.append("; replayable: ").append(isReplayable());
        sb.append("; assertion: ").append(isAssertion());
        sb.append("; msg: ").append(getMessage());
        sb.append("; ex: ").append(getException());
        return sb.toString();
    }

    /**
     * This method converts internal LogEntry structure into XML format. This method is not used within robotme-core
     * project so should be removed from MIDlet during obfuscation. It is here only for clarity and consistency.
     * 
     * @return array of strings with generated XML. Generated XML is not stored in single variable in order to allow
     *         subclasses of this class to insert some other XML parts between beginning and ending XML statements.
     */
    public String[] toXML() {
        final StringBuffer sb = new StringBuffer("<event");

        sb.append(" level=\"");
        appendLevelToXML(sb);
        sb.append("\"");

        sb.append(" timestamp=\"").append(timestamp).append("\"");
        if (null != message) {
            sb.append(" message=\"").append(message).append("\"");
        }
        if (null != exception) {
            sb.append(" exception=\"").append(exception).append("\"");
        }

        sb.append(" replayable=\"").append(replayable).append("\"");
        sb.append(" assertion=\"").append(assertion).append("\"");

        final String beginning = sb.append(">").toString();
        final String logEventNode = "<log-event/>";
        final String ending = "</event>";
        return new String[] { beginning, logEventNode, ending };
    }

    private final void appendLevelToXML(StringBuffer sb) {
        switch (level) {
        case LogHandler.ALL_LEVEL:
            sb.append("ALL");
            break;
        case LogHandler.DEBUG_LEVEL:
            sb.append("DEBUG");
            break;
        case LogHandler.INFO_LEVEL:
            sb.append("INFO");
            break;
        case LogHandler.WARN_LEVEL:
            sb.append("WARN");
            break;
        case LogHandler.ERROR_LEVEL:
            sb.append("ERROR");
            break;
        case LogHandler.FATAL_LEVEL:
            sb.append("FATAL");
            break;
        case LogHandler.OFF_LEVEL:
            sb.append("OFF");
            break;
        case LogHandler.INTERNAL_LEVEL:
            sb.append("INTERNAL");
            break;
        default:
            if (Assert.ASSERT_ON) {
                Assert.unreachableCode("Unknown level value");
            }
        }
    }
}
