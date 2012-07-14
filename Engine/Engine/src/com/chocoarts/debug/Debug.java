/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.debug;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author lieenghao
 */
public class Debug {
    public static Runtime runtime = Runtime.getRuntime();
    private static long startFreeMemory;
    private static long free;
    private static long temp;
    private static String debugString;
    public static boolean DEBUGGING;
    
    public static void initDebug(){
        startFreeMemory = free = runtime.freeMemory();
        debugString = "Starting App... Free Memory: " + toKb(free) + " kb";
        println(debugString);
    }
    
    public static void println(String text){
        if(DEBUGGING){
            System.out.println(text);
        }
    }
    
    public static void printFree(String name){
        if(DEBUGGING){
            if(free == 0){
                println("Anda lupa panggil Debug.initDebug()!");
                println("Memanggil Debug.initDebug() ...");
                initDebug();
            }
            temp = free - runtime.freeMemory();
            free = runtime.freeMemory();
            debugString = name + ": (" + toKb(temp) + "/" + toKb(free) + " kb)";
            println(debugString);
        }
    }
    
    public static void printTotalUsed(){
        debugString = "Total used memory: " + (startFreeMemory-runtime.freeMemory());
        println(debugString);
    }
    
    private static final long toKb(long value){
        return value/(1024*8);
    }

    public static void drawDebugString(Graphics g) {
        g.setColor(0xFFFFFF);
        g.drawString(debugString + "", 0, 200, Graphics.TOP | Graphics.LEFT );
    }

    public static void setDebugString(String message) {
        debugString = message;
    }
}
