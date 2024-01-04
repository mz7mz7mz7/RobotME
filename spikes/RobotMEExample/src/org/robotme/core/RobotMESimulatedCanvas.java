package org.robotme.core;

/**
 * @author Marcin Zduniak
 */
public interface RobotMESimulatedCanvas extends CommandListenerSimulator {
    
    public abstract void keyPressed(int k) ;

    public abstract void keyReleased(int k) ;

    public abstract void keyRepeated(int arg0) ;

    public abstract void pointerDragged(int arg0, int arg1) ;

    public abstract void pointerPressed(int arg0, int arg1) ;

    public abstract void pointerReleased(int arg0, int arg1) ;

}
