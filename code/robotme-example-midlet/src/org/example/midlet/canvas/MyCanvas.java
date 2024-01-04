package org.example.midlet.canvas;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

import org.robotme.core.log.entries.KeyEventOnCanvasLogEntry;
import org.robotme.core.recorder.RobotMERecorder;

public class MyCanvas extends Canvas {

	protected void paint(Graphics g) {
		// painting goes here
	}

	protected void keyPressed(int keyCode) {
		RobotMERecorder.getRecorderInstance().keyEventOnCanvas(
				KeyEventOnCanvasLogEntry.KEY_PRESSED_TYPE, keyCode);
		$robotized$keyPressed(keyCode);
	}

	// NOTE: method signature becomes private and final !
	private final void $robotized$keyPressed(int keyCode) {
		System.out.println("In keyPress");
	}

}
