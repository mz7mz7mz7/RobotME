package org.robotme.asmlib;

import java.util.logging.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.CheckClassAdapter;
import org.robotme.asmlib.visitors.BaseVisitor;
import org.robotme.asmlib.visitors.record.CanvasRecordVisitor;
import org.robotme.asmlib.visitors.record.CommandListenerActionsInvokingInterceptorRecordVisitor;
import org.robotme.asmlib.visitors.record.CommandListenerAddingCommandRecorderVisitor;
import org.robotme.asmlib.visitors.record.DisplayableRecorderVisitor;
import org.robotme.asmlib.visitors.record.MIDletRecorderVisitor;
import org.robotme.asmlib.visitors.replay.CanvasReplayingVisitor;
import org.robotme.asmlib.visitors.replay.CommandListenerActionsInvokingInterceptorReplayingVisitor;
import org.robotme.asmlib.visitors.replay.CommandListenerAddingCommandReplayingVisitor;
import org.robotme.asmlib.visitors.replay.DisplayableReplayingVisitor;
import org.robotme.asmlib.visitors.replay.MIDletReaplyingVisitor;
import org.robotme.asmlib.visitors.replay.MIDletStartAppReplayingVisitor;
import org.robotme.asmlib.visitors.replay.SettingCommandListenerReplayingVisitor;

/**
 * Performs all translations of the given bytecode required for RobotME.
 * 
 * @author Dawid Weiss
 * @author Marcin Zduniak
 */
public class RobotizeClass {

	private final static Logger logger = Logger.getLogger(RobotizeClass.class
			.getName());

	/**
	 * If set to <code>true</code> enhancer will produce recording code,
	 * otherwise it will produce replaying code.
	 */
	private boolean record;

	@SuppressWarnings("unchecked")
	private Class<BaseVisitor>[] recordVisitors = new Class[] {
			MIDletRecorderVisitor.class, DisplayableRecorderVisitor.class,
			CommandListenerAddingCommandRecorderVisitor.class,
			CommandListenerActionsInvokingInterceptorRecordVisitor.class,
            CanvasRecordVisitor.class};

	@SuppressWarnings("unchecked")
	private Class<BaseVisitor>[] replayVisitors = new Class[] {
			MIDletReaplyingVisitor.class, DisplayableReplayingVisitor.class,
			CommandListenerAddingCommandReplayingVisitor.class,
			MIDletStartAppReplayingVisitor.class,
			CommandListenerActionsInvokingInterceptorReplayingVisitor.class,
			SettingCommandListenerReplayingVisitor.class,
            CanvasReplayingVisitor.class};

	public byte[] process(final byte[] bytecode) {
		byte[] processedBytecode = bytecode;
		boolean processed = false;
		final Class<BaseVisitor>[] visitors = record ? recordVisitors
				: replayVisitors;
		for (Class<BaseVisitor> visitorClass : visitors) {
			final byte[] returnedBytecode = process(processedBytecode,
					visitorClass);
			if (null != returnedBytecode) {
				processed = true;
				processedBytecode = returnedBytecode;
			}
		}
		if (processed) {
			return processedBytecode;
		} else {
			return null;
		}
	}

	/**
	 * Processes the bytecode of the given class.
	 * 
	 * @return <code>null</code> if no changes have been made or new bytecode
	 *         of the class.
	 */
	private byte[] process(byte[] bytecode, Class<BaseVisitor> visitorClass) {
		final ClassReader classReader = new ClassReader(bytecode);
		final ClassWriter classWriter = new ClassWriter(
				ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

		// Chain all the visitors required for transformations.
		final ClassVisitor visitor = new CheckClassAdapter(classWriter);
		// final BaseVisitor cVisitor = new DoingNothingExampleVisitor(visitor,
		// classWriter);
		final BaseVisitor cVisitor;
		try {
			cVisitor = visitorClass.getConstructor(ClassVisitor.class,
					ClassWriter.class).newInstance(visitor, classWriter);
		} catch (Exception e) {
			logger.throwing(getClass().getName(),
					"process(byte[], Class<BaseVisitor>)", e);
			throw new RuntimeException(e);
		}

		// Process the given class.
		classReader.accept(cVisitor, /*ClassReader.SKIP_DEBUG
				| */ClassReader.EXPAND_FRAMES);

		if (cVisitor.isProcessing()) {
			return classWriter.toByteArray();
		} else {
			return null;
		}
	}

	public boolean isRecord() {
		return record;
	}

	public void setRecord(boolean record) {
		this.record = record;
	}

}
