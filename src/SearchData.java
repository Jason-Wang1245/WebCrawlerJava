import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchData extends Readable{
    public List<String> getIncomingLinks(String url){
        HashSet<String> list = readList(url, 0);
        if (list == null)
            return null;
        return new ArrayList<String>(list);
    }

    public List<String> getOutgoingLinks(String url){
        HashSet<String> list = readList(url, 1);
        if (list == null)
            return null;
        return new ArrayList<String>(list);
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
        if (readIdfList().get(word) == null)
            return 0;
        return readIdfList().get(word);
    }
}
