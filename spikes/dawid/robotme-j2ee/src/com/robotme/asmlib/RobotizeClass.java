package com.robotme.asmlib;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;

/**
 * Performs all translations of the given bytecode required for
 * RobotME.
 * 
 * @author Dawid Weiss
 */
public class RobotizeClass {
    /**
     * Processes the bytecode of the given class.
     *
     * @return <code>null</code> if no changes have been made or new bytecode of the class.
     */
    public byte[] process(byte[] bytecode) {
        final ClassReader classReader = new ClassReader(bytecode);
        final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        
        // Chain all the visitors required for transformations.
        final ClassVisitor visitor = new CheckClassAdapter(classWriter);
        final CanvasVisitor cVisitor = new CanvasVisitor(visitor, classWriter);

        // Process the given class.
        classReader.accept(cVisitor, ClassReader.SKIP_DEBUG | ClassReader.EXPAND_FRAMES);

        if (cVisitor.isProcessing()) {
            return classWriter.toByteArray();
        } else {
            return null;
        }
    }
}
