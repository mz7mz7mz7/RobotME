package org.robotme.core.log.assertion;

import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public abstract class BaseAssertion implements Assertion {

    protected final Failure createAssertionFailure(String failureMessage) {
        return new Failure(System.currentTimeMillis(), failureMessage, this.getClass());
    }
    
    protected final Failure createAssertionFailure(String failureMessage, Object expected, Object present) {
        if (Assert.ASSERT_ON) {
            Assert.notNull(expected);
            Assert.notNull(present);
        }
        final Failure f = createAssertionFailure(failureMessage);
        f.setExpected(expected.toString());
        f.setPresent(present.toString());
        return f;
    }
}
