package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SearchData extends Readable{
    public List<String> getIncomingLinks(String url){
        HashSet<String> list = readHashSet("referenceLinks", "resources", url);
        // if the url was not found or any reading errors occurred, null is returned
        if (list == null)
            return null;
        return new ArrayList<String>(list);
    }

    public List<String> getOutgoingLinks(String url){
        HashSet<String> list = readHashSet("externalLinks", "resources", url);
        // if the url was not found or any reading errors occurred, null is returned
        if (list == null)
            return null;
        return new ArrayList<String>(list);
    }

    public double getPageRank(String url){
        // readValue automatically returns -1 when the url was not found
        return readValue("pageRank", "resources", url);
    }

    public double getTfValue(String url, String word){
        HashMap<String, Double> tfValues = readHashMap("tf", "resources", url);
        // if the url was invalid or the word does not exist within the map of tf values, 0 is returned
        if (tfValues == null || !tfValues.containsKey(word))
            return 0;
        return tfValues.get(word);
    }

    public double getTfIdfValue(String url, String word){
        HashMap<String, Double> tfIdfValues = readHashMap("tfidf", "resources", url);
        // if the url was invalid or the word does not exist within the map of tfidf values, 0 is returned
        if (tfIdfValues == null || !tfIdfValues.containsKey(word))
            return 0;
        return tfIdfValues.get(word);
    }

    public double getIdfValue(String word){
        HashMap<String, Double> idfValues = readIdfList("resources");
        // if the word was not found throughout the entire crawl, 0 is returned
        if (idfValues == null || !idfValues.containsKey(word))
            return 0;
        return idfValues.get(word);
    }

    public String getTitle(String url){
        return readString("title", "resources", url);
    }
}
