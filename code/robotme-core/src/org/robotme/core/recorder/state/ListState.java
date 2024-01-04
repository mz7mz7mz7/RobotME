package org.robotme.core.recorder.state;

import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.ListModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.util.VariousUtils;

/**
 * @author Marcin Zduniak
 */
public class ListState extends DisplayableState {

    private boolean[] selectedFlags;

    private String[] strings;

    private String tickerString;

    private String title;

    public ListState(Displayable d) {
        storeInternalState(d);
    }

    private void storeInternalState(Displayable d) {
        final List l = (List) d;
        final int listSize = l.size();

        // selectedFlags:
        selectedFlags = new boolean[listSize];
        l.getSelectedFlags(selectedFlags);

        // string:
        strings = new String[listSize];
        for (int i = 0; i < listSize; i++) {
            strings[i] = l.getString(i);
        }

        // title:
        title = l.getTitle();

        if (null != l.getTicker()) {
            tickerString = l.getTicker().getString();
        }
    }

    public boolean equals(DisplayableState ds) {
        if (null == ds || false == (ds instanceof ListState)) {
            return false;
        }

        final ListState ls = (ListState) ds;
        final boolean selectedFlagsEqual = VariousUtils.equalBooleanTables(ls.selectedFlags, selectedFlags);
        final boolean stringsEqual = VariousUtils.equalStringsTables(ls.strings, strings);
        return VariousUtils.equalObjects(title, ls.title) && VariousUtils.equalObjects(tickerString, ls.tickerString)
                && selectedFlagsEqual && stringsEqual;

    }

    public LogEntry createLogEntry() {
        return new ListModificationLogEntry(LogHandler.INTERNAL_LEVEL, selectedFlags, strings, title, tickerString);
    }
}
