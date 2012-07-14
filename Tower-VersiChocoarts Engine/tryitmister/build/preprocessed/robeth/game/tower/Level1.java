/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.tower;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import com.chocoarts.drawing.ChocoSprite;
import com.chocoarts.scene.Scene;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class Level1 extends Scene{
    AnimatedSprite tower, enemy;
    
    public Level1(Engine engine){
        super(engine);
        
    }
    
    public void init() throws Exception {
        try {
            Image towerImage = Image.createImage("/player.png");
            Image enemyImage = Image.createImage("/obstacle.png");
            tower = new AnimatedSprite(towerImage,45,63,200);
            enemy = new AnimatedSprite(enemyImage,32,32,150 );
            
            tower.setPosition(100,100);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pause() {
    }

    public void start() {
    }

    public void resume() {
    }

    public void reset() {
    }

    public void update(long currentTime) {
        tower.update(currentTime);
        enemy.update(currentTime);
    }

    public void paint(Graphics g) {
        tower.paint(g);
        enemy.paint(g);
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        Debug.println("Pressed key:"+keyCode);
        if(keyCode == Canvas.KEY_NUM2){
            tower.move(0,-1);
        } else if(keyCode == Canvas.KEY_NUM8){
            tower.move(0,1);
        } else if(keyCode == Canvas.KEY_NUM4){
            tower.move(-1,0);
        } else if(keyCode == Canvas.KEY_NUM6){
            tower.move(1,0);
        }
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
    }
    
}
