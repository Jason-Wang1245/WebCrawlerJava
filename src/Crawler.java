import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Crawler {
    private HashSet<String> foundPages;

    public Crawler(){
        foundPages = new HashSet<String>();
    }
    public String getPageData(String url){
        try {
            String pageData = WebRequester.readURL(url);
            foundPages.add(url);
            return pageData;
        } catch (IOException e) {
            return "Invalid URL";
        }
    }
    public String getPageTitle(String html){
        return searchHtml(html, "title");
    }

    // gets a hashmap of all the words found
    public HashMap<String, Integer> getWordData(String html){
        HashMap<String, Integer> data = new HashMap<String, Integer>();
        String[] words = searchHtml(html, "p").strip().split(" ");
        for (String word : words)
            if (data.containsKey(word))
                data.put(word, data.get(word) + 1);
            else
                data.put(word, 1);
        return data;
    }

    // gets all content within the argument html tag
    public String searchHtml(String html, String tagType){
        String openTag = "<" + tagType;
        String closeTag = "</" + tagType + ">";
        String data = "";

        int i = 0;
        while(i < html.length() - openTag.length()) {
            if (html.substring(i, i + openTag.length()).equals(openTag)){
                data = data.concat(html.substring(html.indexOf(">", i) + 1, html.indexOf(closeTag, i)));
                i += html.indexOf(closeTag, i) + closeTag.length();
            }
            i += 1;
        }
        // returns null if no data was found
        return data;
    }

    public void crawl(String url){
        String pageData = getPageData(url);
        PageData data = new PageData();
        for (int i = 0; i < pageData.length(); i++){
            if (pageData.substring(i, i + 2).equals("<a")){
                String href = pageData.substring(pageData.indexOf("href=\"", i) + 6, pageData.indexOf("\"", pageData.indexOf("href=\"", i ) + 6));
                if (href.startsWith("./"))
                    if (!url.substring(7).contains("/")) {
                        String fullPath = url.substring(0, url.indexOf(url.lastIndexOf("/") + 1)) + href.substring(2);
                    } else {
                        String fullPath = url + href.substring(2);
                    }
                if (!foundPages.contains(url)){
                    crawl(url);
                }
            }
        }
    }



}
