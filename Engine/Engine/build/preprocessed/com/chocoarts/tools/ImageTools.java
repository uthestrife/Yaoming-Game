/*
 * To change this template, choose ImageTools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.tools;

import javax.microedition.lcdui.Image;

/**
 *
 * @author lieenghao
 */
public class ImageTools {
    
    /**
     * Resizing an image based on percentage of origin size/resolution.
     * The original image doesn't modified
     * @param modified image
     * @param percent
     * @return 
     */
    public static Image resizeImage(final Image image, int percent){
        return resizeImage(image,image.getWidth()*percent/100,image.getHeight()*percent/100);
    }
    
    /**
     * Diambil dari http://www.coderanch.com/t/228899/JME/Mobile/we-resize-image-me
     * @param image
     * @param resizedWidth
     * @param resizedHeight
     * @return 
     */
    public static Image resizeImage(final Image image, int resizedWidth, int resizedHeight) {  
          int[] in = null, out = null;  

          int width = image.getWidth(), height = image.getHeight();  
          in = new int[width];   

          int i=0;  
          int dy,dx;   
          out = new int[resizedWidth * resizedHeight];   

          for (int y = 0; y < resizedHeight; y++)   
          {   
            dy = y * height / resizedHeight;  

            image.getRGB(in,0,width,0,dy,width,1);   

            for (int x = 0; x < resizedWidth; x++)   
            {   
              dx = x * width / resizedWidth;   

              out[(resizedWidth * y) + x] = in[dx];  
            }   
          }   
          Image resized = Image.createRGBImage(out,resizedWidth,resizedHeight,true);  
          return resized;
    }
}
