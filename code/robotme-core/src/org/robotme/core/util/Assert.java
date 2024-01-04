package org.robotme.core.util;

import org.robotme.core.recorder.RobotMERecorder;

/**
 * Utility class for making code assertions and throwing unchecked exceptions if they are not true.
 * 
 * <p>
 * <b>For production:</b> Set {@link #ASSERT_ON} field to <code>false</code>, recompile the code and obfuscate it to
 * remove dead code
 * </p>
 * 
 * @author Dawid Weiss
 * @version $Id: Assert.java 120 2007-01-15 22:06:12Z marcinzduniak $
 */
public class Assert {

    /**
     * Set this field to <code>false</code> for production (final build). This should result in removal of dead
     * fragments of the code and the associated overhead.
     */
    /*
     * DO NOT TOUCH THE 'ASSERT-CLASS-REPLACE' SECTION.
     */
    public static final boolean ASSERT_ON = /* @ASSERT-ENABLED-REPLACE@ */true;

    /** No instances of this class */
    private Assert() {
    }

    public static void notNull(int[] arg) {
        if (ASSERT_ON) {
            if (arg == null) {
                throw new AssertionFailedException("Argument must not be null.");
            }
        }
    }

    public static void notNull(Object arg) {
        if (ASSERT_ON) {
            if (arg == null) {
                throw new AssertionFailedException("Argument must not be null.");
            }
        }
    }

    /**
     * Throws a runtime exception if an unreachable code block has been reached.
     */
    public static void unreachableCode() {
        unreachableCode(null);
    }

    /**
     * Throws a runtime exception if an unreachable code block has been reached.
     */
    public static void unreachableCode(String message) {
        if (ASSERT_ON) {
            // if (Log.LOGGING_ON) {
            String msg = "Unreachable code block reached: " + (message != null ? message : "<no msg>");
            AssertionFailedException e = getCurrentStackTrace(msg);
            // Log.error(msg, e);
            RobotMERecorder.LOG.log(msg);
            throw e;
            // }
            // throw new AssertionFailedException();
        }
    }

    /**
     * Throws an <code>AssertionFailedException</code> if the given assertion is not true.
     * 
     * @param assertion
     *            A condition that is supposed to be true
     * @throws AssertionFailedException
     *             if the condition is false
     */
    public static void isTrue(boolean assertion) {
        if (ASSERT_ON) {
            isTrue(assertion, null);
        }
    }

    /**
     * Throws an <code>AssertionFailedException</code> with the given message if the given assertion is not true.
     * 
     * @param assertion
     *            a condition that is supposed to be true
     * @param message
     *            a description of the assertion
     * @throws AssertionFailedException
     *             if the condition is false
     */
    public static void isTrue(boolean assertion, String message) {
        if (ASSERT_ON) {
            if (assertion == false) {
                // if (Log.LOGGING_ON) {
                String msg = "Assertion: " + (message == null ? "" : message);
                AssertionFailedException e = getCurrentStackTrace(msg);
                // RobotMECommon.LOG.log(msg);
                // Log.error(msg, e);
                throw e;
                // }
                // throw new AssertionFailedException();
            }
        }
    }

    /**
     * Throws an <code>AssertionFailedException</code> with the given message if the given assertion is not false.
     */
    public static void isFalse(boolean assertion, String message) {
        isTrue(!assertion, message);
    }

    /**
     * Throws an <code>AssertionFailedException</code> with the given message if the given assertion is not false.
     */
    public static void isFalse(boolean assertion) {
        isTrue(!assertion);
    }

    /**
     * Throws an <code>AssertionFailedException</code> if the given objects are not equal, according to the
     * <code>equals</code> method.
     * 
     * @param expectedValue
     *            the correct value
     * @param actualValue
     *            the value being checked
     * @throws AssertionFailedException
     *             if the two objects are not equal
     */
    public static void equals(Object expectedValue, Object actualValue) {
        if (ASSERT_ON) {
            equals(expectedValue, actualValue, null);
        }
    }

    /**
     * Throws an <code>AssertionFailedException</code> with the given message if the given objects are not equal,
     * according to the <code>equals</code> method.
     * 
     * @param expectedValue
     *            the correct value
     * @param actualValue
     *            the value being checked
     * @param message
     *            a description of the assertion
     * @throws AssertionFailedException
     *             if the two objects are not equal
     */
    public static void equals(Object expectedValue, Object actualValue, String message) {
        if (ASSERT_ON) {
            if (!actualValue.equals(expectedValue)) {
                String msg = "Assertion failed. Expected " + expectedValue + " but encountered " + actualValue
                        + (message != null ? ": " + message : "");
                // if (Log.LOGGING_ON) {
                // Log.error(msg);
                RobotMERecorder.LOG.log(msg);
                throw new AssertionFailedException(msg);
                // }
                // throw new AssertionFailedException();
            }
        }
    }

    /**
     * Compares the content of two byte arrays at specific offsets. If they differ anyhow, an assertion is thrown.
     */
    public static void assertEquals(byte[] source, int sourceOffset, byte[] dest, int destOffset, int length) {
        if (ASSERT_ON) {
            for (int i = 0; i < length; i++) {
                try {
                    if (source[i + sourceOffset] != dest[i + destOffset]) {
                        throw new AssertionFailedException("Arrays differ at offset: " + i + " (source: "
                                + (sourceOffset + i) + ")" + " (dest: " + (destOffset + i) + ")" + " (value: "
                                + source[i + sourceOffset] + " != " + dest[i + destOffset] + ")");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // out of bounds.
                    throw new AssertionFailedException("Arrays differ in length (out of bounds at offset: " + i);
                }
            }
        }
    }

    /**
     * Returns the "current" stack trace for the invoking thread.
     */
    private final static AssertionFailedException getCurrentStackTrace(String msg) {
        try {
            if (msg == null) {
                throw new AssertionFailedException();
            } else {
                throw new AssertionFailedException(msg);
            }
        } catch (AssertionFailedException e) {
            return e;
        }
    }
}
