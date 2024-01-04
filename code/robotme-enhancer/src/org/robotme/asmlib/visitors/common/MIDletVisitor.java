package org.robotme.asmlib.visitors.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.robotme.asmlib.visitors.BaseVisitor;

/**
 * Finds every constructors of subclasses of class <code>javax.microedition.midlet.MIDlet</code> (only one level of
 * subclassing is supported at the moment) and add statement:
 * 
 * <pre>
 * RobotMERecorder.getInstance().setMIDlet(this);
 * </pre>
 * 
 * as the last instruction in constructor.
 * 
 * @author Marcin Zduniak
 */
public abstract class MIDletVisitor extends BaseVisitor {

    private final static Logger logger = Logger.getLogger(MIDletVisitor.class.getName());

    // private String targetClassName;

    private String superClassName;

    /**
     * Chain constructor.
     */
    public MIDletVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv, cw);
    }

    /**
     * Start visiting a class.
     */
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        logger.log(Level.INFO, "Processing class: " + name);
        // this.targetClassName = name;
        this.superClassName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * Visit a method and check if we need to create a delegation stub.
     */
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        final MethodVisitor mw = super.visitMethod(access, name, desc, signature, exceptions);
        if (METHOD_NAME_CONSTRUCTOR.equals(name) && MIDDLET_CLASS_NAME.equals(superClassName)) {
            setProcessing(true);
            return new MethodAdapter(mw) {
                @Override
                public void visitInsn(int opcode) {
                    // if last statement in constructor:
                    if (Opcodes.RETURN == opcode) {
                        // oryginal source code:
                        // RobotMERecorder.getInstance().setMIDlet(this);
                        final String internalRobotMeClassName = Type.getInternalName(getInternalRobotMeClassName());
                        final String methodDescriptor = Type.getMethodDescriptor(Type
                                .getType(getInternalRobotMeClassName()), new Type[0]);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, internalRobotMeClassName, getFactoryMethodName(),
                                methodDescriptor);
                        mv.visitVarInsn(Opcodes.ALOAD, 0);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, internalRobotMeClassName, "setMIDlet",
                                "(Ljavax/microedition/midlet/MIDlet;)V");
                    }
                    super.visitInsn(opcode);
                }
            };
        } else {
            return mw;
        }
    }

}
