/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.network;

import com.chocoarts.debug.Debug;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
/**
 *
 * @author lieenghao
 */
abstract public class Profile{
    private String APP_STRING;
    
    public static final int JERSEY_DEFAULT = 0, JERSEY_AWAY = 1, JERSEY_HOME = 2;
    public static final int BLACK_SHOES = 0, WHITE_SHOES = 1, RED_SHOES = 2;
    
    // PERSISTENT FIELD
    int recordID;
    
    /**
     * Default profile setting
     */
    public Profile(){
        reset();
    }
    
    public void initProfile(String appRMSString){
        this.APP_STRING = appRMSString;
    }
    
    private void setData(byte[] data) throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(in);
        
        // Isi setiap field kita
        readVariables(dis);
        
        // Setelah baca, harus ditutup ya
        dis.close();
        in.close();
    }
    
    /**
     * Fungsi untuk membaca profile yang ada di handphone user
     * @return
     * @throws Exception 
     */
    
    public void readProfile()throws Exception{
        RecordStore rs = RecordStore.openRecordStore(APP_STRING, false);
        
        // buat record enumeration untuk SELECT semua record yang ada
        RecordEnumeration re = rs.enumerateRecords(null, null, true);
        
        // Pada kenyataannya kita cuman pakai satu record ...
        this.recordID = re.nextRecordId();
        
        // Setelah dapat ID-nya, kita baca pake getRecord
        byte[] data = rs.getRecord(this.recordID); 
        
        // Kalo udahan baca, harus ditutup
        rs.closeRecordStore();
        
        setData(data);
    }
    
    public void writeProfile() throws Exception{
        RecordStore rs = RecordStore.openRecordStore(APP_STRING,true);
        
        byte [] profileBytes = getBytes();
        
        try{
            // Jika tidak ada save an sebelumnya
            rs.setRecord(this.recordID, profileBytes, 0, profileBytes.length);
            
            Debug.println("Meng-update nama di handset");
        } catch( InvalidRecordIDException ex ){
            // Save yang baru
            this.recordID = rs.addRecord(profileBytes, 0, profileBytes.length);
            
            Debug.println("Buat nama yang baru");
        }
        
        // Kalo udahan baca, harus ditutup
        rs.closeRecordStore();
    }

    /**
     * Menuliskan variable-variable pada profile menjadi array of bytes
     * @return arrayOfBytes-nya
     * @throws Exception 
     */
    private byte[] getBytes() throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        writeVariables(dos);
        
        byte [] result = bos.toByteArray();
        
        dos.flush();
        dos.close();
        bos.close();
        
        return result;
    }

    public abstract void reset();

    public abstract void writeVariables(DataOutputStream dos) throws Exception;

    public abstract void readVariables(DataInputStream dis) throws Exception;
}
