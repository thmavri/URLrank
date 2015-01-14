/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.thesmartweb.urlrank;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import org.json.simple.parser.*;
import java.util.*;
import org.json.simple.JSONArray;
/**
 *
 * @author Themis Mavridis
 *///
//"http://api.search.live.net/json.aspx?Appid=<7FC8F5CEC23A5A4B418E83457E2DC00DC189DAF8>&query="+quer+"&sources=web&web.count=4
public class BingResults {
public String[] Get(String query,int bing_results_number, String[] acckeys){
    String chk="ok";
    String[] links=new String[bing_results_number];
    try {
            APIconn apicon = new APIconn();    
            String line="fail";
            query=query.replace("+", "%20");
            URL azure_url=new URL("https://api.datamarket.azure.com/Bing/SearchWeb/v1/Web?Query=%27"+query+"%27&$top="+bing_results_number+"&$format=JSON");
            line=apicon.azureconnect(azure_url,acckeys);
            if((!line.equalsIgnoreCase("fail"))&&(!line.equalsIgnoreCase("insufficient"))&&(!line.equalsIgnoreCase("provided"))){
                 
                //initialize JSONparsing
                JSONparsing gg = new JSONparsing(bing_results_number);
                //get the links in an array
                links = gg.BingAzureJsonParsing(line, bing_results_number);
            }
            return links;
    } 
    catch (IOException ex) {
        Logger.getLogger(BingResults.class.getName()).log(Level.SEVERE, null, ex);
        System.out.print("\n*********Failure in Bing results*********\n");
            return links;
    }
          
        
}

}
