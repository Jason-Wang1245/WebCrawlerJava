package model;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
// this class is responsible for crawling a webpage based on a given url. This class only visits each unique webpage once
public class Crawler{
    private int webpageId;
    private HashMap<Webpage, Integer> webpages;
    private HashSet<String> foundWords;

    // CONSTRUCTOR
    public Crawler(){
        webpageId = 0;
        webpages = new HashMap<Webpage, Integer>();
        foundWords = new HashSet<String>();
    }

    // GETTERS/SETTERS
    public Webpage getWebpageWithUrl(String url){
        for (Webpage webpage : webpages.keySet())
            if (webpage.getUrl().equals(url))
                return webpage;
        return null;
    }
    public HashSet<String> getFoundWords() {
        return foundWords;
    }
    public HashMap<Webpage, Integer> getWebpages() {
        return webpages;
    }
    public int getNumPages() {
        return webpages.size();
    }


    // OTHER METHODS
    // data retrieval of all unique pages that extend from the given webpage
    public void crawl(Webpage webpage){
        // exits crawl process if the webpage object has an invalid url
        if (!getHtml(webpage))
            return;
        addPageTitle(webpage);
        getWebData(webpage);
        webpages.put(webpage, webpageId);
        webpageId++;
        for (Webpage externalWebpage : getReferenceLinks(webpage))
            // recursively calls this method for new webpages that have been found that have not been crawled
            if (!webpages.containsKey(externalWebpage))
                crawl(externalWebpage);
    }
    // HELPER METHODS
    // gets the html form the url attribute of the given webpage argument and set its html attribute to the html found
    private boolean getHtml(Webpage webpage){
        int i = 0;
        // if a read failed, tries 10 times before returning false for invalid read.
        while (i < 10){
            try {
                webpage.setHtml(WebRequester.readURL(webpage.getUrl()));
                return true;
            } catch (IOException e) {
                System.out.println("Failed to read URL(" + webpage.getUrl() + ")");
                i++;
            }
        }
        return false;
    }
    // gets the page title of the given webpage and set its title attribute to the title found
    private void addPageTitle(Webpage webpage){
        webpage.setTitle(searchHtml(webpage, "<title>", "</title>"));
    }
    // gets all words within paragraphs of the given webpage and add them to the argument webpage
    private void getWebData(Webpage webpage){
        String[] words = searchHtml(webpage, "<p", "</p>").strip().split("\\s+");
        webpage.setNumWords(words.length);

        for (String word : words){
            foundWords.add(word);
            webpage.addWord(word.toLowerCase());
        }
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
                Webpage referencePage = new Webpage(fullPath);
                referenceLinks.add(referencePage);

                i = html.indexOf("</a>", i) + 4;
            }
            i++;
        }

        return referenceLinks;
    }
    // helper method that gets a specific portion of the argument webpage's html
    private String searchHtml(Webpage webpage, String openTag, String closeTag) {
        String html = webpage.getHtml();
        String data = "";

        int i = 0;
        while (i < html.length() - openTag.length()) {
            if (html.substring(i, i + openTag.length()).equals(openTag)) {
                data = data.concat(html.substring(html.indexOf(">", i) + 1, html.indexOf(closeTag, i)));
                i += html.indexOf(closeTag, i) + closeTag.length();
            }
            i++;
        }
        // returns an empty string if no data was found
        return data;
    }
}
