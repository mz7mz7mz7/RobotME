package org.robotme.asmlib.visitors.replay;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.robotme.asmlib.visitors.common.CommandListenerAddingCommandVisitor;
import org.robotme.core.recorder.RobotMERecorder;
import org.robotme.core.replaying.RobotMEReplaying;

/**
 * @author Marcin Zduniak
 */
public class CommandListenerAddingCommandReplayingVisitor extends CommandListenerAddingCommandVisitor {

    public CommandListenerAddingCommandReplayingVisitor(ClassVisitor cv, ClassWriter cw) {
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
