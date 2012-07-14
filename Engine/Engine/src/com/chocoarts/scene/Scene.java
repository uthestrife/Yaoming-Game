package com.chocoarts.scene;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import com.chocoarts.drawing.ChocoSprite;
import com.chocoarts.dynamic.Attributes;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author lieenghao
 */
abstract public class Scene {
    public Engine engine;
    private boolean wannaChangeScene, wannaCloseApp;
    
    public Scene(Engine engine){
        this.engine = engine;
    }
    
    abstract public void init() throws Exception;
    
    abstract public void pause();
    
    abstract public void start();
    
    abstract public void resume();
    
    abstract public void reset();
    
    abstract public void update(long currentTime);
    
    abstract public void paint(Graphics g);
    /**
     * Fungsi yang merespon input dari user.
     * Parameter keyCode harus merupakan return dari method getGameAction
     * keyCode = getGameAction(rawKeyCode)
     * rawKeyCode itu keyCode asli dari devicenya.
     * @param keyCode 
     */
    abstract public void keyPressed(int keyCode, int rawKeyCode);

    abstract public void pointerPressed(int x, int y);
    
    abstract public void sleep();

    public final boolean isWannaChangeScene() {
        return wannaChangeScene;
    }
    
    public final boolean isWannaCloseApp() {
        return wannaCloseApp;
    }
    
    public final synchronized void changeScene(Scene scene){
        if(wannaChangeScene){
           return; 
        }
        engine.setNextScene(scene);
        wannaChangeScene = true;
    }
    
    public final ChocoSprite getSprite(String filename) throws IOException{
        Debug.println("getSprite: " + filename);
        ChocoSprite sprite = new ChocoSprite(Image.createImage(filename));
        Attributes attrib = engine.getAttributes(filename);
        
        if(attrib!=null) {
            sprite.setPosition(attrib.getAttrib(Attributes.POSITION_X)
                    , attrib.getAttrib(Attributes.POSITION_Y));
        } else {
            Debug.println("Sprite " + filename + ", attributes is null");
        }
        
        return sprite;
    }
    
    public final AnimatedSprite getAnimatedSprite(String filename) throws IOException{
        Attributes attrib = engine.getAttributes(filename);
        AnimatedSprite animatedSprite = null;
        Image image = Image.createImage(filename);
        
        if(attrib==null){
//            Debug.println("AnimatedSprite " + filename + ", attributes is null");
            animatedSprite = new AnimatedSprite(image,image.getWidth(),image.getHeight(),100);
        } else {
            Debug.println("AnimatedSprite " + filename + " is applying attributes.");
            Debug.println("Width, Height, FPS: " + attrib.getAttrib(Attributes.FRAME_WIDTH) 
                    + ", " + attrib.getAttrib(Attributes.FRAME_HEIGHT) + ", " + attrib.getTimePerFrame());
            
            if(attrib.getAttrib(Attributes.IMAGE_TYPE)==Attributes.TYPE_ANIMATED){
                animatedSprite = new AnimatedSprite(image,attrib.getAttrib(Attributes.FRAME_WIDTH)
                    ,attrib.getAttrib(Attributes.FRAME_HEIGHT)
                    ,attrib.getTimePerFrame());
            } else {
                animatedSprite = new AnimatedSprite(image,image.getWidth()
                    ,image.getHeight(),1000);
            }
            animatedSprite.setPosition(attrib.getAttrib(Attributes.POSITION_X)
                    , attrib.getAttrib(Attributes.POSITION_Y));
        }
        return animatedSprite;
    }
    
    public final void closeApp(){
        wannaCloseApp = true;
    }
}


