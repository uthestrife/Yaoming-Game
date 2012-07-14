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
import javax.microedition.lcdui.game.GameCanvas;
import robeth.game.logic.World;
import robeth.game.sprite.Enemy;
import robeth.game.sprite.Path;
import robeth.game.sprite.Tower;

/**
 *
 * @author Maviosso
 */
public class Level1 extends Scene{
    World world;
    Tower tower, tower2;
    Enemy enemy, enemy2;
    Image towerImage, enemyImage;
    
    public Level1(Engine engine){
        super(engine);
        
    }
    
    public void init() throws Exception {
        int[][] arrays = {{0,100},{50,100},{200,100}};
        Path path = new Path(arrays);
        try {
            towerImage = Image.createImage("/player.png");
            enemyImage = Image.createImage("/obstacle.png");
            tower = new Tower(towerImage,45,63,200,50,2000);
            tower2 = new Tower(towerImage,45,63,200,50,2000);
            enemy = new Enemy(enemyImage,32,32,150,path );
            enemy2 = new Enemy(enemyImage,32,32,150,path );
            
            enemy.setPosition(0,0);
            enemy2.setPosition(0,200);
            tower.setPosition(100,20);
            tower2.setPosition(100,150);
            
            world = new World();
            world.getTowers().addElement(tower);
            world.getTowers().addElement(tower2);
            world.getEnemies().addElement(enemy);
            world.getEnemies().addElement(enemy2);
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
        tower2.update(currentTime);
        enemy.update(currentTime);
        enemy2.update(currentTime);
        
        world.update();
    }

    public void paint(Graphics g) {
        tower.paint(g);
        tower2.paint(g);
        tower.paintCollisionRectangle(g);
        tower2.paintCollisionRectangle(g);
        enemy.paint(g);
        enemy2.paint(g);
        
        world.paint(g);
    }

    public void keyPressed(int keyCode, int rawKeyCode) {
        Debug.println("Pressed key:"+rawKeyCode);
        if(keyCode == GameCanvas.UP){
            tower.move(0,-1);
        } else if(keyCode == GameCanvas.DOWN){
            tower.move(0,1);
        } else if(keyCode == GameCanvas.LEFT){
            tower.move(-1,0);
        } else if(keyCode == GameCanvas.RIGHT){
            tower.move(1,0);
        }
    }

    public void pointerPressed(int x, int y) {
    }

    public void sleep() {
    }
    
}
