import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Crawler {
    // attributes
    private HashSet<Webpage> checkedPages;

    // constructors
    public Crawler(Webpage webpage){
        checkedPages = new HashSet<Webpage>();
    }

    // getters/setters

    // other methods
    public String getPageData(Webpage webpage){
        try {
            if (!checkedPages.contains(webpage)){
                String pageData = WebRequester.readURL(webpage.getUrl());
                checkedPages.add(webpage);
                return pageData;
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Integer> getData(String pageData){
        HashMap<String, Integer> data = new HashMap<String, Integer>();
        for (int i = 0; i < pageData.length(); i++){
            if (pageData.substring(i, i + 2).equals("<p")){
                String[] words = pageData.substring(pageData.indexOf(">", i), pageData.indexOf("</p>", i)).strip().split(" ");
                for (String word : words){
                    if (data.containsKey(word)){
                        
                    }
                }


            }
        }
    }


}
