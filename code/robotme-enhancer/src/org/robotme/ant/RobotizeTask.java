package org.robotme.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileUtils;
import org.robotme.asmlib.RobotizeClass;
import org.robotme.util.StreamUtils;

/**
 * An ANT task to process <code>.class</code> files for RobotME.
 * 
 * @author Dawid Weiss
 * @author Marcin Zduniak
 */
public class RobotizeTask extends Task {
	
	/**
	 * If set to <code>true</code> enhancer will produce recording code, otherwise
	 * it will produce replaying code. 
	 */
	private boolean record;
	
    /**
     * A list of <code>FileSet</code> objects.
     */
    private List<FileSet> filesets = new ArrayList<FileSet>();
    
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
     * this extension is <code>.clazz</code> therefor do not overwrites
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
    @Override
    public void execute() throws BuildException {
        validateArguments();

        final FileUtils fileUtils = FileUtils.newFileUtils();
        final List<File> toProcess = new ArrayList<File>();

        // Iterate through the filesets, remembering class files to process.
        for (final FileSet fs : filesets) {
            final DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            final File fromDir = fs.getDir(getProject());
            final String[] srcFiles = ds.getIncludedFiles();

            for (String srcFile : srcFiles) {
                if (srcFile.endsWith(".class") == false) {
                    super.log("Unexpected class file extension: " + srcFile, Project.MSG_VERBOSE);
                }
                final File file = fileUtils.resolveFile(fromDir, srcFile);
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
            throw new BuildException("Target extension must not be empty.");
        }
    }
    
    public boolean isRecord() {
		return record;
	}

	public void setRecord(boolean record) {
		this.record = record;
	}

	/**
     * Processes a list of class {@link File}s.
     * 
     * @param files A list of {@link File} objects pointing to compiled classes.
     */
    private void process(File [] files) throws IOException {
        final RobotizeClass robbo = new RobotizeClass();
        robbo.setRecord(record);
        int totalSaved = 0; 
        for (final File clazzFile : files) {
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