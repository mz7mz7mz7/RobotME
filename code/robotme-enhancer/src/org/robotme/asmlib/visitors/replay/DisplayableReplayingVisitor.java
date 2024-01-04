package org.robotme.asmlib.visitors.replay;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.robotme.asmlib.visitors.common.DisplayableVisitor;
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class DisplayableReplayingVisitor extends DisplayableVisitor {

    public DisplayableReplayingVisitor(ClassVisitor cv, ClassWriter cw) {
        super(cv, cw);
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
