package org.robotme.core.log;

import java.io.DataInputStream;
import java.io.IOException;

import org.robotme.core.log.assertion.Failure;
import org.robotme.core.log.entries.CanvasModificationLogEntry;
import org.robotme.core.log.entries.CommandLogEntry;
import org.robotme.core.log.entries.DisplayableChangedLogEntry;
import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.log.entries.ListModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.log.entries.PointerEventOnCanvasLogEntry;
import org.robotme.core.log.entries.TextBoxModificationLogEntry;


/**
 * @author Marcin Zduniak
 */
public class LogEntriesMapper {

	private LogEntriesMapper() {
	}

	public synchronized static final LogEntry createNewLogEntryInstance(
			final int logId, final DataInputStream dis) {
		LogEntry entry;
		switch (logId) {
		case LogEntry.LOG_ID:
			entry = new LogEntry();
			break;
		case CommandLogEntry.LOG_ID:
			entry = new CommandLogEntry();
			break;
		case DisplayableChangedLogEntry.LOG_ID:
			entry = new DisplayableChangedLogEntry();
			break;
		case TextBoxModificationLogEntry.LOG_ID:
			entry = new TextBoxModificationLogEntry();
			break;
        case Failure.LOG_ID:
            entry = new Failure();
            break;
        case KeyEventOnCanvasLogEntry.LOG_ID:
            entry = new KeyEventOnCanvasLogEntry();
            break;
        case PointerEventOnCanvasLogEntry.LOG_ID:
            entry = new PointerEventOnCanvasLogEntry();
            break;
        case CanvasModificationLogEntry.LOG_ID:
            entry = new CanvasModificationLogEntry();
            break;
        case ListModificationLogEntry.LOG_ID:
            entry = new ListModificationLogEntry();
            break;
        case FormModificationLogEntry.LOG_ID:
            entry = new FormModificationLogEntry();
            break;
        default:
			System.err.println("Unrecognized LOG_ID: " + logId);
			return null;
		}

		try {
			entry.initFromDIS(dis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return entry;
	}

}
