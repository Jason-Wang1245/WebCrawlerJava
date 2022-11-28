import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Crawler {
    private HashSet<Webpage> webpages;

    // CONSTRUCTOR
    public Crawler(){
        webpages = new HashSet<Webpage>();
    }

    // OTHER METHODS
    // data retrieval of all unique pages that extend from the given webpage
    public void crawl(Webpage webpage){
        getHtml(webpage);
        addPageTitle(webpage);
        getWebData(webpage);
        webpages.add(webpage);
        for (Webpage externalWebpage : getReferenceLinks(webpage))
            if (!webpages.contains(externalWebpage))
                crawl(externalWebpage);
    }
    // gets the html form the url attribute of the given webpage argument and set its html attribute to the html found
    private void getHtml(Webpage webpage){
        try {
            webpage.setHtml(WebRequester.readURL(webpage.getUrl()));
        } catch (IOException e) {
            System.out.println("Invalid Webpage");
        }
    }
    // gets the page title of the given webpage and set its title attribute to the title found
    private void addPageTitle(Webpage webpage){
        webpage.setTitle(searchHtml(webpage, "<title>", "</title>"));
    }
    // gets all words within paragraphs of the given webpage and add them to the argument webpage
    private void getWebData(Webpage webpage){
        String[] words = searchHtml(webpage, "<p", "</p>").strip().split("\\s+");

        for (String word : words)
            webpage.addWord(word.toLowerCase());

    }
    // gets all reference links of the given webpage and add them to the argument webpage
    private HashSet<Webpage> getReferenceLinks(Webpage webpage){
        HashSet<Webpage> referenceLinks = new HashSet<Webpage>();
        String html = webpage.getHtml();

        int i = 0;
        while (i < html.length() - 2){
            if (html.substring(i, i + 2).equals("<a")){
                String href = html.substring(html.indexOf("href=\"", i) + 6, html.indexOf("\"", html.indexOf("href=\"", i) + 6));
                String fullPath = href;
                // checks if the anchor tag is a relative link
                if (href.startsWith("./")){
                    // checks if seed url is http://.../... or just http://... and creates the full path based on the outcome
                    if (!webpage.getUrl().substring(0, 7).contains("/"))
                        fullPath = webpage.getUrl().concat(href.substring(2));
                    else
                        fullPath = webpage.getUrl().substring(0, webpage.getUrl().lastIndexOf("/") + 1).concat(href.substring(2));
                }
                webpage.addReferenceLink(fullPath);
                referenceLinks.add(new Webpage(fullPath));
                i = html.indexOf("</a>", i) + 4;
            }
            i++;
        }

        return referenceLinks;
    }
    // helper method that gets a specific portion of the argument webpage's html
    private String searchHtml(Webpage webpage, String openTag, String closeTag){
        String html = webpage.getHtml();
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
