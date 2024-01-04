package org.robotme.core.log.replayable;

import java.util.Date;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.TextField;

import org.robotme.core.log.entries.FormModificationLogEntry;
import org.robotme.core.log.entries.LogEntry;
import org.robotme.core.recorder.state.widget.AbstractWidgetState;
import org.robotme.core.recorder.state.widget.ChoiceGroupWidgetState;
import org.robotme.core.recorder.state.widget.DateFieldWidgetState;
import org.robotme.core.recorder.state.widget.GaugeWidgetState;
import org.robotme.core.recorder.state.widget.ImageItemWidgetState;
import org.robotme.core.recorder.state.widget.SpacerWidgetState;
import org.robotme.core.recorder.state.widget.StringItemWidgetState;
import org.robotme.core.recorder.state.widget.TextFieldWidgetState;
import org.robotme.core.replaying.RobotMEReplaying;
import org.robotme.core.util.Assert;

/**
 * @author Marcin Zduniak
 */
public class ReplayableFormModificationLogEntry implements Replayable {

    public void replay(LogEntry logEntry, RobotMEReplaying replayingEngine) throws ReplayableException {
        final Displayable disp = replayingEngine.getCurrentDisplayable();
        if (disp instanceof Form) {
            final Form f = (Form) disp;
            final FormModificationLogEntry fe = (FormModificationLogEntry) logEntry;
            applyChanges(fe, f);
        } else {
            throw new RuntimeException("Ooops... Current displayable != Form ???");
        }
    }

    // TODO: apply changes only when occurred and use Display#setCurrentItem(Item) to change focus
    private void applyChanges(final FormModificationLogEntry fe, final Form f) throws ReplayableException {
        final AbstractWidgetState[] abstractWidgetStates = fe.getAbstractWidgetStates();
        for (int i = 0; i < abstractWidgetStates.length; i++) {
            if (abstractWidgetStates[i] instanceof ChoiceGroupWidgetState) {
                if (f.get(i) instanceof ChoiceGroup) {
                    final ChoiceGroupWidgetState w = (ChoiceGroupWidgetState) abstractWidgetStates[i];
                    final boolean[] selectedFlags = w.getSelectedFlags();
                    final ChoiceGroup cg = (ChoiceGroup) f.get(i);
                    cg.setSelectedFlags(selectedFlags);
                } else {
                    throw new ReplayableException("Incompatible widget type: " + f.get(i).getClass().getName() + ", expected: ChoiceGroup");
                }
            } else if (abstractWidgetStates[i] instanceof DateFieldWidgetState) {
                if (f.get(i) instanceof DateField) {
                    final DateFieldWidgetState w = (DateFieldWidgetState) abstractWidgetStates[i];
                    final DateField df = (DateField) f.get(i);
                    final Date d = w.getDate();
                    if (null != d) {
                        df.setDate(d);
                    }
                } else {
                    throw new ReplayableException("Incompatible widget type: " + f.get(i).getClass().getName() + ", expected: DateField");
                }
            } else if (abstractWidgetStates[i] instanceof GaugeWidgetState) {
                // TODO: this widget is replayable only in interactive mode (this condition is not implemented yet)
                if (f.get(i) instanceof Gauge) {
                    final GaugeWidgetState w = (GaugeWidgetState) abstractWidgetStates[i];
                    final Gauge g = (Gauge) f.get(i);
                    g.setValue(w.getValue());
                } else {
                    throw new ReplayableException("Incompatible widget type: " + f.get(i).getClass().getName() + ", expected: Gauge");
                }
            } else if (abstractWidgetStates[i] instanceof ImageItemWidgetState) {
                // This kind of widgets is not replayable
            } else if (abstractWidgetStates[i] instanceof SpacerWidgetState) {
                // This kind of widgets is not replayable
            } else if (abstractWidgetStates[i] instanceof StringItemWidgetState) {
                // This kind of widgets is not replayable
            } else if (abstractWidgetStates[i] instanceof TextFieldWidgetState) {
                if (f.get(i) instanceof TextField) {
                    final TextFieldWidgetState w = (TextFieldWidgetState) abstractWidgetStates[i];
                    final TextField tf = (TextField) f.get(i);
                    tf.setString(w.getString());
                } else {
                    throw new ReplayableException("Incompatible widget type: " + f.get(i).getClass().getName() + ", expected: TextField");
                }
            } /*else if (abstractWidgetStates[i] instanceof CustomItemWidgetState) {
                // TODO: implement
                throw new RuntimeException("CustomItems are not supported yet");
            }*/ else {
                if (Assert.ASSERT_ON) {
                    Assert.unreachableCode("Unrecognized widget type: " + abstractWidgetStates[i]);
                }
            }
        }
        
    }

}
