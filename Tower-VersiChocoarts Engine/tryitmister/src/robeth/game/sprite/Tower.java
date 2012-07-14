/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.sprite;

import com.chocoarts.drawing.AnimatedSprite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Layer;

/**
 *
 * @author Maviosso
 */
public class Tower extends AnimatedSprite{
    private Layer area;
    
    //Area tembak relative terhadap sprite ini.
    private int range;
    
    //center adalah titik tengah dari tower ini. Digunakan untuk implementasi 
    //area tembak yang berbentuk lingkaran
    private int centerX;
    private int centerY;
    private int crX;
    private int crY;
    private int crWidth;
    private int crHeight;
    
    private int fireRate;
    private long lastTime;
          
    
    public Tower(Image image, int frameWidth, int frameHeight, int frameDuration, int range, int rate) {
        super(image, frameWidth, frameHeight, frameDuration);
        
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        this.range = range;
        this.fireRate = rate;
        
        crX = centerX - range;
        crY = centerY - range;
        crWidth = 2 * range;
        crHeight = 2 * range;
        defineCollisionRectangle(crX, crY, crWidth, crHeight);
    }

    public int getCenterX() {
        return centerX+getX();
    }

    public int getCenterY() {
        return centerY+getY();
    }

    public int getCrHeight() {
        return crHeight;
    }

    public int getCrWidth() {
        return crWidth;
    }

    public int getCrX() {
        return crX+getX();
    }

    public int getCrY() {
        return crY+getY();
    }

    public int getRange() {
        return range;
    }

    public boolean readyToShoot(){
        return System.currentTimeMillis() - lastTime >= fireRate;
    }
    
    public void commitShot(){
        lastTime = System.currentTimeMillis();
    }

    public void paintCollisionRectangle(Graphics g) {
        g.setColor(255,255,255);
        g.drawRect(getCrX(), getCrY(), getCrWidth(), getCrHeight());
        g.setColor(255,0,0);
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }
}
