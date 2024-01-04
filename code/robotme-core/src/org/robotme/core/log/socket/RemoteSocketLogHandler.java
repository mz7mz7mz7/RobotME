package org.robotme.core.log.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.OutputConnection;

import org.robotme.core.log.LogHandler;
import org.robotme.core.log.entries.LogEntry;

/**
 * @author Dawid Weiss
 * @author Marcin Zduniak
 */
public class RemoteSocketLogHandler extends LogHandler implements Runnable {

    private OutputConnection connection;

    private OutputStream os;

    /**
     * Output buffer size (in {@link LogEntry}'ies numbers). If set to 0 -
     * buffering is disabled.
     */
    private static short outputBufferSize = 10;

    private final LogEntry[] logEntries;

    private int currentIndex;

    private int slotsTaken;

    /**
     * Creates remote socket log, <b>with background thread</b> which sends
     * data. Data are send only if buffer is full or flush is called from
     * outside.
     * 
     * @param host
     * @param port
     * @param noAutoSend
     */
    public RemoteSocketLogHandler(String host, int port, short bufferSize) {
        currentIndex = 0;
        slotsTaken = 0;
        outputBufferSize = bufferSize;
        logEntries = new LogEntry[bufferSize];
        openRemoteSocket(host, port);

        new Thread(this).start();
    }

    /**
     * Creates remote socket log, <b>with background thread</b> which sends
     * data. Data are send only if buffer is full or flush is called from
     * outside.
     * 
     * @param host
     * @param port
     */
    public RemoteSocketLogHandler(String host, int port) {
        this(host, port, outputBufferSize);
    }

    /**
     * Attempts to open the remote socket and write data to it.
     */
    private void openRemoteSocket(String host, int port) {
        final String SOCKET_ADDRESS = "socket://" + host + ":" + port;
        try {
            connection = (OutputConnection) Connector.open(SOCKET_ADDRESS);
            if (connection == null) {
                throw new RuntimeException("Could not open socket connection.");
            }
            os = connection.openOutputStream();
        } catch (Exception e) {
            final String message = "Could not initialize socket logger.";
            throw new RuntimeException(message);
        }
    }

    /**
     * Adds a new entry to the currently open socket logger.
     */
    private synchronized void addLogEntry(LogEntry logEntry) {
        if (outputBufferSize == 0) {
            final byte[] data;
            try {
                data = logEntry.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            sendData(data);
        } else {
            if (slotsTaken == outputBufferSize) {
                flush();
            }
            enqueueLogEntry(logEntry);
        }
    }

    private synchronized void enqueueLogEntry(LogEntry logEntry) {
        logEntries[currentIndex] = logEntry;
        currentIndex = (currentIndex + 1) % outputBufferSize;
        slotsTaken++;
        System.out.println("currentIndex: " + currentIndex + ", slotsTaken: " + slotsTaken);
    }

    public synchronized void flush() {
        int index = currentIndex - slotsTaken;
        if (index < 0) {
            index = outputBufferSize + index;
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < slotsTaken; i++) {
            System.out.println("FLUSH i: " + i + ", index: " + index);
            try {
                System.out.println("LE: " + logEntries[index]);
                baos.write(logEntries[index].toByteArray());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            index = (index + 1) % outputBufferSize;;
        }

        sendData(baos.toByteArray());
        slotsTaken = 0;
    }

    private synchronized void sendData(byte[] data) {
        try {
            os.write(data);
            os.flush();
        } catch (IOException e) {
            // not good, throw unchecked exception.
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * A {@link Runnable} implementation for periodically flushing the stream.
     */
    public void run() {
        while (true) {
            try {
                flush();
                // 10 second:
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                /* Return on interrupted exceptions. */
                return;
            }
        }
    }

    public void log(LogEntry logEntry) {
        System.out.println("logEntry: " + logEntry);
        System.out.println("LOGENTRY Class: " + logEntry.getClass().getName());
        System.out.println("*** Time: " + logEntry.getTimestamp());
        System.out.println(logEntry);
        addLogEntry(logEntry);
    }

    public void shutdown() {
        super.shutdown();
        if (os != null) {
            try {
                flush();
                os.flush();
                os.close();
            } catch (Exception e) {
                // Ignore
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

}
