/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.sprite;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Layer;

/**
 *
 * @author Maviosso
 */

public class Projectile {
    private int x;
    private int y;
    private int size;
    private boolean isActive;
    private Layer target;
    private int speed;
    private boolean isFinish;

    public boolean isIsFinish() {
        return isFinish;
    }

    public void setIsFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }
    public Projectile(int x, int y, int size, int speed){
        setPosition(x,y);
        this.size = size;
        this.speed = speed;
    }
    
    public void paint(Graphics g) {
        g.fillRect(getX(), getY(), size, size);
    }
    
    public void update(){
        if(isActive && target != null){
            int deltaX = target.getX() - getX();
            int deltaY = target.getY() - getY();
            
            deltaX = (deltaX > 0)? 1 : ((deltaX == 0)? 0 : -1); 
            deltaY = (deltaY > 0)? 1 : ((deltaY == 0)? 0 : -1);
            
            setPosition(getX()+deltaX,getY()+deltaY);
            if(deltaX == 0 && deltaY == 0){
                isFinish = true;
                isActive = false;
            }
        }
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Layer getTarget() {
        return target;
    }

    public void setTarget(Layer target) {
        this.target = target;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean collidesWith(Layer target) {
        return x >= target.getX() && x <= target.getX()+target.getWidth() &&
                y >= target.getY() && y <= target.getY()+target.getHeight();
    }

    public void paintRect(Graphics g) {
        g.drawRect(getX(), getY(), getSize(), getSize());
    }
    
}

