/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.tower;

import com.chocoarts.ChocoMIDlet;
import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import javax.microedition.lcdui.Display;

/**
 *
 * @author Maviosso
 */
public class TowerMIDlet extends ChocoMIDlet{
    TowerProfile profile;
    Engine engine;
    
    public void startApp() {
        engine = new Engine(this, true, false);
        
        //Ambil save data permainan sebelumnya. increment
        profile = new TowerProfile();
        engine.initProfile(profile, "TowerSaveData");
        profile.numberOfPlay++;
        Debug.println("Number of play:"+profile.numberOfPlay);
        
        
        //Buat scene level 1
        Level1 lvl1 = new Level1(engine);
        engine.setFirstScene(lvl1);
        
        Display.getDisplay(this).setCurrent(engine);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        try {
            engine.getProfile().writeProfile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
