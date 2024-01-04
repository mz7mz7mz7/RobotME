package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * DO NOT PLACE ANY MICROEDITION SPECIFIC REFRENCES IN THIS CLASS AND ITS SUBCLASSES.
 * 
 * @author Marcin Zduniak
 */
public abstract class AbstractWidgetState {
    
    public final static byte WIDGET_STATE_CHOICE_GROUP = 0;
    public final static byte WIDGET_STATE_DATE_FIELD = 1;
    public final static byte WIDGET_STATE_GAUGE = 2;
    public final static byte WIDGET_STATE_IMAGE_ITEM = 3;
    public final static byte WIDGET_STATE_SPACER = 4;
    public final static byte WIDGET_STATE_STRING_ITEM = 5;
    public final static byte WIDGET_STATE_TEXT_FIELD = 6;
    public final static byte WIDGET_STATE_CHOICE_CUSTOM_ITEM = 7;

    public abstract boolean equals(AbstractWidgetState abstractWidgetState) ;
    
    public abstract void restoreState(DataInputStream dis) throws IOException;
    
    public abstract void saveState(DataOutputStream dos) throws IOException;
    
    public abstract String toXML();
}
