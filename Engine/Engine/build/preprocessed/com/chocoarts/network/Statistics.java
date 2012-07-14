/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.network;

import com.chocoarts.Engine;
import com.chocoarts.debug.Debug;
import com.chocoarts.network.tools.Http;
import com.chocoarts.network.tools.JSON;
import java.util.Calendar;
import java.util.Date;

/**
 * Depreciated, not used anymore. Please uncomment "// DEBUG" next code
 * to make this class works again.
 * @author lieenghao
 */
public class Statistics {
    // The host name URL to which this application send statistic
    public String hostname = "";
    private Data data;
    private Engine engine;
    
    // Last start time when application once started.
    public String lastStartTime;
    
    public void init(Engine engine, String hostname){
        this.hostname = hostname;
        this.engine = engine;
        this.data = new Data();// membuat profile dengan default configuration
        
        try{
            // Mencoba membaca data profile
            this.data.readData();
            Debug.println("Menemukan data statistik pada sistem");
        }catch(Exception ex){
            Debug.println("Tidak menemukan berkas statistik");
            try{
                Debug.println("Membuat berkas statistik baru");
                // Kalau ternyata tidak ada berkas profile
                // Kita bikin baru
                this.data.writeData();
            }catch(Exception ex2){
                Debug.println("Tidak dapat membuat berkas statistik baru");
                return; // APP FAILED TO START
            }
        }
        
    }
    
    /**
     * has this handset been registered in server database ?
     * @return 
     */
    public boolean hasRegister(){
        return !data.databaseID.equals(Data.DEFAULT_ID);
    }
    
    public synchronized void sendMessage(){
        if( !hasRegister() ){
            Debug.println("Database ID bernilai kosong");
            if( register(engine.getProfile()) ){
                data.hasRegister = true;
                try {
                    data.writeData();
                    Debug.println("Berhasil registrasi statistik");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Debug.println("Tidak berhasil melakukan registrasi");
            }
        }
        
        if(hasRegister() && !data.antrianPesan.equals("")){
            replaceWildID();//if any * mark in antrianPesan, replace it with current ID
            Debug.println("Mengirimkan data statistik yang mengantri");
            sendAntrian();
            Debug.println("Selesai mengirimkan data statistik yang mengantri");
        }
        
        engine.finishLoading();
    }
    
    public void sendToServer2(){
        sendMessage();
    }
    
    public void startPlaying(){
        this.lastStartTime = getTimeStamp();
    }
    
    /**
     * Membuat timestamp dengan waktu saat ini
     * @return String yang berformat "YYYY-MM-DD dd:mm:ss"  //TODO: Cek formatnya
     */
    public String getTimeStamp(){
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String timestamp = 
                c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.DAY_OF_MONTH) + " " +
                c.get(Calendar.HOUR_OF_DAY) + ":" +c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
        return timestamp;
    }
    
    public void finishPlaying() throws Exception{
        data.tambahWaktuKeAntrian(this.lastStartTime, getTimeStamp());
        data.writeData();
    }
    
    public void submitScore(int score) throws Exception{
        // DEBUG
//        data.tambahScoreKeAntrian(score, engine.getProfile().playerName);
        data.writeData();
    }
    
    public boolean register(Profile profile){
        String message = JSON.toJSON(new String [] {
                "header", "REG",
                "content", "xx"
                // DEBUG
//                ,"name", profile.playerName
        });
        
        boolean berhasil = false;
        try{
//            System.out.println("Registrasi dengan: msg=" + message);
            berhasil = Http.post(hostname, "msg=" + message);
            if(berhasil){
                data.databaseID = Http.lastResponseString;
            }
        }catch(Exception ex){
            berhasil = false;
        }
        
        return berhasil;
    }
    
    public boolean sendFeed(String startTime, String finishTime){
        String message = JSON.toJSON(new String [] {
                "header"        , "FEED",
                "id"            , data.databaseID,
                "start_time"    , startTime,
                "finish_time"   , finishTime
        });
        
        boolean berhasil = false;
        try{
            Http.post(hostname, "msg=" + message);
            berhasil = true;
        }catch(Exception ex){
            berhasil = false;
        }
        
        return berhasil;
    }
    
    public String getJSON(){
        return "";
    }

    String lastServerResponse;
    
    public String getLastServerResponse(){
        return this.lastServerResponse;
    }
    
    private void sendAntrian() {
        try{
            if( Http.post(hostname,"msg=" +data.antrianPesan ) ){
                Debug.println("Berhasil mengirimkan data statistik");
                data.antrianPesan = "";
                try {
                    this.lastServerResponse = Http.lastResponseString;
                    data.writeData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Debug.println("Tidak berhasil mengirimkan data statistik");
            }
        }
        catch( Exception ex ){
            ex.printStackTrace(); // TODO: kalau sudah mau launch, di uncomment
        }
    }
    
    public String getID(){
        return data.databaseID;
    }

    private void replaceWildID() {
        int index = data.antrianPesan.indexOf("*");
        
        while(index!=-1){
            if(index==0){
                data.antrianPesan = data.databaseID+data.antrianPesan.substring(1);
            } else if(index==data.antrianPesan.length()-1){
                data.antrianPesan = data.antrianPesan.substring(0,data.antrianPesan.length()-1) + data.databaseID;
            } else {
                data.antrianPesan = data.antrianPesan.substring(0,index) +
                        data.databaseID +
                        data.antrianPesan.substring(index+1,data.antrianPesan.length());
            }
            index = data.antrianPesan.indexOf("*");
        }
    }
}
