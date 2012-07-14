/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.drawing;

import com.chocoarts.scene.Scene;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * Class Button yang merepresentasikan sebuah tombol pada game J2ME.
 * Button terdapat 2 jenis: dengan satu image, atau dengan 2 image.
 * Jenis-jenis button ini bisa dipilih berdasarkan constructor yang dipanggil.
 * @author lieenghao
 */
public class Button {
    public final static int DEFAULT = 0, SELECTED = 1;
    int currentState = DEFAULT;
    AnimatedSprite [] animatedSprites;
    Sprite [] sprites;
    Runnable selectListener, unselectListener;
    
    // Apakah button pada state tertentu merupakan AnimatedSprite?
    // Cara mengeceknya if(isAnimated[DEFAULT]) atau if(isAnimated[SELECTED])
    boolean [] isAnimated;
    // Apakah buttonnya memiliki 2 macam image.
    // Kalau satu image, Spritenya bikin satu doang
    boolean twoImage;
    
    /**
     * Membuat objek Button dengan menggunakan 1 image untuk 2 state yang ada
     * ( DEFAULT dan SELECTED )
     * @param filePath Path ke image
     * @throws Exception 
     */
    public Button(String filePath) throws Exception{
        sprites = new Sprite[1];
        sprites[DEFAULT] = new Sprite(Image.createImage(filePath));
        twoImage = false;
        isAnimated = new boolean[2];
        isAnimated[DEFAULT] = false;
        isAnimated[SELECTED] = false;
    }
    
    public void setSelectListener(Runnable runnable){
        selectListener = runnable;
    }
    
    public void setUnselectListener(Runnable runnable){
        unselectListener = runnable;
    }
    
    /**
     * Membuat objek Button dengan menggunakan 2 image untuk setiap state yang 
     * ada ( DEFAULT dan SELECTED )
     * @param filePath1 Path ke image saat button default
     * @param filePath2 Path ke image saat button selected
     * @throws Exception 
     */
    public Button(Scene scene, String filePath1, String filePath2) throws Exception{
        sprites = new Sprite[2];
        sprites[DEFAULT] = scene.getSprite(filePath1);
        sprites[SELECTED] = scene.getSprite(filePath2);
        twoImage = true;
        isAnimated = new boolean[2];
        isAnimated[DEFAULT] = false;
        isAnimated[SELECTED] = false;
    }
    
    /**
     * Membuat objek Button dengan menggunakan 2 image untuk setiap state yang 
     * ada ( DEFAULT dan SELECTED ).
     * @param animated1 AnimatedSprite pada saat kondisi default
     * @param animated2 AnimatedSprite pada saat kondisi selected
     * @throws Exception 
     */
    public Button(AnimatedSprite animated1, AnimatedSprite animated2) throws Exception{
        animatedSprites = new AnimatedSprite[2];
        animatedSprites[DEFAULT] = animated1;
        animatedSprites[SELECTED] = animated2;
        twoImage = true;
        isAnimated = new boolean[2];
        isAnimated[DEFAULT] = true;
        isAnimated[SELECTED] = true;
    }
    
    /**
     * Membuat objek Button dengan menggunakan 1 image untuk setiap state yang 
     * ada (DEFAULT dan SELECTED).
     * @param animated2 AnimatedSprite pada saat kondisi default dan selected
     * @throws Exception 
     */
    public Button(AnimatedSprite animated) throws Exception{
        animatedSprites = new AnimatedSprite[2];
        animatedSprites[DEFAULT] = animated;
        animatedSprites[SELECTED] = animated;
        twoImage = false;
        isAnimated = new boolean[2];
        isAnimated[DEFAULT] = true;
        isAnimated[SELECTED] = true;
        
    }
    
    public int getX(){
        return getCurrentSprite().getX();
    }
    
    /**
     * Membuat Objek Button ini berada pada state SELECTED, yg menyebabkan
     * gambar yang ditampilkan Button ini berubah (jika contructornya menggunakan
     * 2 buah image)
     */
    public void select(){
        if(twoImage && isAnimated[SELECTED]){
            animatedSprites[SELECTED].play();
        }
        if(selectListener!=null){
            selectListener.run();
        }
        currentState = SELECTED;
    }
    
    /**
     * Membuat Objek Button ini berada pada state DEFAULT
     */
    public void unselect(){
        if(twoImage && isAnimated[DEFAULT]){
            animatedSprites[DEFAULT].play();
        }
        if(unselectListener!=null){
            unselectListener.run();
        }
        currentState = DEFAULT;
    }
    
    /**
     * Menggambar Button ini pada Graphic pada parameter
     * @param g Objek Graphics
     */
    public void paint(Graphics g){
        if(twoImage){
            if(isAnimated[currentState]){
                animatedSprites[currentState].paint(g);
            } else {
                sprites[currentState].paint(g);
            }
        } else {
             if(isAnimated[DEFAULT]){
                animatedSprites[DEFAULT].paint(g);
            } else {
                sprites[DEFAULT].paint(g);
            }
        }
    }

    /**
     * Memposisikan Objek Button pada layar
     * @param koordinat x pada layar
     * @param koordinat y pada layar
     */
    public void setPosition(int x, int y){
        if(twoImage){
            for( int i = 0 ; i < 2 ; i++ ){
                if(isAnimated[i]){
                    animatedSprites[i].setPosition(x, y);
                } else {
                    sprites[i].setPosition(x, y);
                }
            }
        } else {
            if(isAnimated[DEFAULT]){
                animatedSprites[DEFAULT].setPosition(x, y);
            } else {
                sprites[DEFAULT].setPosition(x, y);
            }
        }
    }

    /**
     * Apakah Button ini sedang dipilih oleh pemain
     * @return 
     */
    public final boolean isSelected() {
        return currentState == SELECTED;
    }

    /**
     * Mengupdate AnimatedSprite pada Button ini, jika menggunakan AnimatedSprite
     * pada contructornya
     * @param currentTime 
     */
    public void update(long currentTime) {
        for(int i = 0 ; i < 2 ; i++ ){
            if(isAnimated[i]){
                animatedSprites[i].update(currentTime);
            }
        }
    }

    public void sleep() {
        for(int i = 0 ; i < 2 ; i++ ){
            if(isAnimated[i]){
                animatedSprites[i].sleep();
            }
        }
    }

    public int getWidth() {
        return getCurrentSprite().getWidth();
    }
    
    public Sprite getCurrentSprite(){
        if(twoImage){
            if(isAnimated[currentState]){
                return animatedSprites[currentState];
            } else {
                return sprites[currentState];
            }
        } else {
            if( isAnimated[DEFAULT] ){
                return animatedSprites[DEFAULT];
            } else {
                return sprites[DEFAULT];
            }
        }
    }
    
    public final boolean isInside(int x, int y){
        Sprite sprite = getCurrentSprite();
        return (x >= sprite.getX() && x <= (sprite.getX()+sprite.getWidth())) &&
                (y >= sprite.getY() && y <= (sprite.getY()+sprite.getHeight()));
    }

    public int getY() {
        return getCurrentSprite().getY();
    }

    public void setVisible(boolean isVisible) {
        if(twoImage){
            for( int i = 0 ; i < 2 ; i++ ){
                if(isAnimated[i]){
                    animatedSprites[i].setVisible(isVisible);
                } else {
                    sprites[i].setVisible(isVisible);
                }
            }
        } else {
            if(isAnimated[DEFAULT]){
                animatedSprites[DEFAULT].setVisible(isVisible);
            } else {
                sprites[DEFAULT].setVisible(isVisible);
            }
        }
    }
}

