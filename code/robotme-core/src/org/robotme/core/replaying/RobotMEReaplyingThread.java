package org.robotme.core.replaying;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.robotme.core.log.LogEntriesMapper;
import org.robotme.core.log.assertion.Failure;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.core.util.Assert;

/**
 * @author  Marcin Zduniak
 */
public class RobotMEReaplyingThread extends Thread {

    private final static String SCENARIO_FILE = "/robotmelog.rme";

    protected final RobotMEReplaying engine;

    protected long lastSimulationTime;

    protected long lastLogEntryTime;

    public RobotMEReaplyingThread(RobotMEReplaying engine) {
        this.engine = engine;
    }

    public void run() {
        InputStream is = null;
        try {
            is = openLogInputStream();
            final DataInputStream dis = new DataInputStream(is);
            while (true) {
                final int logEntryId = dis.readInt();
                System.out.println("Received logEntryId: " + logEntryId);
                final LogEntry logEntry = LogEntriesMapper.createNewLogEntryInstance(logEntryId, dis);

                System.out.println("Received timestamp: " + logEntry.getTimestamp());
                System.out.println("lastLogEntryTime: " + lastLogEntryTime);
                System.out.println("currTime: " + System.currentTimeMillis());

                if (Assert.ASSERT_ON) {
                    Assert.isTrue(lastLogEntryTime == 0
                            || (lastLogEntryTime != 0 && logEntry.getTimestamp() >= lastLogEntryTime),
                            "Next logEntry should have timestamp >= from previous");
                }

                // time constraints:
                if (lastLogEntryTime > 0) {
                    if (Assert.ASSERT_ON) {
                        Assert.isTrue(lastSimulationTime > 0);
                    }

                    final long entriesDiff = logEntry.getTimestamp() - lastLogEntryTime;
                    final long simulationsDiff = System.currentTimeMillis() - lastSimulationTime;
                    final long diff = entriesDiff - simulationsDiff;
                    if (diff > 0) {
                        try {
                            System.out.println("Waiting time: " + (diff / 1000L) + " seconds");
                            Thread.sleep(diff);
                        } catch (InterruptedException e) {
                            // Ignore
                        }
                    }
                }// end of time constrains

                if (Assert.ASSERT_ON) {
                    Assert.isFalse(logEntry.isAssertion() && logEntry.isReplayable(),
                            "LogEntry can't be assertion and replayable simultaneously");
                }

                if (logEntry.isAssertion()) {
                    System.out.println("Assertion testing...");
                    final Failure failure = logEntry.createAssertion().checkAssertion(logEntry, engine);
                    if (null != failure) {
                        RobotMERecorder.LOG.log(failure);
                        // TODO: log failure somewhere
                    } else {
                        // OK, assertion passed
                        // TODO: should we report it somewhere ?
                    }
                } else if (logEntry.isReplayable()) {
                    System.out.println("Replaying...");
                    logEntry.createReplayer().replay(logEntry, RobotMEReplaying.getReplayingInstance());
                }

                lastLogEntryTime = logEntry.getTimestamp();
                lastSimulationTime = System.currentTimeMillis();
            }
        } catch (java.io.EOFException e) {
            // OK, ignore
        } catch (Throwable t) {
            RobotMERecorder.LOG.error("Exception during replaying scenario", t);
            t.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                // Ignore
            }
            engine.replayingStopped();
        }
    }

    protected InputStream openLogInputStream() {
        return getClass().getResourceAsStream(SCENARIO_FILE);
    }

}
