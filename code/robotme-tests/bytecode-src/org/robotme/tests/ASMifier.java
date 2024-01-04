package org.robotme.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileUtils;
import org.junit.Assert;
import org.robotme.tests.utils.DiffPrint;

/**
 * @author Marcin Zduniak
 */
public class ASMifier {

    private static final Logger logger = Logger.getLogger(ASMifier.class);

    /**
     * A list of <code>FileSet</code> objects.
     */
    private final List<FileSet> filesets = new ArrayList<FileSet>();

    public ASMifier(boolean record, String bytecodeDir) {
        final String prefix = FileSetCreator.createPrefix(record);
        FileSet fileSet = FileSetCreator.cresteFileSet(record, bytecodeDir, "clazz-" + prefix);
        filesets.add(fileSet);
    }

    public void simulateAndTest() throws Exception {
        final FileUtils fileUtils = FileUtils.newFileUtils();
        final List<File> toProcess = new ArrayList<File>();
        final Project project = new Project();

        // Iterate through the filesets, remembering class files to process.
        for (final FileSet fs : filesets) {
            final DirectoryScanner ds = fs.getDirectoryScanner(project);
            final File fromDir = fs.getDir(project);
            final String[] srcFiles = ds.getIncludedFiles();

            for (final String srcFile : srcFiles) {
                final File file = fileUtils.resolveFile(fromDir, srcFile);
                toProcess.add(file);
            }
        }

        if (toProcess.size() == 0) {
            logger.warn("No class files selected.");
            return;
        }

        process((File[]) toProcess.toArray(new File[toProcess.size()]));
    }

    private void process(File[] files) throws Exception {
        for (File file : files) {
            final String canonicalPath = file.getCanonicalPath();
            final int dotLastIndex = canonicalPath.lastIndexOf('.');
            final String canonicalPathWithoutExtension = canonicalPath.substring(0, dotLastIndex);

            final String presentBytecodeFile = canonicalPath;
            final String presentJavaFile = canonicalPath + ".java";
            final String expectedJavaFile = canonicalPathWithoutExtension + ".java";

            final FileOutputStream fos = new FileOutputStream(presentJavaFile);

            final PrintStream ps = new PrintStream(fos);
            final PrintStream currPs = System.out;
            try {
                System.setOut(ps);
                org.objectweb.asm.util.ASMifierClassVisitor.main(new String[] { "-debug", presentBytecodeFile });
            } finally {
                System.setOut(currPs);
            }

            // diff present and expected java files
            final DiffPrint dp = new DiffPrint();
            final StringWriter sw = new StringWriter();
            dp.diff(presentJavaFile, expectedJavaFile, sw);
            final String diff = sw.getBuffer().toString();
            if (!"No differences".equals(diff)) {
                Assert.fail("Difference between files: '" + presentJavaFile + "' and '" + expectedJavaFile + "'\r\n"
                        + diff);
            }
        }
    }

    @Deprecated
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("c:/tmp/cos.java");

        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);

        org.objectweb.asm.util.ASMifierClassVisitor.main(new String[] { "-debug",
                "c:/WORK/RobotME/dev/trunk/code/robotme-tests/bytecode/replay/TestTextBoxMIDlet.clazz-replay" });
    }

}
