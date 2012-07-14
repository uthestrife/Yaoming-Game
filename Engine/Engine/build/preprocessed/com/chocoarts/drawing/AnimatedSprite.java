/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.drawing;

import com.chocoarts.component.Timer;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *  Kelas AnimatedSprite berguna untuk menampilkan sprite yang beranimasi.
 *  Terdapat 2 fungsi utama yang harus dipanggil, update dan paint.
 * @author lieenghao
 */
public class AnimatedSprite extends ChocoSprite{
    // Konstanta untuk jumlah berapa kali beranimasi
    public static final long ANIMATE_FOREVER = -1;
    
    // Counter dan Count untuk berapa kali beranimasi
    long animateCounter, animateCount;
    
    // Timer untuk menentukan kapan harus mengganti frame
    Timer timer;
    
    // Untuk animated sprite dengan looping yang aneh
    // Misalkan index frame animasinya: 0, 1, 2, 3, 4, 5, 
    //                                           3, 4, 5,
    //                                           3, 4, 5 ... dst
    // Maka startIndex-nya = 3.
    // startIndex tidak mempengaruhi index frame pertama kali.
    int startIndex;
    // Index yang digunakan untuk mengiterasi script yang dimasukkan programmer
    int scriptIndex;
    // Script yang berisikan index-index frame yang akan dilihat sebagai satu
    // kali animasi
    int [] scriptAnimation;
    
    // Apakah AnimatedSprite ini sedang menggunakan script
    boolean isUsingScript;
    // Boolean apakah sedang beranimasi
    private boolean isAnimate;
    
    /**
     * Membuat objek AnimatedSprite.
     * @param image yang didapat dari fungsi Image.createImage
     * @param frameWidth lebar dari setiap frame yang ada pada image masukan
     * @param frameHeight tinggi dari setiap frame yang ada pada image masukan
     * @param timePerFrame waktu tampil untuk setiap frame yang ada
     * @param animateCount berapa kali AnimatedSprite ini melakukan 1 kali animasi penuh
     */
    public AnimatedSprite(Image image, int frameWidth, int frameHeight, long timePerFrame, long animateCount){
        super(image, frameWidth, frameHeight);
        this.animateCount = animateCount;
        this.animateCounter = animateCount;
        timer = new Timer(timePerFrame);
        isAnimate = true;
    }
    
    /**
     * Membuat objek AnimatedSprite.
     * @param image yang didapat dari fungsi Image.createImage
     * @param frameWidth lebar dari setiap frame yang ada pada image masukan
     * @param frameHeight tinggi dari setiap frame yang ada pada image masukan
     * @param timePerFrame waktu tampil untuk setiap frame yang ada
     */
    public AnimatedSprite(Image image, int frameWidth, int frameHeight, long timePerFrame){
        this(image,frameWidth,frameHeight,timePerFrame,ANIMATE_FOREVER);
    }
    
    /**
     * Membuat objek AnimatedSprite.
     * @param image yang didapat dari fungsi Image.createImage
     * @param frameWidth lebar dari setiap frame yang ada pada image masukan
     * @param frameHeight tinggi dari setiap frame yang ada pada image masukan
     * @param timePerFrame waktu tampil untuk setiap frame yang ada
     * @param startIndex frame index untuk animasi pada kali ke 2 dan seterusnya
     */
    public AnimatedSprite(Image image, int frameWidth, int frameHeight, long timePerFrame, long animateCount, int startIndex){
        this(image,frameWidth,frameHeight,timePerFrame,animateCount);
        this.startIndex = startIndex;
    }
    
    /**
     * Fungsi utama untuk meng-update frame yang ditampilkan.
     * @param currentTime berdasarkan nilai System.currentTimeMillis() saat ini.
     */
    public void update(long currentTime){
        if(isAnimate && timer.isTicked(currentTime)){
            if(isUsingScript){
                updateUsingScript();
            } else {
                updateShowedFrame();
            }
        }
    }

    /**
     * Fungsi untuk memulai animasi dari AnimatedSprite ini.
     * Fungsi update perlu dipanggil untuk mengupdate frame-frame yang 
     * ditampilkan.
     * @param isAnimate
     */
    public void play(){
        isAnimate = true;
        if(isUsingScript){
            scriptIndex = 0;
        }
        reset();
    }
    
    /**
     * Me-reset kondisi dari animatedSprite ini seperti belum pernah di play 
     * sebelumnya.
     */
    public void reset(){
        animateCounter = animateCount;
        if(isUsingScript){
            this.setFrame(scriptAnimation[scriptIndex]);
        } else {
            this.setFrame(0);
        }
        timer.reset();
    }

    /**
     * Waktu untuk setiap frame animasinya.
     * @param i 
     */
    public void setTimePerFrame(int i) {
        timer.setTimeInterval(i);
    }

    /**
     * Menghentikan animasi dari objek animatedSprite ini.
     */
    public void stop() {
        isAnimate = false;
        reset();
    }

    /**
     * Fungsi sleep yang harus dipanggil, jika device dalam kondisi sleep.
     */
    public void sleep() {
        timer.sleep();
    }

    /**
     * Fungsi untuk menentukan berapa kali animasinya diputar.
     * @param jumlahPutaran 
     */
    public void setAnimateCount(int i) {
        this.animateCount = i;
        reset();
        isAnimate = true;
    }

    /**
     * Apakah animatedSprite ini sedang beranimasi?
     * @return true jika sedang beranimasi.
     */
    public boolean isAnimate() {
        return this.isAnimate;
    }
    
    /** Mengubah frame sequence suatu animated sprite
     * Merupakan animated sprite yang urutan framenya arbitrary
     * tergantung dari script yang diberikan,
     * Membutuhkan parameter array of integer yang berisikan
     * index frame yang harus dimainkan untuk satu kali putaran
     * */
    public void setFrameSequence(int [] arr){
        this.isUsingScript = true;
        this.scriptAnimation = arr;
    }

    /**
     * Fungsi update standard yang tidak menggunakan script untuk animasinya, 
     * sebaliknya fungsi updateUsingScript menggunakan script untuk menentukan
     * frame animasi berikutnya. Update standard menggambar frame pada animated
     * sprite secara urut dari index 0 sampai n-1 dimana n adalah jumlah frame
     * yang ada pada animatedSprite
     */
    private void updateShowedFrame() {
        int frameIndex = this.getFrame() + 1;       
        if(frameIndex == this.getFrameSequenceLength() ){// jika sudah selesai satu sequence frame
            if( animateCounter!=ANIMATE_FOREVER && // dan bukan infinite loop
                    --animateCounter==0){ // dan jumlah loop habis)
                isAnimate = false;  // Selesai animated    
                timer.reset();
            } else {
                this.setFrame(startIndex);
            }
        } else {
            this.nextFrame();   // Ganti gambar ke frame berikutnya
        }
    }
    
    /*
     * Fungsi updateUsingScript akan dipanggil jika fungsi setFrameSequence
     * pernah dipanggil sebelumnya. Fungsi ini meng-update frame yang akan digambar
     * sesuai dengan script yang dimasukkan pada fungsi setFrameSequence.
     */
    private void updateUsingScript() {
        int scriptNextIndex = scriptIndex + 1;
        if(scriptNextIndex == scriptAnimation.length ){// jika sudah selesai satu sequence frame
            if( animateCounter!=ANIMATE_FOREVER && // dan bukan infinite loop
                    --animateCounter==0){ // dan jumlah loop habis)
                isAnimate = false;  // Selesai animated
                timer.reset();
            } else {
                scriptIndex = 0;
                this.setFrame(scriptAnimation[scriptIndex]);
            }
        } else {
            // Ganti gambar ke frame berikutnya sesuai script
            scriptIndex = scriptNextIndex;
            this.setFrame(scriptAnimation[scriptIndex]);
        }
    }

    public void resetTimer() {
        timer.reset();
    }

    public Timer getTimer() {
        return timer;
    }
}
