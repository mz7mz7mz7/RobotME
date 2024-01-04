package org.robotme.core.log.assertion;

import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author   Marcin Zduniak
 * @uml.dependency   supplier="org.robotme.core.log.assertion.Failure"
 */
public interface Assertion {
    
    /**
     * @return null if assertion checking passed, {@link Failure} object otherwise.
     */
    public Failure checkAssertion(LogEntry logEntry, RobotMEReplaying replayingEngine);
}
