/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.tools;

import java.util.Random;

/**
 * Masih salah, perlu dipikirkan ulang.....
 * Apakah butuh public static int INTEGER_PER_60 = Integer.MAX_VALUE/60; untuk
 * mendapatkan nilai random dengan batas atas 60?
 * @author lieenghao
 */
public class RandomGenerator {
    Random random;
    /**
     * upperBound excluded from possible resulting number
     * @param upperBound 
     */
    public RandomGenerator(){
        random = new Random();
        
        random.nextInt();
        // misalkan 100
    }
    
    public int nextInt(int upperBound){
        return random.nextInt()%upperBound;
    }
    
    public boolean nextBoolean(){
        return random.nextInt()%2==0;
    }
    
}
