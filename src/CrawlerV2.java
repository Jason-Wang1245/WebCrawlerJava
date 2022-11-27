import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class CrawlerV2 {
    HashSet<Webpage> webpages;

    // CONSTRUCTOR
    public CrawlerV2(){
        webpages = new HashSet<Webpage>();
    }

    // OTHER METHODS
    // gets the html form the url attribute of the given webpage argument and set its html attribute to the html found
    public String getHtml(Webpage webpage){
        try {
            return WebRequester.readURL(webpage.getUrl());
        } catch (IOException e) {
            return null;
        }
    }
    // gets the page title of the given webpage and set its title attribute to the title found
    public void addPageTitle(Webpage webpage){
        webpage.setTitle(searchHtml(webpage, "<title>", "</title>", 0));
    }
    // gets all words within paragraphs of the given webpage to the Webpage argument
    public void getWebData(Webpage webpage){
        String[] words = searchHtml(webpage, "<p", "</p>", 0).strip().split(" ");
        ArrayList<String> webData = new ArrayList<String>();

        for (String word : words){
            webpage.addWord(word);
        }

    }

    public void getReferenceLinks(Webpage webpage){
        String html = webpage.getHtml();

        int i = 0;
        while (i < html.length() - 2){
            if (html.substring(i, i + 2).equals("<a")){
                String href = html.substring(html.indexOf("href=\"", i) + 6, html.indexOf("\"", html.indexOf(html.indexOf("href\"", i) + 6)));
                String fullPath = href;
                // checks if the anchor tag is a relative link
                if (href.startsWith("./")){
                    // checks if seed url is http://.../... or just http://... and creates the full path based on the outcome
                    if (!webpage.getUrl().substring(0, 7).contains("/"))
                        fullPath = webpage.getHtml().concat(href.substring(2));
                    else
                        fullPath = webpage.getHtml().substring(0, webpage.getHtml().lastIndexOf("/") + 1).concat(href.substring(2));
                }
                webpage.addReferenceLink(fullPath);
            }
            i++;
        }
    }

    public String searchHtml(Webpage webPage, String openTag, String closeTag, int startingIndex){
        String html = webPage.getHtml();
        String data = "";

        int i = 0;
        while (i < html.length() - openTag.length()) {
            if (html.substring(i, i + openTag.length()).equals(openTag)){
                data = data.concat(html.substring(html.indexOf(">", i) + 1, html.indexOf(closeTag, i)));
                i += html.indexOf(closeTag, i) + closeTag.length();
            }
            i++;
        }
        // returns null if no data was found
        return data;
    }

}
