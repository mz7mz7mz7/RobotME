package org.robotme.asmlib.visitors;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.util.StreamUtils;

/**
 * Base superclass for robotizing visitors.
 * 
 * @author Dawid Weiss
 * @author Marcin Zduniak
 */
public abstract class BaseVisitor extends ClassAdapter {

    /**
     * Fully qualified name of the marker interface.
     */
    // protected final static String IROBOTIZED_INTERFACE_NAME
    // = "org.robotme.asmlib.delegates.IRobotized";
    /**
     * Internal name of the {@link #IROBOTIZED_INTERFACE_NAME}.
     */
    // protected final static String IROBOTIZED_INTERFACE_INT_NAME
    // = IROBOTIZED_INTERFACE_NAME.replace('.', '/');
    protected final String METHOD_NAME_CONSTRUCTOR = "<init>";

    protected final String METHOD_NAME_CLASS_INIT = "<clinit>";

    protected final static String DISPLAYABLE_CLASS_NAME = "javax/microedition/lcdui/Displayable";

    protected final static String DISPLAY_CLASS_NAME = "javax/microedition/lcdui/Display";

    protected final static String COMMANDLISTENER_INTERFACE_NAME = "javax/microedition/lcdui/CommandListener";

    protected final static String MIDDLET_CLASS_NAME = "javax/microedition/midlet/MIDlet";

    /**
     * The writer to which delegate stubs are added.
     */
    protected final ClassWriter cw;

    /**
     * A private flag telling visitor methods if the current class is being processed or not.
     */
    private boolean processing;

    // protected BaseVisitor(ClassVisitor cv) {
    // super(cv);
    // }

    protected BaseVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv);
        this.cw = cw;
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
                throw new IOException("Cannot find the required resource: " + resourceName);
            }
        }

        return StreamUtils.readAndCloseInput(is);
    }

    /**
     * Returns <code>true</code> if this visitor processed the class somehow. The result of this method is valid only
     * after the processing is finished (the visitor returned from {@link #visitEnd()}.
     */
    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    protected abstract Class<? extends RobotMERecorder> getInternalRobotMeClassName();

    protected abstract String getFactoryMethodName();
}
