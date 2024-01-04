package com.robotme.asmlib;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;

import com.robotme.util.StreamUtils;


/**
 * Base superclass for robotizing visitors.
 * 
 * @author Dawid Weiss
 */
abstract class BaseVisitor extends ClassAdapter {
    /**
     * Fully qualified name of the marker interface.
     */
    protected final static String IROBOTIZED_INTERFACE_NAME 
        = "com.robotme.asmlib.delegates.IRobotized";

    /**
     * Internal name of the {@link #IROBOTIZED_INTERFACE_NAME}.
     */
    protected final static String IROBOTIZED_INTERFACE_INT_NAME
        = IROBOTIZED_INTERFACE_NAME.replace('.', '/');

    protected final String METHOD_NAME_CONSTRUCTOR = "<init>";
    protected final String METHOD_NAME_CLASS_INIT = "<clinit>";

    protected BaseVisitor(ClassVisitor cv) {
        super(cv);
    }

    /**
     * Returns bytecode of a given class.
     */
    protected static byte[] getClassBytecode(String className) throws IOException {
        final String resourceName = className.replace('.', '/') + ".class";

        final ClassLoader cl = BaseVisitor.class.getClassLoader();
        InputStream is = null;
        // Try this class's class loader first.
        is = cl.getResourceAsStream(resourceName);
        if (is == null) {
            // Try the system class loader then.
            is = ClassLoader.getSystemResourceAsStream(resourceName);
            if (is == null) {
                throw new IOException("Cannot find the required resource: "
                        + resourceName);
            }
        }

        return StreamUtils.readAndCloseInput(is);
    }
}
