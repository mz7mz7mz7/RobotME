package org.robotme.core.recorder.state;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.util.MIDPUtils;

/**
 * @author Marcin Zduniak
 */
public class FormState extends DisplayableState {

    private AbstractWidgetState[] abstractWidgetStates;
    
    private String tickerString;

    private String title;

    public FormState(Displayable d) {
        storeInternalState(d);
    }

    private void storeInternalState(Displayable d) {
        final Form f = (Form) d;
        abstractWidgetStates = MIDPUtils.getAbstractWidgetStatesFromForm(f);
        
        title = f.getTitle();

        if (null != f.getTicker()) {
            tickerString = f.getTicker().getString();
        }
    }

    public boolean equals(DisplayableState ds) {
        if (null == ds || false == (ds instanceof FormState)) {
            return false;
        }
        
        final FormState fs = (FormState) ds;
        
        if ((null == fs.title && null != title)
                || (null != fs.title && null == title)) {
            return false;
        }
        
        if ((null == fs.tickerString && null != tickerString)
                || (null != fs.tickerString && null == tickerString)) {
            return false;
        }
        
        if ((null == fs.title && null == title) || title.equals(fs.title)) {
            // OK, do nothing
        } else {
            return false;
        }
        
        if ((null == fs.tickerString && null == tickerString) || tickerString.equals(fs.tickerString)) {
            // OK, do nothing
        } else {
            return false;
        }
        
        if (fs.abstractWidgetStates.length == abstractWidgetStates.length) {
            for (int i = 0; i < abstractWidgetStates.length; i++) {
                if (!abstractWidgetStates[i].equals(fs.abstractWidgetStates[i])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    public LogEntry createLogEntry() {
        return new FormModificationLogEntry(LogHandler.INTERNAL_LEVEL, title, tickerString, abstractWidgetStates);
    }

}
