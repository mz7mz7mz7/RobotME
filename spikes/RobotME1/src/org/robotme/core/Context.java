package org.robotme.core;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;

/**
 * @author Marcin Zduniak
 */
public class Context {
	
	private static Context ctx;
	private static DisplayRobotME display;

	private Context() {
		super();
	}
	
	public synchronized static Context getContext() {
		if (null == display) {
			throw new RuntimeException("Context should be initialized init(MIDlet) method first");
		}
		if (null == ctx) {
			ctx = new Context(); 
		}
		return ctx;
	}
	
	public static void init(MIDlet midlet) {
		if (null == display) {
			display = new DisplayRobotME(Display.getDisplay(midlet));
		}
	}
	
	public void setTextInCurrentDisplayable(String text) {
		Displayable disp = display.getCurrenDisplayable();
		if (disp instanceof Form) {
			//Form form = (Form) disp;
			// TODO: implement
			//display.setCurrentItem();
		} else if (disp instanceof TextBox) {
			TextBox textBox = (TextBox) disp;
			textBox.setString(text);
		} else {
			throw new RuntimeException("Incorrect Displayable");
		}
	}
	
	public void signalEnterMethod(Class clazz, String method) {
		final String log = "[RobotME] Entering: " + clazz.getName()
			+ "#" + method;
		System.out.println(log);
	}
	
	public void signalLeaveMethod(Class clazz, String method, Object returnObject) {
		final String log = "[RobotME] Leaving: " + clazz.getName()
				+ "#" + method + " return: "
				+ (null != returnObject ? returnObject.toString() : "null");
		System.out.println(log);
	}
	
	public void signalLeaveMethod(Class clazz, String method, int returnObject) {
		signalLeaveMethod(clazz, method, new Integer(returnObject));
	}
	
	public void signalThrowException(Class clazz, String method, Throwable throwable) {
		final String log = "[RobotME] Exception: " + clazz.getName()
			+ "#" + method + " exception:" + throwable.getClass().getName();
		System.out.println(log);
	}

}
