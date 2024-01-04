package org.robotme.xmlparser.utils;

import java.lang.reflect.Field;

/**
 * @author Marcin Zduniak
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static <T> T getFieldValue(String fieldName, String prefix, String sufix, Class fromClass, Class<T> returnClass)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (null == prefix) {
            prefix = "";
        }
        if (null == sufix) {
            sufix = "";
        }
        fieldName = prefix + fieldName.toUpperCase() + sufix;
        final Field field = fromClass.getField(fieldName);
        final Object fieldValue = field.get(null);
        return returnClass.cast(fieldValue);
    }

}
