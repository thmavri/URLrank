/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thesmartweb.urlrank;

/**
 *
 * @author themis
 */
public class Main {
    public static void main(String[] args) {
        String domain="nba.com";//set your domain
        String[] queries=new String[2];
        queries[0]="nba";//set your queries
        queries[1]="basketball";
        //-------------------------------
        int results_number=50;//results number of SERPs (max is 50)
        //----------Google API keys------------
        String[] apikeys = new String[2];
        String[] cxs = new String[2];
        apikeys[0]="your api key here";
        apikeys[1]="your api key here";
        cxs[0]="your cxs key here";
        cxs[1]="your cxs key here";
        //-----------Bing API keys--------------
        String[] accKeys=new String[2];
        accKeys[0]="your bing api key here";
        accKeys[1]="your bing api key here";
        //-----------------------------------------
        GoogleResults google=new GoogleResults();
        BingResults bing=new BingResults();
        //----------get the results for every query-------
        for(String query:queries){
            String[] gres=google.Get(query, results_number, apikeys, cxs);//get google results
            String[] bres=bing.Get(query, results_number, accKeys);//get bing results
            for (int i=0;i<gres.length;i++){
                if(gres[i]!=null){
                    if(gres[i].contains(domain)){
                        System.out.println("Google Rank for "+query+":"+(i+1));
                        break;
                    }
                }
            }
            for (int i=0;i<bres.length;i++){
                if(bres[i]!=null){
                    if(bres[i].contains(domain)){
                        System.out.println("Bing Rank for "+query+":"+(i+1));
                        break;
                    }
                }
            }
        }
    }
}
