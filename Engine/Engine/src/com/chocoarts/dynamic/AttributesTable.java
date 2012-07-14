/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.dynamic;

import com.chocoarts.debug.Debug;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author lieenghao
 */
public class AttributesTable {
    Hashtable attributesTable;
    
    public AttributesTable(){
        attributesTable = new Hashtable();
    }
    
    public Attributes getAttributes(String key){
        return (Attributes)attributesTable.get(key);
    }
    
    public void setAttributes(String key, Attributes attrib){
        attributesTable.put(key, attrib);
    }
    
    public void collectAttributes(){
        collectAttributes(getClass().getResourceAsStream("/attributes.txt"));
    }
    
    public void collectAttributes(InputStream is){
        String result = "";
        StringBuffer sb = new StringBuffer();
        try{
            int c;
            while ((c = is.read()) != -1){
                sb.append((char)c);
            }
            result = sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        int indeks = result.indexOf("\r\n");
        
        Attributes attrib = null;
        String key = "";
        
        while(indeks!=-1){
            String piece = result.substring(0, indeks);
            result = result.substring(indeks+2,result.length());
            indeks = result.indexOf("\r\n");
            
            int colonIndeks = piece.indexOf(':');
            
//            System.out.println("Result: " + result);
//            System.out.println("*****************************");
//            System.out.println("Mentahan: " + piece);
            
            if(piece.equals("")){
                if(attrib!=null){
                    Debug.println("Put attributes: " + key);
                    attributesTable.put(key, attrib);
                    attrib = null;
                    key = "";
                } else {
                    // Ignore
                }
            } else if(piece.startsWith("file-name")){
                key = piece.substring(colonIndeks+1);
                attrib = new Attributes();
//                attrib.setFilename(key);
                attrib.addLine(piece);
            } else {
                attrib.addLine(piece);
            }
        }
        if(attrib!=null){
            attributesTable.put(key, attrib);
            attrib = null;
            key = "";
        }
        try {
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Enumeration getAllAttributes(){
        return attributesTable.elements();
    }
    
    public void removeAnAttribute(String key){
        Object obj = attributesTable.remove(key);
//        System.out.println("removed? " + (obj!=null));
    }
    
    /**
     * Add an attribute with its filename as the key
     * @param attrib 
     */
    public void addAnAttribute(Attributes attrib){
        attributesTable.put(attrib.getFilename(), attrib);
    }
}
