package org.robotme.asmlib.visitors.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.robotme.asmlib.visitors.BaseVisitor;

/**
 * Finds every occurrences of code like:
 * 
 * <pre>
 * displayable.addCommand(COMMAND1);
 * </pre>
 * 
 * and add instruction:
 * 
 * <pre>
 * RobotMERecorder.getInstance().commandAddedToDisplayable(COMMAND1, displayable);
 * </pre>
 * 
 * after it.
 * 
 * @author Marcin Zduniak
 */
public abstract class CommandListenerAddingCommandVisitor extends BaseVisitor {

    private final static Logger logger = Logger.getLogger(CommandListenerAddingCommandVisitor.class.getName());

    private final GenericInstruction[] last2Instructions = new GenericInstruction[2];

    private int instructionIndex;

    private class GenericInstruction {
        private int opcode;

        private String arg1, arg2, arg3;

        private int arg1Int;

        public int getArg1Int() {
            return arg1Int;
        }

        public void setArg1Int(int arg1Int) {
            this.arg1Int = arg1Int;
        }

        public String getArg1() {
            return arg1;
        }

        public void setArg1(String arg1) {
            this.arg1 = arg1;
        }

        public String getArg2() {
            return arg2;
        }

        public void setArg2(String arg2) {
            this.arg2 = arg2;
        }

        public String getArg3() {
            return arg3;
        }

        public void setArg3(String arg3) {
            this.arg3 = arg3;
        }

        public int getOpcode() {
            return opcode;
        }

        public void setOpcode(int opcode) {
            this.opcode = opcode;
        }

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return new ToStringBuilder(this).append("opcode", this.opcode).append("arg1", this.arg1).append("arg2",
                    this.arg2).append("arg3", this.arg3).append("arg1Int", this.arg1Int).toString();
        }

    }

    /**
     * Chain constructor.
     */
    public CommandListenerAddingCommandVisitor(ClassVisitor cv, ClassWriter cw) {
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
     * Remeber last 2 instructions.
     */
    private void addGenericInstruction(GenericInstruction gi) {
        last2Instructions[instructionIndex] = gi;
        // logger.info(gi.toString());
        instructionIndex = (instructionIndex + 1) % last2Instructions.length;
    }

    private void visitLastInstruction(MethodVisitor mv) {
        instructionIndex--;
        if (instructionIndex < 0) {
            instructionIndex = last2Instructions.length - 1;
        }
        final GenericInstruction gi = last2Instructions[instructionIndex];
        last2Instructions[instructionIndex] = null;
        logger.info(gi.toString());
        if (Opcodes.GETSTATIC == gi.getOpcode()) {
            mv.visitFieldInsn(gi.getOpcode(), gi.getArg1(), gi.getArg2(), gi.getArg3());
        } else if (Opcodes.ALOAD == gi.getOpcode()) {
            mv.visitVarInsn(gi.getOpcode(), gi.getArg1Int());
        } else {
            final String msg = "Not understandable GenericInstruction's opcode.";
            logger.log(Level.SEVERE, msg);
            throw new RuntimeException(msg);
        }
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
                if (Opcodes.ALOAD == opcode) {
                    final GenericInstruction gi = new GenericInstruction();
                    gi.setOpcode(opcode);
                    gi.setArg1Int(var);
                    addGenericInstruction(gi);
                }
                super.visitVarInsn(opcode, var);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (Opcodes.GETSTATIC == opcode && ("Ljavax/microedition/lcdui/Command;".equals(desc)
                // || "Ljavax/microedition/lcdui/TextBox;".equals(desc)
                        )) {
                    final GenericInstruction gi = new GenericInstruction();
                    gi.setOpcode(opcode);
                    gi.setArg1(owner);
                    gi.setArg2(name);
                    gi.setArg3(desc);
                    addGenericInstruction(gi);
                }
                super.visitFieldInsn(opcode, owner, name, desc);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                super.visitMethodInsn(opcode, owner, name, desc);
                // if matches to: mv.visitMethodInsn(INVOKEVIRTUAL,
                // "javax/microedition/lcdui/Displayable", "addCommand",
                // "(Ljavax/microedition/lcdui/Command;)V");
                if (Opcodes.INVOKEVIRTUAL == opcode && DISPLAYABLE_CLASS_NAME.equals(owner)
                        && "addCommand".equals(name) && "(Ljavax/microedition/lcdui/Command;)V".equals(desc)) {
                    // add code:
                    // RobotMERecorder.getInstance().commandAddedToDisplayable(Command,
                    // Displayable);
                    final String internalRobotMeClassName = Type.getInternalName(getInternalRobotMeClassName());
                    final String methodDescriptor = Type.getMethodDescriptor(Type
                            .getType(getInternalRobotMeClassName()), new Type[0]);
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, internalRobotMeClassName, getFactoryMethodName(),
                            methodDescriptor);
                    visitLastInstruction(mv); // Displayable
                    visitLastInstruction(mv); // Command
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(getInternalRobotMeClassName()),
                            "commandAddedToDisplayable",
                            "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V");
                }
            }
        };
    }

}
