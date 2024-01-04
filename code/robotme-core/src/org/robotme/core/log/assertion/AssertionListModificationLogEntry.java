package org.robotme.core.log.assertion;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import org.robotme.core.log.entries.ListModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.VariousUtils;

/**
 * @author Marcin Zduniak
 */
public class AssertionListModificationLogEntry extends BaseAssertion {

    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine) {
        final Displayable d = replayingEngine.getCurrentDisplayable();
        if (null == d || !(d instanceof List)) {
            return createAssertionFailure("List is not current displayable");
        }
        final List list = (List) d;
        final ListModificationLogEntry listLogEntry = (ListModificationLogEntry) logEntry;

        final int listSize = list.size();
        boolean[] selectedFlags;
        String[] strings;
        // selectedFlags:
        selectedFlags = new boolean[listSize];
        list.setSelectedFlags(selectedFlags);
        // string:
        strings = new String[listSize];
        for (int i = 0; i < listSize; i++) {
            strings[i] = list.getString(i);
        }

        final boolean selectedFlagsEqual = VariousUtils.equalBooleanTables(listLogEntry.getSelectedFlags(), selectedFlags);
        if (!selectedFlagsEqual) {
            return createAssertionFailure("List has different flags", listLogEntry.getSelectedFlags(), selectedFlags);
        }
        
        final boolean stringsEqual = VariousUtils.equalStringsTables(listLogEntry.getStrings(), strings);
        if (!stringsEqual) {
            return createAssertionFailure("List has different strings", listLogEntry.getStrings(), strings);
        }

        if (!VariousUtils.equalObjects(listLogEntry.getTitle(), list.getTitle())) {
            return createAssertionFailure("List has different title", listLogEntry.getTitle(), list.getTitle());
        }
        
        if (null == list.getTicker() && null == listLogEntry.getTickerString()) {
            return null;
        } else if (null == list.getTicker() || null == listLogEntry.getTickerString()) {
            return createAssertionFailure("List has different tickers", listLogEntry.getTickerString(), (null == list
                    .getTicker() ? null : list.getTicker().getString()));
        } else {
            if (!VariousUtils.equalObjects(listLogEntry.getTickerString(), list.getTicker().getString())) {
                return createAssertionFailure("List has different ticker string", listLogEntry.getTickerString(), list
                        .getTicker().getString());
            }
        }

        return null;
    }

}
