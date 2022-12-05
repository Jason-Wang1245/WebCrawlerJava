package model;

import java.util.HashMap;
import java.util.HashSet;

public class PageData {
    private HashSet<String> referenceLinks;
    private HashSet<String> outgoingLinks;
    private HashMap<String, Double> tfIdf;
    private HashMap<String, Double> tf;
    private HashMap<String, Integer> data;
    private double pageRank;
    private String title;

    // constructor
    public PageData(){
        referenceLinks = new HashSet<String>();
        outgoingLinks = new HashSet<String>();
        tfIdf = new HashMap<String, Double>();
        tf = new HashMap<String, Double>();
        data = new HashMap<String, Integer>();
        pageRank = 0;
        title = "";
    }

    // setters
    public void setTitle(String title){
        this.title = title;
    }

    // other methods
    public void addWord(String word){
        if (data.containsKey(word))
            data.put(word, data.get(word) + 1);
        else
            data.put(word, 1);
    }

}
