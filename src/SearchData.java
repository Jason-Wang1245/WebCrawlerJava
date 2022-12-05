import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SearchData extends Readable{
    public List<String> getIncomingLinks(String url){
        HashSet<String> list = readHashSet("referenceLinks", "resources", url);
        if (list == null)
            return null;
        return new ArrayList<String>(list);
    }

    public List<String> getOutgoingLinks(String url){
        HashSet<String> list = readHashSet("externalLinks", "resources", url);
        if (list == null)
            return null;
        return new ArrayList<String>(list);
    }

    public double getPageRank(String url){
        return readValue("pageRank", "resources", url);
    }

    public double getTfValue(String url, String word){
        HashMap<String, Double> tfValues = readHashMap("tf", "resources", url);
        if (tfValues == null || !tfValues.containsKey(word))
            return 0;
        return tfValues.get(word);
    }

    public double getTfIdfValue(String url, String word){
        HashMap<String, Double> tfIdfValues = readHashMap("tfidf", "resources", url);
        if (tfIdfValues == null || !tfIdfValues.containsKey(word))
            return 0;
        return tfIdfValues.get(word);
    }

    public double getIdfValue(String word){
        HashMap<String, Double> idfValues = readIdfList("resources");
        if (idfValues == null || !idfValues.containsKey(word))
            return 0;
        return idfValues.get(word);
    }

    public String getTitle(String url){
        return readString("title", "resources", url);
    }
}
