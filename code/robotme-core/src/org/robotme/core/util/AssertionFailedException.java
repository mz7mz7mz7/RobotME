package org.robotme.core.util;

/**
 * An exception thrown when an assertion was not fulfilled in the code.
 * 
 * @author Dawid Weiss
 * @version $Id: AssertionFailedException.java 43 2006-08-04 08:08:11Z marcinzduniak $
 */
public class AssertionFailedException extends RuntimeException {

    /**
     * Creates an <code>AssertionFailedException</code> with no associated
     * message.
     */
    public AssertionFailedException() {
    }

    /**
     * Creates a <code>AssertionFailedException</code> with the given detail
     * message.
     * 
     * @param message
     *            A description of the assertion
     */
    public AssertionFailedException(String message) {
        super(message);
    }
}

