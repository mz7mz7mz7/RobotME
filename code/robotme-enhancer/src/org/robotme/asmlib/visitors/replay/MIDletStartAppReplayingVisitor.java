package org.robotme.asmlib.visitors.replay;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.robotme.asmlib.visitors.BaseVisitor;
import org.robotme.asmlib.visitors.common.CommandListenerActionsInvokingInterceptorVisitor;
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class MIDletStartAppReplayingVisitor extends BaseVisitor {

    // TODO: switch to log4j implementation
    private final static Logger logger = Logger.getLogger(CommandListenerActionsInvokingInterceptorVisitor.class
            .getName());

    /**
     * Bytecode name of <code>javax.microedition.midlet.MIDlet</code>.
     */
    private final static String STARTAPP_METHOD_NAME = "startApp";

    private String superClassName;

    public MIDletStartAppReplayingVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv, cw);
    }

    /**
     * Start visiting a class.
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        logger.log(Level.INFO, "Processing class: " + name);
        // this.targetClassName = name;
        this.superClassName = superName;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    protected Class<? extends RobotMERecorder> getInternalRobotMeClassName() {
        return RobotMEReplaying.class;
    }
    
    @Override
    protected String getFactoryMethodName() {
        return "getReplayingInstance";
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        final MethodVisitor mw = super.visitMethod(access, name, desc, signature, exceptions);
        if (STARTAPP_METHOD_NAME.equals(name) && MIDDLET_CLASS_NAME.equals(superClassName)) {
            setProcessing(true);
            return new MethodAdapter(mw) {
                @Override
                public void visitInsn(int opcode) {
                    // if last statement in constructor:
                    if (Opcodes.RETURN == opcode) {
                        // Original source code:
                        // RobotMERecorder.getInstance().setMIDlet(this);
                        final String internalRobotMeClassName = Type.getInternalName(getInternalRobotMeClassName());
                        final String methodDescriptor = Type.getMethodDescriptor(Type
                                .getType(getInternalRobotMeClassName()), new Type[0]);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, internalRobotMeClassName, getFactoryMethodName(),
                                methodDescriptor);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, internalRobotMeClassName, "startReplaying", "()V");
                    }
                    super.visitInsn(opcode);
                }
            };
        } else {
            return mw;
        }
    }

}
