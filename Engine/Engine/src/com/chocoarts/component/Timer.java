/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.component;

/**
 * Timer class is used as its name, to do something every/once the time has
 * come. e.g: do increment every 5 second. Just use the constructor with 5000
 * as its parameter. We are using milliseconds as base unit here.
 * @author lieenghao
 */
public class Timer {
    long lastTime = -1;    // -1 berarti belum dimulai timernya
    long timeInterval;
    long deltaTime;
    boolean isActive;
    /**
     * Class untuk menghitung interval waktu (timeInterval)
     * fungsi isTicked akan return true jika timeInteval sudah dilewati
     * @param timeInterval 
     */
    public Timer(long timeInterval){
        this.timeInterval = timeInterval;
        isActive = true;
    }
    
    /**
     * fungsi isTicked akan return true jika timeInteval sudah dilewati
     * @param currentTime
     * @return 
     */
    public boolean isTicked(long currentTime){
        if(isActive){
            if(lastTime==-1){
                lastTime = currentTime;
            } else {
                deltaTime+=currentTime-lastTime;
                lastTime = currentTime;

                if(deltaTime>=timeInterval){
                    deltaTime-=timeInterval;
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    /**
     * Mereset timer
     */
    public void reset(){
        deltaTime = 0;
        lastTime = -1;
    }

    /**
     * Provide time interval for this timer. If the time interval is 4000, so
     * isTicked method will return true every 4 seconds.
     * @param i 
     */
    public void setTimeInterval(int i) {
        timeInterval = i;
        reset();
    }

    /**
     * Berbeda dengan fungsi reset, fungsi sleep ini hanya dipanggil
     * jika screen berada pada kondisi sleep. Yang fungsi sleep lakukan
     * hanya mengubah nilai lastTime menjadi -1, supaya perhitungannya
     * dimulai ulang pada saat dibangunkan kembali nanti (tidak sleep lagi)
     */
    public void sleep() {
        lastTime = -1;
    }

    /**
     * Start the timer immediately from the start.
     * Yes, it will reset the timer all over.
     */
    public void start() {
        reset();
        isActive = true;
    }
    
    /**
     * Stop the timer immediately.
     */
    public void stop(){
        reset();
        isActive = false;
    }

    /**
     * Checking whether this timer has been started or not.
     * @return 
     */
    public boolean isStarted() {
        return isActive;
    }
}
