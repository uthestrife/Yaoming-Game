/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.logic;

import com.chocoarts.debug.Debug;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Layer;
import robeth.game.sprite.Enemy;
import robeth.game.sprite.Projectile;
import robeth.game.sprite.Tower;

/**
 *
 * @author Maviosso
 */
public class World {
    private Vector towers;
    private Vector enemies;
    private Vector projectiles;

    public Vector getEnemies() {
        return enemies;
    }

    public void setEnemies(Vector enemies) {
        this.enemies = enemies;
    }

    public Vector getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(Vector projectiles) {
        this.projectiles = projectiles;
    }

    public Vector getTowers() {
        return towers;
    }

    public void setTowers(Vector towers) {
        this.towers = towers;
    }
    
    public World(){
        towers = new Vector(5);
        enemies = new Vector(5);
        projectiles = new Vector (5);
    }
    
    public void update(){
        for(int i = 0; i < towers.size(); i++){
            Tower t = (Tower)towers.elementAt(i);
            for(int j = 0; j < enemies.size(); j++){
                Enemy e = (Enemy)enemies.elementAt(j);
                if(t.readyToShoot() && t.collidesWith(e,false)){
                    t.commitShot();
                    
                    Debug.printFree("Add Projectile!!1: ");
                    Projectile p = new Projectile(t.getCenterX(), t.getCenterY(), 10, 3);
                    Debug.printFree("Add Projectile!!2: ");
                    p.setTarget(e);
                    p.setIsActive(true);
                    projectiles.addElement(p);
                }
            }
        }
        
        for(int i = 0; i< projectiles.size(); i++){
            Projectile p = (Projectile)projectiles.elementAt(i);
            
            if(p!=null ){
                if(p.isIsFinish())projectiles.removeElementAt(i);
                else if(p.isIsActive())p.update();
            }
        }
    }
    
    public void paint(Graphics g){
        for(int i = 0; i < projectiles.size(); i++){
            Projectile p = (Projectile)projectiles.elementAt(i);
            if(p != null){
                p.paintRect(g);
            }
        }
    }
}
