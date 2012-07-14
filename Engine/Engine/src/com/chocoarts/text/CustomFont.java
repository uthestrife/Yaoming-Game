/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.text;

import java.io.IOException;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Class untuk menuliskan custom font di J2ME
 * Memerlukan image keluaran dari microfontmaker (http://j2me-mwt.sourceforge.net/microfontmaker.html)
 * Cara execute jarnya: java -jar microfont-maker.jar <ttf> chars.txt default 12 AA0000 out -dir -png
 *                      -   <ttf> itu nama file ttfnya,
 *                      -   chars.txt berisikan supported karakternya
 *                      -   12 itu ukuran fontnya
 *                      -   AA0000 itu kode warnanya
 *                      -   out itu nama folder/file keluarannya
 *                      -   -dir agar keluarannya berupa banyak file di folder
 *                      -   -png itu agar keluarannya juga berupa satu file png
 * 
 * Original Source Code was copied from http://budsus.wordpress.com/2009/02/12/431/
 * also modified by lieenghao 14 march 2012
 * @author lieenghao
 */
public class CustomFont {
    // Kalau ingin tambahin supportedSequence, ini dimodif aja
    // gak boleh ada spasi yah
    private static final String supportedSequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=,.<>;':\"[]{}/?\\|";
    private final Hashtable fontChars;
    int spaceAdditionalDistant;
    private int spaceWidth = -1;

    public CustomFont(String fontFolderName) throws IOException {
      fontChars = new Hashtable();
      for(int i=0; i < supportedSequence.length(); i++) {
        char c = supportedSequence.charAt(i);
        int code = c;
        fontChars.put(new Integer(code),
        Image.createImage( fontFolderName + "/" + code + ".png") );
      }
      // Spasi ngambil lebarnya si hurup 'o' aja deh...
      // TODO: Harus diperbaiki...
      fontChars.put(new Integer(32), Image.createImage( fontFolderName + "/97.png") );
    }

    public int drawChar(Graphics g, char ch, int x, int y, int anchor) {
      // find the position in the font
      if (ch != ' ') {
        int i = supportedSequence.indexOf(ch);
        if (i == -1) {
          throw new IllegalArgumentException("unsupported character: " + ch);
        }
      }
      
      int code = ch;
      Integer key = new Integer(code);
      Image img = (Image)fontChars.get(key);
      int wChar = img.getWidth();       // TODO: ini widthnya bisa disimpen di array, makin cepet lagi...
      
      if(code==32) {    // Kalo spasi nggak usah digambar
          if(spaceWidth==-1){
            return wChar;
          } else {
              return spaceWidth;
          }
      } else {
          // draw it
          g.drawImage(img, x, y, anchor);
          return wChar;
      }
    }

    private int charLength(int charCode){
        Image img = (Image)fontChars.get(new Integer(charCode));
        int wChar = img.getWidth();
        
        return wChar;
    }
    
    /**
     * Only support center or right or left alignment
     * @param g
     * @param s
     * @param x
     * @param y
     * @param anchor 
     * @return last x coordinate
     */
    public int paintString(Graphics g, String s, int x, int y, int anchor) {
        // this is faster than using s.charAt()
        char[] chs = s.toCharArray();

        if((anchor&Graphics.LEFT)!=0){
            for(int i = 0; i < chs.length; i++) {
                x += drawChar(g, chs[i], x, y, anchor) + spaceAdditionalDistant;
            }
        }
        else if((anchor&Graphics.RIGHT)!=0){
            for(int i = chs.length - 1; i >= 0 ; i--) {
               x -= drawChar(g, chs[i], x, y, anchor) + spaceAdditionalDistant;
            }
        } else if((anchor&Graphics.HCENTER)!=0){
            int sum = 0;
            for(int i = 0; i < chs.length; i++) {
                sum += charLength(chs[i]) + spaceAdditionalDistant;
            }
            x -= sum/2;
            for(int i = 0; i < chs.length; i++) {
                x += drawChar(g, chs[i], x, y, Graphics.LEFT|Graphics.TOP) + spaceAdditionalDistant;
            }
        }
        
        return x;
    }
    
    public void addSpaceDistant(int i) {
        spaceAdditionalDistant = i;
    }

    public void setSpaceWidth(int i) {
        spaceWidth = i;
    }
}
