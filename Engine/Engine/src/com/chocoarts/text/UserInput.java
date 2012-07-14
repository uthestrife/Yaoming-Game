/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.text;

/**
 *
 * @author lieenghao
 */
public abstract class UserInput {
    // Mode-mode karakter yang valid untuk kelas ini
    public static final int 
            ALPHA = 1,NUMERIC = 1<<1,PUNCTUATION = 1<<2,
            ALPHA_NUMERIC = ALPHA|NUMERIC,
            ALL = ALPHA|NUMERIC|PUNCTUATION;
    
    public static final int BACKSPACE = 8, SPACE = 32, ENTER = 10;
    
    private final int validChars;
    private final int maxCharacters;
    
    private String currentStr = "";
    private int charsCounter;
    
    // Debugging purpose
    protected int lastInputChar;
    
    // Untuk memberikan feedback jika masukannya tidak termasuk validChars
    protected boolean validInput;
    
    public UserInput( int validChars, int maxCharacters){
        this.validChars = validChars;
        this.maxCharacters = maxCharacters;
    }
    
    /**
     * Valid character default-nya ALPHA_NUMERIC
     */
    public UserInput(){
        this(ALPHA_NUMERIC, Integer.MAX_VALUE);
    }
    
    public void setText(String text){
        currentStr = text;
        charsCounter = currentStr.length();
    }
    
    abstract public void update(long currentTime);
    
    /**
     * Fungsi keyPressed menambahkan satu karakter baru jika input yang dimasukan
     * merupakan input yang valid. return false jika inputnya tidak valid.
     * @param keyCode
     * @param rawKeyCode
     * @return 
     */
    public abstract boolean keyPressed(int keyCode, int rawKeyCode);
//    {
//        // Karakter input yang baru saja dimasukkan user
//        lastInputChar = rawKeyCode;
//        
//        // Melakukan pengecekan pada ALPHA, NUMERIC, PUNCTUATION, dan BACKSPACE
//        // Nilai validInput akan false jika input tidak valid
//        validInput =  checkAlpha(rawKeyCode) || 
//                        checkNumeric(rawKeyCode) || 
//                        checkPunctuation(rawKeyCode) || 
//                        checkBackspace(rawKeyCode) ||
//                        checkSpace(rawKeyCode);
//        
//        return validInput;
//    }

    public static boolean isAlpha(int rawKeyCode) {
        return (rawKeyCode>='A'&&rawKeyCode<='Z')||
                (rawKeyCode>='a'&&rawKeyCode<='z');
    }
    
    public static boolean isNumeric(int rawKeyCode) {
        return (rawKeyCode>='0'&&rawKeyCode<='9');
    }

    protected boolean addChar(int rawKeyCode){
        if(charsCounter!=maxCharacters){
            currentStr += (char)rawKeyCode;
            ++charsCounter;
            return true;
        } else {
            return false;
        }
    }
    
    protected boolean deleteChar(){
        if(charsCounter!=0){
            currentStr = currentStr.substring(0, currentStr.length()-1);
            --charsCounter;
            return true;
        } else {
            return false;
        }
    }
    
    
    /**
     * Fungsi yang mengembalikan rawKeyCode terakhir yang diterima oleh
     * objek QwertyInput ini
     * @return last rawKeyCode
     */
    public int getLastInput(){
        return lastInputChar;
    }
    
    /**
     * Fungsi yang mengembalikan String yang diterima oleh objek ini
     * selama hidupnya
     * @return current String
     */
    public String getInputString(){
        return currentStr;
    }
    
    /**
     * Alpha checker, return true if rawKeyCode is an alpha, false otherwise.
     * @param rawKeyCode
     * @return 
     */
    protected boolean checkAlpha(int rawKeyCode) {
        if(isAlpha(rawKeyCode)){
            // Jika menerima ALPHA
            if((validChars&ALPHA)!=0){
                // Tambahkan satu karakter ke currentStr
                return addChar(rawKeyCode);
            } else {
                // Tandai kalau input terakhir itu invalid
                return false;
            }
        } else {
            // Bukan alpha, return false supaya pengecekan dapat dilanjutkan
            // pada tipe lain (NUMERIC atau PUNCTUATION atau BACKSPACE)
            return false;
        }
    }

    protected boolean checkNumeric(int rawKeyCode) {
        // jika rawKeyCode termasuk numerik
        if(isNumeric(rawKeyCode)){
            // Jika menerima numerik
            if((validChars&NUMERIC)!=0){
                // Tambahkan satu karakter ke currentStr
                return addChar(rawKeyCode);
            } else {
                // Tandai kalau input terakhir itu invalid
                return false;
            }
        } else {
            // Bukan numerik, return false supaya pengecekan dapat dilanjutkan
            // pada tipe lain (ALPHA atau PUNCTUATION atau BACKSPACE)
            return false;
        }
    }

    protected boolean checkPunctuation(int rawKeyCode) {
        // jika rawKeyCode termasuk punctuation
        if(isPunctuation(rawKeyCode)){
            // Jika menerima punctuation
            if((validChars&PUNCTUATION)!=0){
                // Tambahkan satu karakter ke currentStr
                return addChar(rawKeyCode);
            } else {
                // Tandai kalau input terakhir itu invalid
                return false;
            }
        } else {
            // Bukan numerik, return false supaya pengecekan dapat dilanjutkan
            // pada tipe lain (ALPHA atau NUMERIC atau BACKSPACE)
            return false;
        }
    }
    
    // Menggunakan karakter-karakter ini untuk pengecekan isPunctuation
    private static final String PUNCTUATION_CHARS = "!@#$%^&*(){};:'\",.<>/?";
    
    // TODO: cari cara cek punctuation yang bagus
    protected boolean isPunctuation(int rawKeyCode) {
        return PUNCTUATION_CHARS.indexOf(rawKeyCode)!=-1;
    }

    protected boolean checkBackspace(int rawKeyCode) {
        // Jika input adalah Backspace
        // Cek panjang input yang telah disimpan
        if(rawKeyCode==BACKSPACE){
            // Kalau masih ada isinya, kurangi panjangnya satu.
            return deleteChar();
        } else {
            // Bukan numerik, return false supaya pengecekan dapat dilanjutkan
            // pada tipe lain (ALPHA atau NUMERIC atau PUNCTUATION)
            return false;
        }
    }

    protected boolean checkSpace(int rawKeyCode) {
        // Jika input adalah SPACE
        if(rawKeyCode==SPACE){
            // Tambahin deh SPACE-nya ke currentStr
            return addChar(rawKeyCode);
        } else {
            // Kalau bukan space, return false supaya pengecekan dapat dilanjutkan
            // pada tipe lain (ALPHA atau NUMERIC atau PUNCTUATION atau BACKSPACE)
            return false;
        }
    }
}
