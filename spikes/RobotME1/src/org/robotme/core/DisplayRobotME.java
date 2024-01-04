package org.robotme.core;

import java.util.Vector;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;

/**
 * @author Marcin Zduniak
 */
public class DisplayRobotME {
	
	private final static int NO_ITEM_SELECTED = -1;
	
	private final Display display;
	private final Vector itemsAddedtoCurrentDisplayable;
	private int currentItem;
	private int numberOfItems;

	/* package scope */ DisplayRobotME(Display display) {
		super();
		this.display = display;
		this.itemsAddedtoCurrentDisplayable = new Vector();
		clearItemsList();
		
	}
	
	public Displayable getCurrenDisplayable() {
		return display.getCurrent();
	}
	
	private synchronized void clearItemsList() {
		itemsAddedtoCurrentDisplayable.setSize(0);
		currentItem = NO_ITEM_SELECTED;
		numberOfItems = 0;
	}
	
	private void addItem(Item item) {
		itemsAddedtoCurrentDisplayable.addElement(item);
	}
	
	public synchronized void simulateButtonDownOnScreen() {
		Displayable disp = display.getCurrent();
		if (disp instanceof Form) {
			Form form = (Form) disp;
			if (currentItem < numberOfItems - 1) {
				currentItem++;
			}
			display.setCurrentItem(form.get(currentItem));
		} else if (disp instanceof List) {
			List list = (List) disp;
			if (currentItem < numberOfItems - 1) {
				currentItem++;
			}
			list.setSelectedIndex(currentItem, true);
		}
		else {
			throw new RuntimeException("Displayable: " + disp.toString()
					+ " do not support buttons simulation");
		}
	}
	
	/**
	 * Tick given items in current ChoiceGroup, and make all others unticked.
	 *  
	 * @param itemsToSelect
	 */
	public synchronized void simylateSelectionInChoiceGroup(int[] itemsToSelect) {
		Displayable disp = display.getCurrent();
		if (disp instanceof Form) {
			Form form = (Form) disp;
			Item item = form.get(currentItem);
			if (item instanceof ChoiceGroup) {
				ChoiceGroup choice = (ChoiceGroup) item;
				
				// clear all selection
				for (int i = 0; i < choice.size(); i++) {
					choice.setSelectedIndex(i, false);
				}
				
				// select given choices
				for (int i = 0; i < itemsToSelect.length; i++) {
					final int index = itemsToSelect[i];
					if (index > choice.size()) {
						throw new RuntimeException("Given index too high");
					}
					choice.setSelectedIndex(index, true);
				}
				
			} else {
				throw new RuntimeException("Current item is not a ChoiceGroup");
			}
		} else {
			throw new RuntimeException("Displayable: " + disp.toString()
					+ " do not support selection in ChoiceGroup");
		}
	}
	
	public synchronized void setCurrentDisplayable(Displayable disp) {
		clearItemsList();
		
		if (disp instanceof Form) {
			Form form = (Form) disp;
			
			numberOfItems = form.size();
			if (numberOfItems > 0) {
				currentItem = 0;
			} else {
				currentItem = NO_ITEM_SELECTED;
			}
			
			for (int i = 0; i < numberOfItems; i++) {
				addItem(form.get(i));
			}
		} else if (disp instanceof List) {
			List list = (List) disp;
			
			numberOfItems = list.size();
			if (numberOfItems > 0) {
				currentItem = 0;
			} else {
				currentItem = NO_ITEM_SELECTED;
			}
		} else if (disp instanceof TextBox) {
			//TextBox textBox = (TextBox) disp;
			
			currentItem = NO_ITEM_SELECTED;
			numberOfItems = 0;
		} else if (disp instanceof Alert) {
			//Alert alert = (Alert) disp;
			
			currentItem = NO_ITEM_SELECTED;
			numberOfItems = 0;
		} else if (disp instanceof Canvas) {
			//Canvas canvas = (Canvas) disp;
			
			currentItem = NO_ITEM_SELECTED;
			numberOfItems = 0;
		} else {
			throw new RuntimeException("Displayable: " + disp.toString() + " not implemented");
		}
		
		display.setCurrent(disp);
	}

}
