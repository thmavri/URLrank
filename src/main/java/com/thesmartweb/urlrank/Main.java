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
        String domain="nba.com";
        String query="nba";
        int results_number=50;
        GoogleResults google=new GoogleResults();
        String[] gres=google.Get(query, results_number);
        BingResults bing=new BingResults();
        String[] bres=bing.Get(query, results_number);
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
