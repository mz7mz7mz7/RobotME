package com.robotme.asmlib;

import java.awt.Canvas;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * A {@link ClassAdapter} for rewriting classes extending 
 * {@link Canvas} class.
 * 
 * The target application code is dynamically rewritten according
 * to the following contract:
 * <ol>
 *  <li>all classes directly extending {@link Canvas} are processed,</li>
 *  <li>matching classes are rewritten to implement 
 *  <code>com.robotme.asmlib.delegates.IRobotized</code> interface,
 *  and <code>com.robotme.asmlib.delegates.IRobotizedCanvas</code> interface,
 *  </li>
 *  <li>matching classes are made final,</li>
 *  <li>all methods from the template <code>RobotizedCanvas</code> not starting with <code>robot$</code>
 *  are copied directly to the matching class. If there is a method with the same exact signature,
 *  it is rewritten to a signature matching <code>robot$<i>methodName</i></code>. 
 * </ol>
 * 
 * @author Dawid Weiss
 */
public class CanvasVisitor extends BaseVisitor {
    private final static Logger logger = Logger.getLogger(CanvasVisitor.class.getName());

    /**
     * Bytecode name of <code>javax.microedition.lcdui.Canvas</code>. 
     */
    private final static String CANVAS_CLASS_NAME = "javax/microedition/lcdui/Canvas";

    /**
     * Template implementation of {@link Canvas}.
     */
    private static final String ROBOTIZED_CANVAS_CLASS_NAME = "com.robotme.asmlib.delegates.RobotizedCanvas";
    private static final String ROBOTIZED_CANVAS_CLASS_INT_NAME = ROBOTIZED_CANVAS_CLASS_NAME.replace('.', '/');
    private static final String IROBOTIZED_CANVAS_INTERFACE_NAME = "com.robotme.asmlib.delegates.IRobotizedCanvas";
    private static final String IROBOTIZED_CANVAS_INTERFACE_INT_NAME = IROBOTIZED_CANVAS_INTERFACE_NAME.replace('.', '/');

    /**
     * A cached copy of the <code>RobotizedCanvas</code> template class. We need
     * it to extract method templates.
     */
    private static final byte [] robotizedCanvasClass;

    static {
        try {
            robotizedCanvasClass = getClassBytecode(ROBOTIZED_CANVAS_CLASS_NAME);
        } catch (IOException e) {
            throw new RuntimeException("Cannot find the required resource: "
                    + ROBOTIZED_CANVAS_CLASS_NAME);
        }
    }

    /**
     * The writer to which delegate stubs are added.
     */
    private final ClassWriter cw;

    /**
     * A private flag telling visitor methods if the current class is being
     * processed or not.
     */
    private boolean processing;

    /**
     * Template methods to be copied into the processed class.
     */
    private final HashMap templateMethods;

    /**
     * Fully qualified target class name (internal format).
     */
    private String targetClassName;

    /**
     * Chain constructor.
     */
    public CanvasVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv);
        this.cw = cw;
        this.templateMethods = readTemplateMethods();
    }

    /**
     * Start visiting a class.
     */
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        // Check if this class implements robotized interface.
        // If so, it has been processed already -- return immediately.
        final boolean alreadyProcessed = Arrays.asList(interfaces).contains(IROBOTIZED_INTERFACE_INT_NAME);
        if (alreadyProcessed) {
            logger.log(Level.FINE, "Class already robotized: " + name);
        }

        // TODO: This will only work for classes extending directly
        // from Canvas. If we created an abstract subclass of Canvas and then
        // extended it with yet another concrete class it'd probably require
        // a different detection mechanism.
        if (!alreadyProcessed && CANVAS_CLASS_NAME.equals(superName)) {
            logger.log(Level.INFO, "Processing class: " + name);
            this.processing = true;
            this.targetClassName = name;

            // We mark the class so that we know
            // it has been processed by RobotME. Something the obfuscator won't
            // remove -- we implement a marker interface.
            final String [] extInterfaces = new String[interfaces.length + 2];
            System.arraycopy(interfaces, 0, extInterfaces, 0, interfaces.length);
            extInterfaces[interfaces.length] = IROBOTIZED_INTERFACE_INT_NAME;
            extInterfaces[interfaces.length + 1] = IROBOTIZED_CANVAS_INTERFACE_INT_NAME;
            interfaces = extInterfaces;

            // Check if we have a final class. If not, make it final so that no other
            // class may extend it.

            // TODO: This needs to be thought over. What about subclasses of classes
            // extending Canvas? Do we need to robotize them as well? This flag at the
            // moment also conflicts with ACC_ABSTRACT.
            if ((access & Opcodes.ACC_FINAL) == 0) {
                access = access | Opcodes.ACC_FINAL;
            }
            super.visit(version, access, name, signature, superName, interfaces);
        } else {
            // Skip this class.
            this.processing = false;
            super.visit(version, access, name, signature, superName, interfaces);
        }
    }

    /**
     * Visit a method and check if we need to create a delegation stub.
     */
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (!processing) {
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

        if (METHOD_NAME_CONSTRUCTOR.equals(name)) {
            // TODO: Constructors must invoke superclass constructor first. But then,
            // after that, we can call our own method.
            return super.visitMethod(access, name, desc, signature, exceptions);
        } else if (METHOD_NAME_CLASS_INIT.equals(name)) {
            // Class initialization blocks are safe -- we can simply
            // rewrite them
            return super.visitMethod(access, name, desc, signature, exceptions);
        } else {
            final String methodKey = createMethodKey(name, desc);
            logger.log(Level.FINE, "Rewriting method: " + name + desc);
            if (this.templateMethods.containsKey(methodKey)) {
                // Such method exists in the template. Rewrite the name
                // of this method, make it final
                name = "robot$" + name;
                access |= Opcodes.ACC_FINAL;
                // and remove a potential template delegation stub.
                templateMethods.remove(createMethodKey(name, desc));
            } else {
                // Non-template methods simply go unchanged.
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    /**
     * End visiting the class.
     */
    public void visitEnd() {
        if (!processing) {
            super.visitEnd();
            return;
        }

        // Adding all delegation stubs and template methods.
        for (final MethodNode methodNode : (Collection<MethodNode>) templateMethods.values()) {
            final List exceptionList = methodNode.exceptions;
            final String [] exceptions = (String []) exceptionList.toArray(new String[exceptionList.size()]);
            final MethodVisitor methodVisitor = cw.visitMethod(
                    methodNode.access, methodNode.name, methodNode.desc,
                    methodNode.signature, exceptions);
            final MethodCopierVisitor copier = new MethodCopierVisitor(methodVisitor,
                    ROBOTIZED_CANVAS_CLASS_INT_NAME, targetClassName);
            methodNode.accept(copier);
        }

        super.visitEnd();
    }

    /**
     * Returns <code>true</code> if this visitor processed the class somehow.
     * The result of this method is valid only after the processing is finished
     * (the visitor returned from {@link #visitEnd()}.
     */
    public boolean isProcessing() {
        return processing;
    }
    
    /**
     * Reads signatures of template methods into a hashmap.
     */
    private HashMap readTemplateMethods() {
        final ClassReader cr = new ClassReader(robotizedCanvasClass);
        final ClassNode cn = new ClassNode();
        cr.accept(cn, ClassReader.SKIP_DEBUG);

        final HashMap methods = new HashMap();
        for (final MethodNode methodNode : (List<MethodNode>) cn.methods) {
            // Skip constructors.
            if (METHOD_NAME_CONSTRUCTOR.equals(methodNode.name)) {
                continue;
            }
            // REVIEW: is it correct? below and above test are identical
            if (METHOD_NAME_CONSTRUCTOR.equals(methodNode.name)) {
                // TODO: Should we consider this as an option? When would we actually need it?
                throw new RuntimeException("Class initialization blocks in templates unsupported.");
            }
            // Other methods go into a hashmap with signature keys.
            final String signature = 
                createMethodKey(methodNode.name, methodNode.desc);
            methods.put(signature, methodNode);
        }

        return methods;
    }

    /**
     * Creates unique key for maps (concatenation of name and parameters signature).
     * 
     * TODO: Should we include access scope? We should check the JVM spec what makes a method
     * unique (name + formal parameter types I think). 
     */
    private String createMethodKey(String name, String signature) {
        return name + "@" + signature;
    }
}
