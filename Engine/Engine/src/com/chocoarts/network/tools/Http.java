/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chocoarts.network.tools;

import com.chocoarts.debug.Debug;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author lieenghao
 */
public class Http {
    public static String lastResponseString = "";
    
    public static boolean post(String host, String message) throws Exception {
        String url = host;
        byte[] data = null;
        InputStream istrm = null;

        HttpConnection http = (HttpConnection)Connector.open(url);
        http.setRequestMethod(HttpConnection.POST);
        
        // This allows a JSP page to process the parameters correctly
        // This format (or content type) is the same as when forms are
        // submitted from a web page.
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        http.setRequestProperty("Content-Type", "text/xml; charset=" + "UTF-8");
        
        // You may want to indicate to the server side (eg servlet) that 
        // this request is coming from a specific application.  This would
        // allow generation of appropriate format for the content.
        http.setRequestProperty("User-Agent", "HttpMidlet/0.2");

        // You can send any custom property as part of the HTTP header
        // This would matter for the "infrastructure" code, as opposed
        // to "body parameters" that would matter to the application
        // (eg, user=marco)
//        http.setRequestProperty("Custom-Property", 
//          "MyCustomProperty/1.0; AnotherProperty/debug_0.1");

        // You MUST create the body of the post BEFORE actually opening
        // the stream, otherwise you won't be able to set the length of
        // the request (see next step).
        // In this example, I am sending coordinates to a mapping server
        String msg = message;

        // THIS is important! without it a JSP won't process the POST data
        // it would also appear that CASE MATTERS (using "Content-Length" -note 
        // the capital 'L'- caused my servlet to return -1 from the 
        //    HttpServletRequest.getContentLenght() method
//        http.setRequestProperty("Content-length", ""+msg.getBytes().length);

        // After this point, any call to http.setRequestProperty() will
        // cause an IOException
        OutputStream out = http.openOutputStream();
        out.write(msg.getBytes());
//        out.flush();

        boolean berhasil = false;
        
        if (http.getResponseCode() == HttpConnection.HTTP_OK) {
            int len = (int)http.getLength();
            istrm = http.openInputStream();
            if (istrm == null) {
              throw new IOException("Cannot open HTTP InputStream, aborting");
            }
            if (len != -1) {
              data = new byte[len];
              int bytesRead = istrm.read(data);
            } else {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                  int ch;
                  int count = 0;

                  // This is obviously not particularly efficient
                  // You may want to use a byte[] buffer to read bytes in chunks
                  while ((ch = istrm.read()) != -1) {
                    bo.write(ch);
                    count++;
                  }
                  data = bo.toByteArray();
                  bo.close();
            }
            lastResponseString = new String(data);
            Debug.println("Server's Responses: " + lastResponseString);
            berhasil = true;
        } else {
            lastResponseString = "Response: "+http.getResponseCode()+", "+http.getResponseMessage() ;
            
            berhasil = false;
        }
        // This is critical, unless you close the HTTP connection, the application
        // will either be consuming needlessly resources or, even worse, sending
        // 'keep-alive' data, causing your user to foot unwanted bills!
        http.close();
        
        return berhasil;
    }
    
}
