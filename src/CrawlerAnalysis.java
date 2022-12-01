public class CrawlerAnalysis {
    private Crawler crawler;

    // CONSTRUCTOR
    public CrawlerAnalysis(Crawler crawler){
        this.crawler = crawler;
    }


    // OTHER METHODS
    // with argument webpage, adds all the external references for the webpage based on crawled data in private attribute crawler
    private void getExternalLinks(Webpage webpage){
        for (Webpage otherWebpage : crawler.getWebpages())
            if (otherWebpage.getReferenceLinks().contains(webpage.getUrl()))
                webpage.addExternalLink(otherWebpage.getUrl());
    }
    // with argument webpage, calculates and adds all tfValue based on the webpages data attribute
    private void getPageTf(Webpage webpage){
        for (String word : webpage.getData().keySet())
            webpage.addTfValue(word, webpage.getData().get(word) / (double) webpage.getNumWords());
    }
    // with argument word, calculates and adds the according idf values to the hashmap of idValues in the crawler attribute
    private void getIdf(String word){
        int counter = 0;
        for (Webpage webpage : crawler.getWebpages())
            if (webpage.containsWord(word))
                counter++;
        crawler.addIdfValue(word, crawler.getNumPages() / (double) (1 + counter));
    }
}
