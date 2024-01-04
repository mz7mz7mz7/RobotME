package org.robotme.core.recorder.state.widget;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This kind of {@link AbstractWidgetState} hasn't any special internal state. It is differentiated from other
 * {@link AbstractWidgetState} only by its type.
 * 
 * @author Marcin Zduniak
 */
public class SpacerWidgetState extends AbstractWidgetState {

    public SpacerWidgetState() {
        // Nothing special here
    }

    public boolean equals(AbstractWidgetState abstractWidgetState) {
        return (abstractWidgetState instanceof SpacerWidgetState);
    }

    public void restoreState(DataInputStream dis) throws IOException {
        // Empty implementation
    }

    public void saveState(DataOutputStream dos) throws IOException {
        // Empty implementation
    }

    public String toXML() {
        return "<spacer />";
    }

}
