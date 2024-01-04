package org.robotme.core.recorder.state;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Displayable;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.AlertModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.util.VariousUtils;

/**
 * @author Marcin Zduniak
 */
public class AlertState extends DisplayableState {

    public static final byte ALERT_TYPE_ALARM = 0;

    public static final byte ALERT_TYPE_CONFIRMATION = 1;

    public static final byte ALERT_TYPE_ERROR = 2;

    public static final byte ALERT_TYPE_INFO = 3;

    public static final byte ALERT_TYPE_WARNING = 4;

    private String tickerString;

    private String title;

    private String string;

    private int timeout;

    private int gaugeValue;

    private byte alertType;

    public AlertState(Displayable d) {
        storeInternalState(d);
    }

    private void storeInternalState(Displayable d) {
        final Alert a = (Alert) d;

        string = a.getString();

        if (null != a.getIndicator()) {
            gaugeValue = a.getIndicator().getValue();
        } else {
            gaugeValue = -1;
        }

        timeout = a.getTimeout();

        if (AlertType.ALARM == a.getType()) {
            alertType = ALERT_TYPE_ALARM;
        } else if (AlertType.CONFIRMATION == a.getType()) {
            alertType = ALERT_TYPE_CONFIRMATION;
        } else if (AlertType.ERROR == a.getType()) {
            alertType = ALERT_TYPE_ERROR;
        } else if (AlertType.INFO == a.getType()) {
            alertType = ALERT_TYPE_INFO;
        } else if (AlertType.WARNING == a.getType()) {
            alertType = ALERT_TYPE_WARNING;
        }

        // title:
        title = a.getTitle();

        // tickerString:
        if (null != a.getTicker()) {
            tickerString = a.getTicker().getString();
        }
    }

    public boolean equals(final DisplayableState ds) {
        if (null == ds || false == (ds instanceof AlertState)) {
            return false;
        }

        final AlertState as = (AlertState) ds;

        // string:
        if (!VariousUtils.equalsString(string, as.string)) {
            return false;
        }

        // string:
        if (!VariousUtils.equalsString(title, as.string)) {
            return false;
        }

        // string:
        if (!VariousUtils.equalsString(tickerString, as.tickerString)) {
            return false;
        }

        // remaining values:
        return (timeout == as.timeout) && (gaugeValue == as.gaugeValue) && (alertType == as.alertType);
    }

    public LogEntry createLogEntry() {
        return new AlertModificationLogEntry(LogHandler.INTERNAL_LEVEL, string, tickerString, title, timeout, gaugeValue, alertType);
    }
}
