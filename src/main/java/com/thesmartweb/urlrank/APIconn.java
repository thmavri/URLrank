/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thesmartweb.urlrank;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import javax.net.ssl.HttpsURLConnection;
/**
 *
 * @author Themis Mavridis
 */
public class APIconn {
    public HttpURLConnection httpCon;
    public HttpsURLConnection httpsCon;
    //public HttpsURLConnection[] httpsConn;

public String connect(URL link_ur) {
        try {
            httpCon = (HttpURLConnection) link_ur.openConnection();
            if (httpCon.getResponseCode() != 200) {
                String line;
                line = "fail";
                return line;
                // throw new IOException(httpCon.getResponseMessage());
            } else {
                String line;
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(httpCon.getInputStream()))) {
                    StringBuilder sb = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    line = sb.toString();
                }
                return line;
            }
        } catch (IOException ex) {
            Logger.getLogger(APIconn.class.getName()).log(Level.SEVERE, null, ex);
            String line="fail";
            return line;
        }

}

public String azureconnect(URL link_ur,String[] accKeys){
    String string_link_ur=link_ur.toString();
    String line="fail";
    if(string_link_ur.substring(23,28).equalsIgnoreCase("azure")){
        HttpsURLConnection[] httpsConn=new HttpsURLConnection[accKeys.length];
        int i=-1;
        int respp=0;
        do{
            try {
                i++;
                httpsConn[i]=(HttpsURLConnection) link_ur.openConnection();
                byte[] accountKeyBytes = Base64.encodeBase64((accKeys[i]+":"+accKeys[i]).getBytes());
                String accountKeyEnc = new String(accountKeyBytes);
                httpsConn[i].setRequestProperty("Authorization","Basic "+accountKeyEnc);
                respp=httpsConn[i].getResponseCode();
            } catch (IOException ex) {
                Logger.getLogger(APIconn.class.getName()).log(Level.SEVERE, null, ex);
                return line;
            }
        }
        while(respp!=200&&i<accKeys.length-1);
        int j=i;
        if(j>0){
            for(int k=0;k<j;k++){
                httpsConn[k].disconnect();
            }
        }
        if (respp != 200){
             if(respp!=503){
                 try {
                     String responseMessage = httpsConn[i].getResponseMessage();
                     if(responseMessage.startsWith("Insufficient")){
                         line="insufficient";
                     }
                     if(responseMessage.contains("provided")){
                         line="provided";
                     }
                 } catch (IOException ex) {
                     Logger.getLogger(APIconn.class.getName()).log(Level.SEVERE, null, ex);
                     return line;
                 }
             }
             return line;
             // throw new IOException(httpCon.getResponseMessage());
       } 
       else {
            BufferedReader rd = null;
            try {
                rd = new BufferedReader(new InputStreamReader(httpsConn[i].getInputStream()));
                StringBuilder sb = new StringBuilder();
                line="";
                while ((line = rd.readLine()) != null) {
                sb.append(line);
            }   line = sb.toString();
                rd.close();
                return line;
            } catch (IOException ex) {
                Logger.getLogger(APIconn.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if(rd!=null){
                        rd.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(APIconn.class.getName()).log(Level.SEVERE, null, ex);
                    return line;
                }
            }
        }                
    }
    return line;

}

}
