package org.robotme.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Various IO-related utilities.
 * 
 * @author Dawid Weiss
 */
public final class StreamUtils {
    
    private StreamUtils() {
        // No instances. Use static methods.
    }
    
    /**
     * Reads a file to a byte array.
     */
    public final static byte [] readFile(final File file) throws IOException {
        final byte [] content = new byte [(int) file.length()];
        final FileInputStream is = new FileInputStream(file);
        try {
            is.read(content);
            return content;
        } finally {
            is.close(); 
        }
    }

    /**
     * Saves a byte array to a file.
     */
    public static void writeFile(File file, byte[] content) throws IOException {
        final FileOutputStream os = new FileOutputStream(file);
        try {
            os.write(content);
        } finally {
            os.close(); 
        }
    }

    /**
     * Reads all available bytes from the input stream
     * and closes it at the end (even if an exception is thrown).
     */
    public static byte[] readAndCloseInput(InputStream is) throws IOException {
        // 8k buffer.
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final byte [] buffer = new byte[8 * 1024];
            int bytes;
            while ((bytes = is.read(buffer)) >= 0) {
                baos.write(buffer, 0, bytes);
            }
            return baos.toByteArray();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // Don't repropagate.
            }
        }
    }
}
