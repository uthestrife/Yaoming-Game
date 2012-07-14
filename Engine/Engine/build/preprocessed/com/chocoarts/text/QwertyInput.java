/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.text;

/**
 * Kelas untuk menerima input keyboard Qwerty untuk J2ME
 * @author lieenghao
 */
public class QwertyInput extends UserInput{
    
    public QwertyInput( int validChars, int maxCharacters){
        super(validChars,maxCharacters);
    }
    
    /**
     * Valid character default-nya ALPHA_NUMERIC
     */
    public QwertyInput(){
        this(ALPHA_NUMERIC, Integer.MAX_VALUE);
    }
    
    public boolean keyPressed(int keyCode, int rawKeyCode){
        // Karakter input yang baru saja dimasukkan user
        lastInputChar = rawKeyCode;
        
        // Melakukan pengecekan pada ALPHA, NUMERIC, PUNCTUATION, dan BACKSPACE
        // Nilai validInput akan false jika input tidak valid
        validInput =  checkAlpha(rawKeyCode) || 
                        checkNumeric(rawKeyCode) || 
                        checkPunctuation(rawKeyCode) || 
                        checkBackspace(rawKeyCode) ||
                        checkSpace(rawKeyCode);
        
        return validInput;
    }

    public void update(long currentTime) {
        
    }
}
