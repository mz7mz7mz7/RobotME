package org.robotme.tests;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcin Zduniak
 */
public class BytecodeTest {

    // TODO: this path must not be hardcoded
    private final String BYTECODE_DIR = getClass().getResource("/").getPath() + "../bytecode";

    private Robotizer robotizer;

    @Before
    public void setUp() {
        robotizer = new Robotizer();
        robotizer.setBytecodeDir(BYTECODE_DIR);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Test
    public void testRecordngBytecode() throws Exception {
        robotizer.simulateRecording();

        final ASMifier asmifier = new ASMifier(true, BYTECODE_DIR);
        asmifier.simulateAndTest();
    }

    /**
     * @param args
     * @throws Exception
     */
    @Test
    public void testReplayingBytecode() throws Exception {
        robotizer.simulateReplaying();

        final ASMifier asmifier = new ASMifier(false, BYTECODE_DIR);
        asmifier.simulateAndTest();
    }

}
