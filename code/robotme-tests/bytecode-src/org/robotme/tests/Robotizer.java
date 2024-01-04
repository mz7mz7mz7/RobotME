package org.robotme.tests;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.robotme.ant.RobotizeTask;

/**
 * @author Marcin Zduniak
 */
public class Robotizer {

    private String bytecodeDir;
    
    public void simulateReplaying() {
        simulate(false);
    }

    public void simulateRecording() {
        simulate(true);
    }

    private void simulate(boolean record) {
        final RobotizeTask robotize = new RobotizeTask();
        
        robotize.setProject(new Project());

        FileSet fileSet = FileSetCreator.cresteFileSet(record, bytecodeDir, "class");
        final String prefix = FileSetCreator.createPrefix(record);
        robotize.addConfigured(fileSet);
        robotize.setRecord(record);
        robotize.setTargetExtension(".clazz-" + prefix);
        robotize.execute();
    }

    /**
     * @param bytecodeDir
     *            the bytecodeDir to set
     */
    public void setBytecodeDir(String bytecodeDir) {
        this.bytecodeDir = bytecodeDir;
    }

}
