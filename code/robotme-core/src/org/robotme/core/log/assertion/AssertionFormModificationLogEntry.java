package org.robotme.core.log.assertion;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.MIDPUtils;
import org.robotme.core.util.VariousUtils;

/**
 * TODO: is this class used ?
 * 
 * @author Marcin Zduniak
 */
public class AssertionFormModificationLogEntry extends BaseAssertion {

    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine) {
        final Displayable d = replayingEngine.getCurrentDisplayable();
        if (null == d || !(d instanceof Form)) {
            return createAssertionFailure("Form is not current displayable");
        }
        final Form form = (Form) d;
        final FormModificationLogEntry formLogEntry = (FormModificationLogEntry) logEntry;

        if (!VariousUtils.equalObjects(formLogEntry.getTitle(), form.getTitle())) {
            return createAssertionFailure("Form has different title", formLogEntry.getTitle(), form.getTitle());
        }

        if (null == form.getTicker() && null == formLogEntry.getTickerString()) {
            return null;
        } else if (null == form.getTicker() || null == formLogEntry.getTickerString()) {
            return createAssertionFailure("Form has different tickers", formLogEntry.getTickerString(), (null == form
                    .getTicker() ? null : form.getTicker().getString()));
        } else {
            final AbstractWidgetState[] abstractWidgetStates = formLogEntry.getAbstractWidgetStates();
            final AbstractWidgetState[] formAbstractWidgetStates = MIDPUtils.getAbstractWidgetStatesFromForm(form);
            if (formAbstractWidgetStates.length == abstractWidgetStates.length) {
                for (int i = 0; i < abstractWidgetStates.length; i++) {
                    if (!abstractWidgetStates[i].equals(formAbstractWidgetStates[i])) {
                        return createAssertionFailure("Form has different widgets", abstractWidgetStates[i],
                                formAbstractWidgetStates[i]);
                    }
                }
                return null;
            } else {
                return createAssertionFailure("Form has different widgets count", new Integer(
                        abstractWidgetStates.length), new Integer(formAbstractWidgetStates.length));
            }
        }
    }

}
