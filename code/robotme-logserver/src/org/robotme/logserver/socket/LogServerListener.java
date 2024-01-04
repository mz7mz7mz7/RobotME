package org.robotme.logserver.socket;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.robotme.core.log.LogEntriesMapper;
import org.robotme.core.log.entries.LogEntry;

/**
 * A socket server that lists on the given socket and dumps every incoming data to console.
 * @author   Dawid Weiss
 * @author   Adam Szarecki
 * @author   Marcin Zduniak
 * @uml.dependency   supplier="org.robotme.core.log.LogEntriesMapper"
 */
public class LogServerListener {

	public final static int portNr = 9999;

	/**
	 * @deprecated not used in current implementation
	 */
    @Deprecated
	private static class LogEntriesWriter extends InputStream {

		private final Writer out;

		private InputStream is;

		private LogEntriesWriter(String logFilePrefix, InputStream is)
				throws IOException {
			super();
			out = new FileWriter(logFilePrefix + ".rme");
			this.is = is;
		}

		@Override
		public int read() throws IOException {
			final int i = is.read();
			out.write(i);
			out.flush();
			return i;
		}
	}

	public static void println(Writer os, String arg) {
		System.out.print(arg);
		System.out.flush();
		try {
			os.write(arg);
			os.write("\n");
			os.flush();
		} catch (IOException e) {
			/* Ignore exceptions? */
			System.err.print("I/O exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		// open the socket.
		if (args.length != 1) {
			System.err
					.println("No argument - log store directory should be given");
			return;
		}
		if (new File(args[0]).isDirectory() == false) {
			System.err
					.println("No argument - log store directory does not exist:"
							+ args[0]);
			return;
		}

		final String dir = new File(args[0]).getPath() + File.separatorChar;

		final ServerSocket socket = new ServerSocket(portNr);
		System.out.println("Socket listening on port:" + portNr);
		int count = 0;
		while (true) {
			final Socket client = socket.accept();
			new Thread() {
				final String logFilePrefix = dir
						+ String.valueOf(System.currentTimeMillis());

				final PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter(logFilePrefix + ".txt")));

				public void run() {
					try {
						System.out.println("Logs at:" + dir);
						println(out, "\n\n---------------------------\n\n");
						final InputStream is = client.getInputStream();
						final DataInputStream dis = new DataInputStream(/*
								new LogEntriesWriter(logFilePrefix, */is/*)*/);
                        
                        final FileOutputStream fos = new FileOutputStream(logFilePrefix + ".rme");
                        
						while (true) {
							final int logEntryId = dis.readInt();
							System.out.println("Received logEntryId: "
									+ logEntryId);
							final LogEntry logEntry = LogEntriesMapper
									.createNewLogEntryInstance(logEntryId, dis);
                            fos.write(logEntry.toByteArray());
                            fos.flush();
							println(out, logEntry.toString());
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							client.close();
						} catch (IOException e1) {
							// Ignore
						}
					}
				}
			}.start();

			count++;
		}
	}
}
