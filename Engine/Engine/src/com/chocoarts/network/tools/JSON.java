
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.network.tools;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author lieenghao
 */
public class JSON {
    /**
     * Parameter fungsi ini harus berpasangan, misalkan mau membuat JSON untuk
     * nama = Joko, umur = 3. Maka penulisannya harus seperti:
     * JSON.toJSON( { "nama", "joko", "umur", "3" });
     * @param nameAndValue
     * @return 
     */
    public static String toJSON(String [] nameAndValue) {
        JSONObject inner = new JSONObject();
        try {
            for( int i = 0 ; i < nameAndValue.length ; i+= 2){
                inner.put(nameAndValue[i], nameAndValue[i+1]);
            }
        } catch (JSONException ex) {
                ex.printStackTrace();
        }
        return inner.toString();
    }
}
