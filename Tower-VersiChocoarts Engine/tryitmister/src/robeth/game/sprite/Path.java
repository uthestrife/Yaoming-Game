/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robeth.game.sprite;

/**
 *
 * @author Maviosso
 */
public class Path {
    int[][] points;
    int count;
    public Path(int[][] points){
        this.points = points;
        this.count = points.length;
    }
}
