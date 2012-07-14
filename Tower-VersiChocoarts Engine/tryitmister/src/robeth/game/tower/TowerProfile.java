/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.tower;

import com.chocoarts.network.Profile;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Maviosso
 */
public class TowerProfile extends Profile{
    int numberOfPlay;
    
    public void reset() {
        numberOfPlay = 0;
    }

    public void writeVariables(DataOutputStream dos) throws Exception {
        dos.writeInt(numberOfPlay);
    }

    public void readVariables(DataInputStream dis) throws Exception {
        this.numberOfPlay = dis.readInt();
    }
    
}
