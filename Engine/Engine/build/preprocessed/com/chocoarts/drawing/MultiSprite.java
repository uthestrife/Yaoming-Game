/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.drawing;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author lieenghao
 */
public class MultiSprite {
    public static int TEMPLATE = 0;
    
    Sprite [] sprites;
    
    // Relative Position from this sprite origin
    int [] relativeX, relativeY;
    
    public MultiSprite(int spriteCount){
        sprites = new Sprite[spriteCount];
        relativeX = new int[spriteCount];
        relativeY = new int[spriteCount];
    }
    
    public void setSprite(int index, Sprite sprite, int relativeX, int relativeY){
        this.sprites[index] = sprite;
        this.relativeX[index] = relativeX;
        this.relativeY[index] = relativeY;
        
//        System.out.println("Index, x, y:" + index 
//                + ", " + sprites[0].getX()+relativeX 
//                + ", "  + sprites[0].getY()+relativeY );
        sprite.setPosition(sprites[0].getX()+relativeX, sprites[0].getY()+relativeY);
    }
    
    public void paint(Graphics g){
        for( int i = 0 ; i < sprites.length ; i++ ){
            sprites[i].paint(g);
        }
    }
    
    public void setPosition(int x, int y){
        for( int i = 0 ; i < sprites.length ; i++ ){
            sprites[i].setPosition(x+relativeX[i], y+relativeY[i]);
        }
    }

    public void setVisible(boolean b) {
        for( int i = 0 ; i < sprites.length ; i++ ){
            sprites[i].setVisible(b);
        }
    }
    
    public int getX() {
        return sprites[TEMPLATE].getX();
    }
    
    public int getY() {
        return sprites[TEMPLATE].getY();
    }

    public int getFrame() {
        return sprites[TEMPLATE].getFrame();
    }

    public int getWidth() {
        return sprites[TEMPLATE].getWidth();
    }
}
