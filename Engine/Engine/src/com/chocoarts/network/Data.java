/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.network;

import com.chocoarts.network.tools.JSON;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/**
 *
 * @author lieenghao
 */
public class Data {
    private static final String APP_STRING = "KABOSTATS";
    public static final String DEFAULT_ID = "*";
    public int recordID;
    public boolean hasRegister;
    public String databaseID;
    public String antrianPesan = "";
    
    /**
     * Default data setting
     */
    public Data(){
        databaseID = DEFAULT_ID;
        antrianPesan = "";
        hasRegister = false;
    }
    
    private void setData(byte[] data) throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(in);
        
        // Isi setiap field kita
        this.hasRegister = dis.readBoolean();
        this.databaseID = dis.readUTF();
        this.antrianPesan = dis.readUTF();
        
        // Setelah baca, harus ditutup ya
        dis.close();
        in.close();
    }
    
    /**
     * Fungsi untuk membaca data yang ada di handphone user
     * @return
     * @throws Exception 
     */
    
    public void readData() throws Exception{
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
    
    public void writeData() throws Exception{
        RecordStore rs = RecordStore.openRecordStore(APP_STRING,true);
        
        byte [] profileBytes = getBytes();
        
        try{
            // Jika tidak ada save-an sebelumnya
            rs.setRecord(this.recordID, profileBytes, 0, profileBytes.length);
        } catch( InvalidRecordIDException ex ){
            // Kita buat ajah save-an yang baru
            this.recordID = rs.addRecord(profileBytes, 0, profileBytes.length);
        }
        
        // Kalo udahan baca, harus ditutup
        rs.closeRecordStore();
    }

    /**
     * Menuliskan variable-variable pada data menjadi array of bytes
     * @return arrayOfBytes-nya
     * @throws Exception 
     */
    private byte[] getBytes() throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        // Menuliskan satu persatu variablenya
        dos.writeBoolean(this.hasRegister);
        dos.writeUTF(this.databaseID);
        dos.writeUTF(this.antrianPesan);
//        System.out.println("Menuliskan pada sistem: hasRegister("+ hasRegister + "), databaseID("+ databaseID + "),"
//                + "antrianPesan(" + antrianPesan + ")" );
        byte [] result = bos.toByteArray();
        
        dos.flush();
        dos.close();
        bos.close();
        
        return result;
    }
    
    public void tambahWaktuKeAntrian(String startTime, String finishTime){
        String message = JSON.toJSON(new String [] {
                "header"        , "FEED",
                "id"            , databaseID,
                "start_time"    , startTime,
                "finish_time"   , finishTime
        });
        
        if(!antrianPesan.equals("")){
            antrianPesan += "#" + message;
        } else {
            antrianPesan = message;
        }
    }
    
    public void tambahScoreKeAntrian(int score, String playerName){
        String message = JSON.toJSON(new String [] {
                "header"        , "SUBMIT_SCORE",
                "id"            , databaseID,
                "player_name"   , playerName,
                "score"    , score +""
        });
        
        if(!antrianPesan.equals("")){
            antrianPesan += "#" + message;
        } else {
            antrianPesan = message;
        }
    }
    
    public String [] splitAntrian(){
        Vector str = new Vector();
        
        int index = antrianPesan.indexOf('#');
        while( index != -1 ){
            str.addElement(antrianPesan.substring(0, index));
            antrianPesan = antrianPesan.substring(index+1);
            index = antrianPesan.indexOf('#');
        }
        
        // Ingat bahwa banyaknya message = jumlah kres (#) ditambah satu
        String [] result = new String [str.size() + 1];
        
        for( int i = 0 ; i < str.size() ; i++ ){
            result[i] = str.elementAt(i).toString();
        }
        
        result[result.length-1] = antrianPesan;
        
        return result;
    }

    public void antriUntukRegistrasi() {
        String message = JSON.toJSON(new String [] {
                "header", "REG",
                "content", "xx"
        });
        antrianPesan = message;
    }
    
}
