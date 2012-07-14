/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.text;

import com.chocoarts.component.Timer;

/**
 * Kelas untuk menerima input keyboard Qwerty untuk J2ME
 * @author lieenghao
 */
public class NumpadInput extends UserInput{
    Timer timer;
    
    public NumpadInput( int validChars, int maxCharacters){
        super(validChars,maxCharacters);
        timer = new Timer(1000);
        timer.stop();
    }
    
    /**
     * Valid character default-nya ALPHA_NUMERIC
     */
    public NumpadInput(){
        this(ALPHA_NUMERIC, Integer.MAX_VALUE);
        timer = new Timer(1000);
        timer.stop();
    }
    
    public final static char[][] numpadChar = new char [][]{
        {' '},              // Numpad '0'
        {BACKSPACE},        // Numpad '1'
        {'a','b','c'},      // Numpad '2'
        {'d','e','f'},      // Numpad '3'
        {'g','h','i'},      // Numpad '4'
        {'j','k','l'},      // Numpad '5'
        {'m','n','o'},      // Numpad '6'
        {'p','q','r','s'},      // Numpad '7'
        {'t','u','v'},      // Numpad '8'
        {'w','x','y','z'}      // Numpad '9'
    };
    
    int charColumnIndex;
    public boolean keyPressed(int keyCode, int rawKeyCode){
        // Karakter input yang baru saja dimasukkan user
        if(isWaiting){
            // Untuk numpad 0 sampai 9..... 
            // Kalo bintang dan pagar (*,#) belum termasuk
            if(isNumeric(rawKeyCode)){
                int indexNumpadChar = rawKeyCode-'0';
                if(rawKeyCode=='1'){
                    deleteChar();
                    dontWaitAnotherChar();
                } else if(rawKeyCode=='0'){
                    addChar(' ');
                    dontWaitAnotherChar();
                }
                // Jika tombol yang sama dipencet lagi
                else if(lastInputChar == rawKeyCode){
                    if(numpadChar[indexNumpadChar].length > (charColumnIndex+1)){
                        ++charColumnIndex;
                        deleteChar();
                        addChar(numpadChar[indexNumpadChar][charColumnIndex]);
                        waitAnotherChar();
                    } else {
                        charColumnIndex = 0;
                        deleteChar();
                        addChar(numpadChar[indexNumpadChar][charColumnIndex]);
                        waitAnotherChar();
                    }
                } else {
                    charColumnIndex = 0;
                    isWaiting = true;
                    addChar(numpadChar[indexNumpadChar][charColumnIndex]);
                    waitAnotherChar();
                }
                // Kalo bintang dan pagar (*,#)
            } else if( rawKeyCode == '*' ){
                deleteChar();
                isWaiting = false;
            } else {
                validInput = false;
            }
        } else {
            // Untuk numpad 0 sampai 9..... 
            // Kalo bintang dan pagar (*,#) belum termasuk
            if(isNumeric(rawKeyCode)){
                if(rawKeyCode=='1'){
                    deleteChar();
                    dontWaitAnotherChar();
                } else if(rawKeyCode=='0'){
                    addChar(' ');
                    waitAnotherChar();
                }
                else {
                    charColumnIndex = 0;
                    int indexNumpadChar = rawKeyCode-'0';
                    addChar(numpadChar[indexNumpadChar][charColumnIndex]);
                    waitAnotherChar();
                }
            } // Kalo bintang dan pagar (*,#)
            else if( rawKeyCode == '*' ){
                deleteChar();
                isWaiting = false;
            } else {
                validInput = false;
            }
            charColumnIndex = 0;
        }
        lastInputChar = rawKeyCode;
        // Melakukan pengecekan pada ALPHA, NUMERIC, PUNCTUATION, dan BACKSPACE
        // Nilai validInput akan false jika input tidak valid
//        validInput =  checkAlpha(rawKeyCode) || 
//                        checkNumeric(rawKeyCode) || 
//                        checkPunctuation(rawKeyCode) || 
//                        checkBackspace(rawKeyCode) ||
//                        checkSpace(rawKeyCode);
        
        return validInput;
    }

    boolean isWaiting;
    
    
    public void update(long currentTime) {
        if(timer.isTicked(currentTime)){
            if(isWaiting){
                isWaiting = false;
            }
            timer.stop();
        }
    }

    private void waitAnotherChar() {
        isWaiting = true;
        timer.start();
    }
    
    private void dontWaitAnotherChar(){
        isWaiting = false;
        timer.stop();
    }
}
