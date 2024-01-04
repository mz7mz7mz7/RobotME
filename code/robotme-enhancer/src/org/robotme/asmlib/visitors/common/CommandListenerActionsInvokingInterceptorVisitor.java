package org.robotme.asmlib.visitors.common;

import java.util.Arrays;
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
 * Check if processing class implements interface <code>javax.microedition.lcdui.CommandListener</code> and if true
 * add statement:
 * 
 * <pre>
 * RobotMERecorder.getInstance().commandIvoked(command, displayable);
 * </pre>
 * 
 * at the beginning of implmentation of
 * javax.microedition.lcdui.CommandListener#commandAction(Command, Displayable) method.
 * 
 * @author Marcin Zduniak
 */
public abstract class CommandListenerActionsInvokingInterceptorVisitor extends BaseVisitor {

    private final static Logger logger = Logger.getLogger(CommandListenerActionsInvokingInterceptorVisitor.class
            .getName());

    private boolean implementsCommandListener = false;

    /**
     * Chain constructor.
     */
    public CommandListenerActionsInvokingInterceptorVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv, cw);
    }

    /**
     * Start visiting a class.
     */
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        logger.log(Level.INFO, "Processing class: " + name);
        if (Arrays.asList(interfaces).contains(COMMANDLISTENER_INTERFACE_NAME)) {
            implementsCommandListener = true;
        }
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /**
     * Visit a method and check if we need to create a delegation stub.
     */
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        final MethodVisitor mw = super.visitMethod(access, name, desc, signature, exceptions);
        if (implementsCommandListener && "commandAction".equals(name)
                && "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V".equals(desc)) {
            setProcessing(true);
            return new MethodAdapter(mw) {

                @Override
                public void visitCode() {
                    super.visitCode();
                    final String methodDescriptor = Type.getMethodDescriptor(Type
                            .getType(getInternalRobotMeClassName()), new Type[0]);

                    super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(getInternalRobotMeClassName()),
                            getFactoryMethodName(), methodDescriptor);

                    super.visitVarInsn(Opcodes.ALOAD, 1);
                    super.visitVarInsn(Opcodes.ALOAD, 2);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(getInternalRobotMeClassName()),
                            "commandIvoked",
                            "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V");
                }

                // @Override
                // public void visitVarInsn(int opcode, int var) {
                // super.visitVarInsn(opcode, var);
                // }

                // @Override
                // public void visitFieldInsn(int opcode, String owner,
                // String name, String desc) {
                // super.visitFieldInsn(opcode, owner, name, desc);
                // }

                // @Override
                // public void visitMethodInsn(int opcode, String owner,
                // String name, String desc) {
                // super.visitMethodInsn(opcode, owner, name, desc);
                // }
            };
        } else {
            return mw;
        }
    }

}
