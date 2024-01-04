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
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * Finds every occurrences of code like:
 * 
 * <pre>
 * displayable.setCommandListener(commandListener);
 * </pre>
 * 
 * and add instruction:
 * 
 * <pre>
 * RobotMEReplaying.getInstance().commandListenerSetOnDisplayable(commandListener, displayable);
 * </pre>
 * 
 * after it.
 * 
 * @author Marcin Zduniak
 */
public class SettingCommandListenerReplayingVisitor extends BaseVisitor {

    private final static Logger logger = Logger.getLogger(SettingCommandListenerReplayingVisitor.class.getName());

    protected int indexALOAD = 0;

    /**
     * Only last 2 ALOADs op codes should be saved.
     */
    protected int[] lastALOADs = new int[2];

    /**
     * @param cv
     * @param cw
     */
    public SettingCommandListenerReplayingVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv, cw);
    }

    /**
     * Start visiting a class.
     */
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        logger.log(Level.INFO, "Processing class: " + name);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * Visit a method and check if we need to create a delegation stub.
     */
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        final MethodVisitor mw = super.visitMethod(access, name, desc, signature, exceptions);
        setProcessing(true);
        return new MethodAdapter(mw) {

            @Override
            public void visitVarInsn(int opcode, int var) {
                super.visitVarInsn(opcode, var);
                if (Opcodes.ALOAD == opcode) {
                    lastALOADs[indexALOAD++] = var;
                    indexALOAD %= lastALOADs.length;
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                super.visitMethodInsn(opcode, owner, name, desc);
                // if matches to: mv.visitMethodInsn(INVOKEVIRTUAL,
                // "javax/microedition/lcdui/Displayable", "setCommandListener",
                // "(Ljavax/microedition/lcdui/CommandListener;)V");
                if (Opcodes.INVOKEVIRTUAL == opcode && DISPLAYABLE_CLASS_NAME.equals(owner)
                        && "setCommandListener".equals(name)
                        && "(Ljavax/microedition/lcdui/CommandListener;)V".equals(desc)) {
                    // add code:
                    // RobotMEReplaying.getInstance().commandListenerSetOnDisplayable(commandListener,
                    // displayable);
                    final String internalRobotMeClassName = Type.getInternalName(getInternalRobotMeClassName());
                    final String methodDescriptor = Type.getMethodDescriptor(Type
                            .getType(getInternalRobotMeClassName()), new Type[0]);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, internalRobotMeClassName, getFactoryMethodName(),
                            methodDescriptor);
                    visitLastVarInsn(mw);
                    visitLastVarInsn(mw);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(getInternalRobotMeClassName()),
                            "commandListenerSetOnDisplayable",
                            "(Ljavax/microedition/lcdui/CommandListener;Ljavax/microedition/lcdui/Displayable;)V");
                }
            }
        };
    }

    protected void visitLastVarInsn(MethodVisitor mw) {
        indexALOAD--;
        if (indexALOAD < 0) {
            indexALOAD = lastALOADs.length - 1;
        }
        mw.visitVarInsn(Opcodes.ALOAD, lastALOADs[indexALOAD]);
    }

    @Override
    protected Class<? extends RobotMERecorder> getInternalRobotMeClassName() {
        return RobotMEReplaying.class;
    }
    
    @Override
    protected String getFactoryMethodName() {
        return "getReplayingInstance";
    }

}
