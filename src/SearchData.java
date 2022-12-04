import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchData extends Readable{
    public List<String> getIncomingLinks(String url){
        return new ArrayList<String>(readList(url, 0));
    }

    public List<String> getOutgoingLinks(String url){
        return new ArrayList<String>(readList(url, 1));
    }

    public double getPageRank(String url){
        return readValue(url, "", 0);
    }

    public double getTfValue(String url, String word){
        return readValue(url, word, 1);
    }

    public double getTfIdfValue(String url, String word){
        return readValue(url, word, 2);
    }

    public double getIdfValue(String word){
        return readIdfValue(word);
    }
}
