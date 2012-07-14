/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.drawing;

import com.chocoarts.shape.Rectangle;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author lieenghao
 */
public class ChocoSprite extends Sprite{
    // Rectangle which bounds the Animated Sprite
    private Rectangle rectangle;
    
    public ChocoSprite(Image image){
        super(image);
    }
    
    public ChocoSprite(Image image, int frameWidth, int frameHeight){
        super(image, frameWidth, frameHeight);
    }
    
    public ChocoSprite(Sprite sprite){
        super(sprite);
    }
    
    public Rectangle getRectangle(){
        if(rectangle==null){
            rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        }
        return rectangle;
    }
    
    /**
     * Mengetes apakah satu titik yang dimasukkan berada pada area penggambaran
     * animatedSprite. Area penggambaran yang dimaksud adalah bounding rectangle
     * dari animatedSprite.
     * @param koordinat x dari titik yang dicoba
     * @param koordinat y dari titik yang dicoba
     * @return true jika berada didalam kotak / bounding rectangle
     */
    public final boolean isInside(int x,int y){
        return (x >= this.getX() && x <= (this.getX()+this.getWidth())) &&
                (y >= this.getY() && y <= (this.getY()+this.getHeight()));
    }
}
