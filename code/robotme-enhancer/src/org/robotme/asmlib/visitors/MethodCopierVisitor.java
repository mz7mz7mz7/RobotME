package org.robotme.asmlib.visitors;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;

/**
 * A visitor which copies a method from one class to another
 * replacing virtual references of the owner class methods with the target class.
 * 
 * @author Dawid Weiss
 */
public class MethodCopierVisitor extends MethodAdapter {
    
    /**
     * Internal name of the class from which methods are to be copied (<code>/</code>-separated
     * elements).
     */
    private final String fromClass;
    
    /**
     * Internal name of the class to which methods are to be copied (<code>/</code>-separated
     * elements).
     */
    private final String toClass;

    public MethodCopierVisitor(MethodVisitor mv, String fromClass, String toClass) {
        super(mv);
        if (fromClass.indexOf('.') >= 0
                || toClass.indexOf('.') >= 0) {
            throw new IllegalArgumentException("Arguments are internal class names ('/'-separated).");
        }
        this.fromClass = fromClass;
        this.toClass = toClass;
    }

    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
        // TODO: We should probably translate fields as well.
    }

    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        if (fromClass.equals(owner)) {
            owner = toClass;
        }
        super.visitMethodInsn(opcode, owner, name, desc);
    }
}
