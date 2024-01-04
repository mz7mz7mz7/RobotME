package org.robotme.lcdui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemStateListener;
import javax.microedition.lcdui.Ticker;

import org.robotme.core.Context;

/**
 * Wrapper for {@link javax.microedition.lcdui.Form} class.
 * 
 * @author Marcin Zduniak
 */
public class FormRobotME extends javax.microedition.lcdui.Form {

	public FormRobotME(String s) {
		super(s);
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "Form(String)");
			// other code from wraping contructor
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "Form(String)", null);
		}
	}

	public FormRobotME(String s, Item[] is) {
		super(s, is);
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "FormRobotME(String,Item[])");
			// other code from wraping contructor
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "FormRobotME(String,Item[])", null);
		}
	}

	public int append(Image i) {
		int returnObject = -1;
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "append(Image)");
			returnObject = super.append(i);
			// other code from wraping method
		} catch (Throwable t) {
			Context.getContext().signalThrowException(this.getClass(), "append(Image)", t);
			throw t;
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "append(Image)", returnObject);
			return returnObject;
		}
	}

	public int append(Item i) {
		int returnObject = -1;
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "append(Item)");
			returnObject = super.append(i);
		} catch (Throwable t) {
			Context.getContext().signalThrowException(this.getClass(), "append(Item)", t);
			throw t;
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "append(Item)", returnObject);
			return returnObject;
		}
	}

	public int append(String s) {
		int returnObject = -1;
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "append(String)");
			returnObject = super.append(s);
		} catch (Throwable t) {
			Context.getContext().signalThrowException(this.getClass(), "append(String)", t);
			throw t;
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "append(String)", returnObject);
			return returnObject;
		}
	}

	public void delete(int i) {
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "delete(int)");
			super.delete(i);
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "delete(int)", null);
		}
	}

	public void deleteAll() {
		try {
			Context.getContext().signalEnterMethod(this.getClass(), "deleteAll");
			super.deleteAll();
		} finally {
			Context.getContext().signalLeaveMethod(this.getClass(), "deleteAll", null);
		}
	}

	public Item get(int arg0) {
		// TODO Auto-generated method stub
		return super.get(arg0);
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return super.getHeight();
	}
	
	public int getWidth() {
		// TODO Auto-generated method stub
		return super.getWidth();
	}

	public void insert(int arg0, Item arg1) {
		// TODO Auto-generated method stub
		super.insert(arg0, arg1);
	}

	public void set(int arg0, Item arg1) {
		// TODO Auto-generated method stub
		super.set(arg0, arg1);
	}

	public void setItemStateListener(ItemStateListener arg0) {
		// TODO Auto-generated method stub
		super.setItemStateListener(arg0);
	}

	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	public void addCommand(Command arg0) {
		// TODO Auto-generated method stub
		super.addCommand(arg0);
	}

	public Ticker getTicker() {
		// TODO Auto-generated method stub
		return super.getTicker();
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return super.getTitle();
	}

	public boolean isShown() {
		// TODO Auto-generated method stub
		return super.isShown();
	}

	public void removeCommand(Command arg0) {
		// TODO Auto-generated method stub
		super.removeCommand(arg0);
	}

	public void setCommandListener(CommandListener arg0) {
		// TODO Auto-generated method stub
		super.setCommandListener(arg0);
	}

	public void setTicker(Ticker arg0) {
		// TODO Auto-generated method stub
		super.setTicker(arg0);
	}

	public void setTitle(String arg0) {
		// TODO Auto-generated method stub
		super.setTitle(arg0);
	}

	protected void sizeChanged(int arg0, int arg1) {
		// TODO Auto-generated method stub
		super.sizeChanged(arg0, arg1);
	}

	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
