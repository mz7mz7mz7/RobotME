package org.robotme.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.util.ClassLoaderRepository;
import org.apache.bcel.verifier.VerificationResult;
import org.apache.bcel.verifier.Verifier;
import org.apache.bcel.verifier.VerifierFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.util.FileUtils;

/**
 * This task is not used at the moment, but could be probably used in the future.
 * 
 * @author Dawid Weiss
 * @author Marcin Zduniak
 */
@Deprecated
public class JustIceVerifierTask extends Task {

    private class MyRepository implements org.apache.bcel.util.Repository {

        private static final long serialVersionUID = 7215163852398422828L;

        private final Map<String, String> classes;

        private final ClassLoaderRepository classLoaderRepo = new ClassLoaderRepository(
                getClass().getClassLoader());

        public MyRepository(Map<String, String> classes) {
            this.classes = classes;
        }

        public void storeClass(JavaClass jc) {
            // Do notghing
        }

        public void removeClass(JavaClass jc) {
            // Do notghing
        }

        public JavaClass findClass(String s) {
            try {
                return loadClass(s);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }

        public JavaClass loadClass(String s) throws ClassNotFoundException {
            System.out.println("LOADING: " + s);
            if (classes.containsKey(s)) {
                try {
                    final JavaClass clazz = new ClassParser(classes.get(s))
                            .parse();
                    System.out.println("RETURNING: " + clazz);
                    return clazz;
                } catch (ClassFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("RETURNING2");
//            final JavaClass clazz = classLoaderRepo.loadClass(s);
//            return clazz;
            return null;
        }

        public JavaClass loadClass(Class c) throws ClassNotFoundException {
            return loadClass(c.getName());
        }

        public void clear() {
            // Do notghing
        }

    }

    /**
     * A list of <code>FileSet</code> objects.
     */
    private List<FileSet> filesets = new ArrayList<FileSet>();

    private String logFile;

    private PrintWriter out;

    /**
     * Adds a configured fileset pointing to compiled class files.
     */
    public void addConfigured(FileSet fileset) {
        this.filesets.add(fileset);
    }

    public void setLogFile(final String logFile) {
        this.logFile = logFile;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    private String convertToClassName(String pathToClass) {
        String str = pathToClass.replace('\\', '.').replace('/', '.');
        if (str.endsWith(".class")) {
            str = str.substring(0, str.length() - 6);
        }
        return str;
    }

    /**
     * Execute the task.
     */
    public void execute() throws BuildException {
        validateArguments();

        final FileUtils fileUtils = FileUtils.newFileUtils();
        final Map<String, String> toProcess = new TreeMap<String, String>();

        // Iterate through the filesets, remembering class files to process.
        for (final FileSet fs : filesets) {
            final DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            final File fromDir = fs.getDir(getProject());
            final String[] srcFiles = ds.getIncludedFiles();

            for (String srcFile : srcFiles) {
                if (srcFile.endsWith(".class") == false) {
                    super.log("Unexpected class file extension: " + srcFile,
                            Project.MSG_VERBOSE);
                }
                final File file = fileUtils.resolveFile(fromDir, srcFile);
                final String clazz = convertToClassName(srcFile);
                toProcess.put(clazz, file.getAbsolutePath());
            }
        }

        if (toProcess.size() == 0) {
            super.log("No class files selected.", Project.MSG_WARN);
            return;
        }

        try {
            process(toProcess);
        } catch (IOException e) {
            throw new BuildException("I/O exception when processing files.", e);
        }
    }

    private void validateArguments() throws BuildException {
        if (filesets.size() == 0) {
            throw new BuildException(
                    "Nested fileset element pointing to classes is required.");
        }

        if (null == logFile) {
            throw new BuildException("'logFile' atribute is required.");
        }
    }

    private void println(String str) {
        super.log(str, Project.MSG_INFO);
        out.write(str);
        out.flush();
    }

    /**
     * Processes a list of classes.
     */
    private void process(Map<String, String> classes) throws IOException {
        int totalVerified = 0;
        int correctlyVerified = 0;
        println("JustIce by Enver Haase, (C) 2001-2002.\n<http://bcel.sourceforge.net>\n<http://jakarta.apache.org/bcel>\n");
//         final ClassLoaderRepository classLoaderRepo = new
//         ClassLoaderRepository(
//         getClass().getClassLoader());
//         Repository.setRepository(classLoaderRepo);
        Repository.setRepository(new MyRepository(classes));
        for (final String clazz : classes.keySet()) {
            boolean correct = true;
            // implementation based on oryginal {@link
            // org.apache.bcel.verifier.Verifier} class:
            final Verifier v = VerifierFactory.getVerifier(clazz);
            VerificationResult vr = v.doPass1();
            if (VerificationResult.VR_OK != vr) {
                correct = false;
            }
            println("Pass 1:\n" + vr);
            vr = v.doPass2();
            println("Pass 2:\n" + vr);
            if (vr == VerificationResult.VR_OK) {
                final JavaClass jc = Repository.lookupClass(clazz);
                for (int i = 0; i < jc.getMethods().length; i++) {
                    vr = v.doPass3a(i);
                    if (VerificationResult.VR_OK != vr) {
                        correct = false;
                    }
                    println("Pass 3a, method number " + i + " ['"
                            + jc.getMethods()[i] + "']:\n" + vr);
                    vr = v.doPass3b(i);
                    if (VerificationResult.VR_OK != vr) {
                        correct = false;
                    }
                    println("Pass 3b, method number " + i + " ['"
                            + jc.getMethods()[i] + "']:\n" + vr);
                }
            } else {
                correct = false;
            }
            println("Warnings:");
            final String warnings[] = v.getMessages();
            if (warnings.length == 0) {
                println("<none>");
            }
            for (int j = 0; j < warnings.length; j++) {
                println(warnings[j]);
            }

            println("\n\n");
            v.flush();
            Repository.clearCache();
            System.gc();
            // end of implementation based on oryginal BCEL {@link Verifier}
            // class.

            if (correct) {
                correctlyVerified++;
            }
            totalVerified++;
        }
        super.log("Verifiede " + totalVerified + " file(s). Correct file(s): "
                + correctlyVerified, Project.MSG_INFO);
    }
}