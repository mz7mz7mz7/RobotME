package org.robotme.asmlib.visitors.record;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.robotme.asmlib.visitors.common.CanvasVisitor;
import org.robotme.core.recorder.RobotMERecorder;

/**
 * @author Marcin Zduniak
 */
public class CanvasRecordVisitor extends CanvasVisitor {

    /**
     * @param cv
     * @param cw
     */
    public CanvasRecordVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv, cw);
    }

    @Override
    protected Class<? extends RobotMERecorder> getInternalRobotMeClassName() {
        return RobotMERecorder.class;
    }

    @Override
    protected String getFactoryMethodName() {
        return "getRecorderInstance";
    }

}
