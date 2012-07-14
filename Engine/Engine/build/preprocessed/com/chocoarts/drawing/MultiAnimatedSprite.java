/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.drawing;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author lieenghao
 */
public class MultiAnimatedSprite {
    public static int TEMPLATE = 0;
    
    AnimatedSprite [] animatedSprites;
    
    // Relative Position from this sprite origin
    int [] relativeX, relativeY;
    
    public MultiAnimatedSprite(int spriteCount){
        animatedSprites = new AnimatedSprite[spriteCount];
        relativeX = new int[spriteCount];
        relativeY = new int[spriteCount];
    }
    
    public void setAnimatedSprite(int index, AnimatedSprite animatedSprite, int relativeX, int relativeY){
        this.animatedSprites[index] = animatedSprite;
        this.relativeX[index] = relativeX;
        this.relativeY[index] = relativeY;
    }
    
    public void update(long currentTime){
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].update(currentTime);
        }
    }
    
    public void sleep(long currentTime){
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].sleep();
        }
    }
    
    public void paint(Graphics g){
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].paint(g);
        }
    }
    
    public void setPosition(int x, int y){
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].setPosition(x+relativeX[i], y+relativeY[i]);
        }
    }

    public void stop() {
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].stop();
        }
    }

    public void setVisible(boolean b) {
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].setVisible(b);
        }
    }
    
    public void play() {
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].play();
        }
    }
    
    public void sleep() {
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].sleep();
        }
    }
    
    public void setAnimateCount(int count) {
        for( int i = 0 ; i < animatedSprites.length ; i++ ){
            animatedSprites[i].setAnimateCount(count);
        }
    }
    
    public int getX() {
        return animatedSprites[TEMPLATE].getX();
    }
    
    public int getY() {
        return animatedSprites[TEMPLATE].getY();
    }

    public int getFrame() {
        return animatedSprites[TEMPLATE].getFrame();
    }

    public int getWidth() {
        return animatedSprites[TEMPLATE].getWidth();
    }
}
