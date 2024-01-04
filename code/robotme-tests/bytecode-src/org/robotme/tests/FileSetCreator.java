package org.robotme.tests;

import java.io.File;

import org.apache.tools.ant.types.FileSet;

/**
 * @author Marcin Zduniak
 */
public class FileSetCreator {

    private FileSetCreator() {
        // Do not instantiate
    }
    
    public static String createPrefix(boolean record) {
        String prefix;
        if (record) {
            prefix = "record";
        } else {
            prefix = "replay";
        }
        return prefix;
    }
    
    
    public static FileSet cresteFileSet(boolean record, String bytecodeDir, String extension) {
        final String prefix = createPrefix(record);
        final FileSet fileSet = new FileSet();
        fileSet.setDir(new File(bytecodeDir));
        fileSet.setIncludes(prefix + "/**/*." + extension);
        fileSet.setIncludes("common/**/*." + extension);
        return fileSet;
    }

}
