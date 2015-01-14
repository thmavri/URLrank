/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * @author Themis Mavridis
 */

package com.thesmartweb.urlrank;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import java.util.*;

//keys for the google API
//AIzaSyCGIdV0fatLZw_ltq7WdkiPfdv0t62_3C8&cx=004407321776517455750:p9yjzmshjac
//AIzaSyBdafvgCrExUWDdR6sr57Rl_x_FojxF8Dk&cx=004407321776517455750:p9yjzmshjac
//
/**
 *
 * @author Themis Mavridis
 */
public class GoogleResults {
    public String[] Get(String quer,int google_results_number, String[] apikeys, String[] cxs){
        //counter is set
        int counter_limit=google_results_number+1;
        String chk="fail";
        String[] links_google_total=new String[google_results_number];//it contains all the links of the google, because the google api allows to get only 10 results each time
        int flag=0;
        APIconn apicon = new APIconn();
        for (int counter=1;counter<counter_limit;counter+=10){
            String[] links=new String[10];//10 is the default number that Google Search API returns
            String[] keys=new String[36];//keys is the combination of cxs and apikeys which are parameters for performing queries to Google Search API
            keys=GetKeys(apikeys,cxs);
            int i=0;
            int flag_key=0;
            String key="";
            String line="";
            //-------------after this loop line will include the JSON response of the custom search api of google (we try with the different keys, due to account limitations)
            while(flag_key==0&&i<(cxs.length*apikeys.length)){
                key=keys[i];
                i++;
                URL link_ur = null;
                //-------we form the URL-----
                try {
                    link_ur = new URL("https://www.googleapis.com/customsearch/v1?key="+key+"&q=" + quer + "&alt=json" + "&start=" + counter);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GoogleResults.class.getName()).log(Level.SEVERE, null, ex);
                    return links_google_total;
                }
                //------we attempt to connect to Google custom search API
                line = apicon.connect(link_ur);
                if(!line.equalsIgnoreCase("fail")){
                    flag_key=1;
                    //initialize JSONparsing that parses the json and gets the links
                    JSONparsing gg = new JSONparsing();
                    //get the links in an array
                    links = gg.GoogleJsonParsing(line);
                    //insert the links from each loop to the array with all the google links
                    for(int jj=0;jj<links.length;jj++){
                        links_google_total[jj+counter-1]=links[jj];
                    }
                }
            }
        }
        return links_google_total;   
    }
 
    
    
    public String[] GetKeys(String[] apikeys,String[] cxs){
                String[] keys=new String[36];
                                
                int k=0;
                for(int i=0;i<apikeys.length;i++){
                    for(int j=0;j<cxs.length;j++){
                        keys[k]=apikeys[i]+"&cx="+cxs[j];
                        k++;
                    }
                }
                return keys;
    }
}
