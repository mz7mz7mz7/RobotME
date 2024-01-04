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
 * 
 * Finds every occurrences of code like:
 * 
 * <pre>
 * Display.getDisplay(this).setCurrent(displayable);
 * </pre>
 * 
 * and changes it into:
 * 
 * <pre>
 * Display.getDisplay(this).setCurrent(displayable);
 * RobotMERecorder.getInstance().setCurrentDisplayable(displayable);
 * </pre>
 * 
 * @author Marcin Zduniak
 */
public abstract class DisplayableVisitor extends BaseVisitor {

    private final static Logger logger = Logger.getLogger(DisplayableVisitor.class.getName());

    // private String targetClassName;

    // private String superClassName;

    /**
     * Chain constructor.
     */
    public DisplayableVisitor(ClassVisitor cv, ClassWriter cw) {
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
        // REVIEW: taka strategia zawiedzie gdy ktos w polu lokalnym w klasie
        // bedzie przechowywal
        // referencje do obiektu Display
        return new MethodAdapter(mw) {

            private int lastVarInVisitVarInsnWithALOADOpcode;

            @Override
            public void visitVarInsn(int opcode, int var) {
                if (Opcodes.ALOAD == opcode) {
                    lastVarInVisitVarInsnWithALOADOpcode = var;
                }
                super.visitVarInsn(opcode, var);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                super.visitMethodInsn(opcode, owner, name, desc);
                // if matches to: mv.visitMethodInsn(INVOKEVIRTUAL,
                // "javax/microedition/lcdui/Display", "setCurrent",
                // "(Ljavax/microedition/lcdui/Displayable;)V");
                if (Opcodes.INVOKEVIRTUAL == opcode && DISPLAY_CLASS_NAME.equals(owner) && "setCurrent".equals(name)
                        && "(Ljavax/microedition/lcdui/Displayable;)V".equals(desc)) {
                    // add code:
                    // RobotMERecorder.getInstance().setCurrentDisplayable(displayableObject);
                    final String methodDescriptor = Type.getMethodDescriptor(Type
                            .getType(getInternalRobotMeClassName()), new Type[0]);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(getInternalRobotMeClassName()),
                            getFactoryMethodName(), methodDescriptor);
                    super.visitVarInsn(Opcodes.ALOAD, lastVarInVisitVarInsnWithALOADOpcode);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(getInternalRobotMeClassName()),
                            "setCurrentDisplayable", "(Ljavax/microedition/lcdui/Displayable;)V");
                }
            }

        };
    }

}
