package org.robotme.core.util;

/**
 * @author Marcin Zduniak
 */
public class VariousUtils {

    private VariousUtils() {
        // Do not instantiate
    }

    public static final boolean equalObjects(final Object o1, final Object o2) {
        if (null != o1 && null != o2) {
            return o1.equals(o2);
        } else if (null != o1 || null != o2) {
            return false;
        } else {
            return true;
        }
    }

    public static final boolean equalStringsTables(final String[] s1, final String[] s2) {
        if (null == s1 || null == s2) {
            return false;
        }
        if (s1.length != s2.length) {
            return false;
        }
        for (int i = 0; i < s1.length; i++) {
            if (!s1[i].equals(s2[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static final boolean equalBooleanTables(final boolean[] b1, final boolean[] b2) {
        if (null == b1 || null == b2) {
            return false;
        }
        if (b1.length != b2.length) {
            return false;
        }
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsString(final String s1, final String s2) {
        if ((null == s1 && null != s2) || (null != s1 && null == s2)) {
            return false;
        }
        if ((null == s1 && null == s2) || s1.equals(s2)) {
            return true;
        } else {
            return false;
        }
    }

}
