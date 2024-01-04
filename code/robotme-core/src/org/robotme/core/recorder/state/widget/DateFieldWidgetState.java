package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class DateFieldWidgetState extends AbstractWidgetState {

    private String label;

    private Date date;

    // private int inputMode;

    public DateFieldWidgetState(String label, Date date) {
        this.label = label;
        this.date = date;
    }
    
    public DateFieldWidgetState() {
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        if (!(abstractWidgetState instanceof DateFieldWidgetState)) {
            return false;
        }

        final DateFieldWidgetState dfws = (DateFieldWidgetState) abstractWidgetState;
        if (!dfws.label.equals(label)) {
            return false;
        }
        // TODO: date could be null, we must handle that situation
        if (null == dfws.date && null == date) {
            return true;
        } else if (null == dfws.date || null == date) {
            return false;
        }
        if (!dfws.date.equals(date)) {
            return false;
        }
        return true;
    }
    
    public void restoreState(DataInputStream dis) throws IOException {
        label = dis.readUTF();
        final long t = dis.readLong();
        if (-1 != t) {
            date = new Date(t);    
        } else {
            date = null;
        }
        
    }

    public void saveState(DataOutputStream dos) throws IOException {
        dos.writeUTF(label);
        if (null != date) {
            dos.writeLong(date.getTime());    
        } else {
            dos.writeLong(-1L);
        }
        
    }

    public String toXML() {
        if (Assert.ASSERT_ON) {
            Assert.notNull(label);
        }

        final StringBuffer sb = new StringBuffer("<date-field");
        // TODO: implementation of handling cases where string contains new-line sign
        sb.append(" label=\"").append(label).append("\"");
        final long timeToSerialize = (null == date ? -1L : date.getTime());
        sb.append(" date=\"").append(timeToSerialize).append("\"");
        sb.append("/>\n");

        return sb.toString();
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    
}
