/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thesmartweb.urlrank;
/**
 *
 * @author Themis Mavridis
 */
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;
import java.util.Iterator;
public class JSONparsing {
    public static String[] links;
    public static String[] links_yahoo_bing;
    public static Map.Entry[] entries_yahoo_bing;
    public static int triple_cnt;
    public static int ent_query_cnt=0;
    public static int cat_query_cnt=0;
    
    JSONparsing(){links=new String[10];}
    JSONparsing(int results_number){links_yahoo_bing=new String[results_number];}

    public String[] GoogleJsonParsing(String input)  {
        try {
            //Create a parser
            JSONParser parser = new JSONParser();
            //Create a map
            JSONObject json = (JSONObject) parser.parse(input);         
            //Get a set of the entries
            Set set = json.entrySet();
            //Create an iterator
            Iterator iterator = set.iterator();
            //Find the entry that contain the part of JSON that contains the link
            int i=0;
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                if(entry.getKey().toString().equalsIgnoreCase("items")){
                    JSONArray jsonarray = (JSONArray) entry.getValue();
                    //find the key=link entry which contains the link
                    Iterator iterator_jsonarray= jsonarray.iterator();
                    while(iterator_jsonarray.hasNext()){
                        JSONObject next = (JSONObject) iterator_jsonarray.next();
                        links[i] = next.get("link").toString();
                        i++;
                    }
                }
            }
            return links;
        } catch (ParseException ex) {
            Logger.getLogger(JSONparsing.class.getName()).log(Level.SEVERE, null, ex);
            return links;
        }
    }


public String[] BingAzureJsonParsing(String input,int bing_result_number) {
        try {
            //Create a parser
            JSONParser parser = new JSONParser();
            //Create the map
            JSONObject jsonmap = (JSONObject) parser.parse(input);
            // Get a set of the entries
            Set set = jsonmap.entrySet();
            Iterator iterator=set.iterator();
            int i=0;
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                if(entry.getKey().toString().equalsIgnoreCase("d")){
                    JSONObject jsonobject=(JSONObject) entry.getValue();
                    JSONArray jsonarray = (JSONArray) jsonobject.get("results");
                    Iterator jsonarrayiterator=jsonarray.listIterator();
                    while(jsonarrayiterator.hasNext()){
                        JSONObject linkobject= (JSONObject) jsonarrayiterator.next();
                        links_yahoo_bing[i]=linkobject.get("Url").toString();
                        i++;
                    }
                }
            }
            return links_yahoo_bing;
        }
        catch (ParseException ex) {
            Logger.getLogger(JSONparsing.class.getName()).log(Level.SEVERE, null, ex);
            return links_yahoo_bing;
        }
            
}



}



