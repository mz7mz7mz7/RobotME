package org.robotme.core.replaying;

import java.util.Hashtable;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.recorder.RobotMERecorder;

/**
 * @author  Marcin Zduniak
 */
public class RobotMEReplaying extends RobotMERecorder {

	protected boolean replayingStarted;

	protected RobotMEReaplyingThread replayingThread;

	protected final Hashtable displayablesWithCommandListener = new Hashtable();

	private RobotMEReplaying() {
		// Nothing here
	}

	public static synchronized RobotMEReplaying getReplayingInstance() {
		if (null == RobotMERecorder.INSTANCE) {
            RobotMERecorder.INSTANCE = new RobotMEReplaying();
		}
		return (RobotMEReplaying) RobotMERecorder.INSTANCE;
	}

	public void startReplaying() {
		if (replayingStarted) {
			return;
		}
		replayingStarted = true;
		replayingThread = new RobotMEReaplyingThread(this);
		replayingThread.start();
	}
	
	public void replayingStopped() {
		// TODO: ensure that replaying thread has been stopped
		try {
			replayingThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void commandListenerSetOnDisplayable(
			CommandListener listener, Displayable disp) {
		LOG.log(new LogEntry(LogHandler.DEBUG_LEVEL,
				"CommandListener set on displayable"));

		final String dispKey = disp.getTitle();
		displayablesWithCommandListener.put(dispKey, listener);
	}

	protected void specificReset() {
		displayablesWithCommandListener.clear();
	}

	public Hashtable getDisplayablesWithCommandListener() {
		return displayablesWithCommandListener;
	}

	public boolean isReplayingStarted() {
		return replayingStarted;
	}
	
}
