package com.robotme.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileUtils;

import com.robotme.asmlib.RobotizeClass;
import com.robotme.util.StreamUtils;

/**
 * An ANT task to process <code>.class</code> files for RobotME.
 * 
 * @author Dawid Weiss
 */
public class RobotizeTask extends Task {
    /**
     * A list of <code>FileSet</code> objects.
     */
    private ArrayList filesets = new ArrayList();
    
    /**
     * Target extension for generated class files.
     */
    private String targetExtension = ".clazz";

    /**
     * Adds a configured fileset pointing to compiled
     * class files. 
     */
    public void addConfigured(FileSet fileset) {
        this.filesets.add(fileset);
    }

    /**
     * Sets target extension for robotized files. By default
     * this extension is <code>.class</code> and overwrites
     * the source class.
     */
    public void setTargetExtension(final String extension) {
        if (extension.startsWith(".")) {
            this.targetExtension = extension;
        } else {
            this.targetExtension = "." + extension;
        }
    }

    /**
     * Execute the task.
     */
    public void execute() throws BuildException {
        validateArguments();

        final FileUtils fileUtils = FileUtils.newFileUtils();
        final ArrayList toProcess = new ArrayList();

        // Iterate through the filesets, remembering class files to process.
        for (Iterator fsIterator = filesets.iterator(); fsIterator.hasNext(); ) {
            final FileSet fs = (FileSet) fsIterator.next();
            final DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            final File fromDir = fs.getDir(getProject());
            final String [] srcFiles = ds.getIncludedFiles();

            for (int fIndex = 0; fIndex < srcFiles.length; fIndex++) {
                if (srcFiles[fIndex].endsWith(".class") == false) {
                    super.log("Unexpected class file extension: " + srcFiles[fIndex], Project.MSG_VERBOSE);
                }
                final File file = fileUtils.resolveFile(fromDir, srcFiles[fIndex]);
                toProcess.add(file);
            }
        }

        if (toProcess.size() == 0) {
            super.log("No class files selected.", Project.MSG_WARN);
            return;
        }

        try {
            process((File []) toProcess.toArray(new File[toProcess.size()]));
        } catch (IOException e) {
            throw new BuildException("I/O exception when processing files.", e);
        }
    }

    private void validateArguments() throws BuildException {
        if (filesets.size() == 0) {
            throw new BuildException("Nested fileset element pointing to classes is required.");
        }
        
        if (".".equals(targetExtension.trim())) {
            throw new BuildException("Target exception must not be empty.");
        }
    }
    
    /**
     * Processes a list of class {@link File}s.
     * 
     * @param files A list of {@link File} objects pointing to compiled classes.
     */
    private void process(File [] files) throws IOException {
        final RobotizeClass robbo = new RobotizeClass();
        int totalSaved = 0; 
        for (int i = 0; i < files.length; i++) {
            final File clazzFile = files[i];
            final byte [] result = robbo.process(StreamUtils.readFile(clazzFile));
            if (result == null) {
                // Nothing changed.
                continue;
            } else {
                // Save the new class file.
                final File parent = clazzFile.getParentFile();
                final String clazzName = clazzFile.getName();
                final File newFile = new File(parent, clazzName.substring(0, clazzName.lastIndexOf('.')) 
                        + targetExtension);
                StreamUtils.writeFile(newFile, result);
                totalSaved++;
            }
        }
        super.log("Robotized " + totalSaved + " file(s).", Project.MSG_INFO);
    }
}