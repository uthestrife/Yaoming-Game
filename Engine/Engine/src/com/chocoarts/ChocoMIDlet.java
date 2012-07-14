/*
 * 
 */
package com.chocoarts;

import javax.microedition.midlet.*;

/**
 * Class ChocoMIDlet are created for MIDLet template which enable us
 * to create Engine object later in the project
 * @author lieenghao
 */
abstract public class ChocoMIDlet extends MIDlet {
    
    /**
     * Called when application start
     */
    abstract public void startApp();
    
    /**
     * I'm not using this method
     */
    abstract public void pauseApp();
    
    /**
     * Called when application is closed
     */
    abstract public void destroyApp(boolean unconditional);
}
