/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.sprite;

import com.chocoarts.debug.Debug;
import com.chocoarts.drawing.AnimatedSprite;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Maviosso
 */
public class Enemy extends AnimatedSprite{
    private Path path;
    private int nextIndex;
    
    public Enemy(Image image, int frameWidth, int frameHeight, int frameDuration, Path path){
        super(image, frameWidth, frameHeight, frameDuration);
        this.path = path;
        if(path!=null && path.count > 0){
            nextIndex = 0;
            setPosition(path.points[nextIndex][0], path.points[nextIndex][1]);
            nextIndex++;
        }
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void update(long currentTime) {
        super.update(currentTime);
        //Go to next destination point
        if(path != null && path.count > nextIndex ){
            int deltaX = path.points[nextIndex][0]-getX();
            int deltaY = path.points[nextIndex][1]-getY();
            
            deltaX = (deltaX > 0)? 1 : ((deltaX == 0)? 0 : -1); 
            deltaY = (deltaY > 0)? 1 : ((deltaY == 0)? 0 : -1);
            
            move(deltaX,deltaY);
            if(deltaX == 0 && deltaY == 0)
                nextIndex++;
        }
    }
    
    
    
}
