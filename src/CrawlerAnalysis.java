public class CrawlerAnalysis {
    private Crawler crawler;

    // CONSTRUCTOR
    public CrawlerAnalysis(Crawler crawler){
        this.crawler = crawler;
    }


    // OTHER METHODS
    // with argument webpage, adds all the external references for the webpage based on crawled data in private attribute crawler
    private void getExternalLinks(Webpage webpage){
        for (Webpage otherWebpage : crawler.getWebpages().keySet())
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
        for (Webpage webpage : crawler.getWebpages().keySet())
            if (webpage.containsWord(word))
                counter++;
        crawler.addIdfValue(word, crawler.getNumPages() / (double) (1 + counter));
    }
    // with argument webpage, calculates and adds all tfIdfValue based on the webpages data attribute
    private void getTfIdf(Webpage webpage){
        for (String word : webpage.getData().keySet()) {
            webpage.addTfIdfValue(word, (Math.log(1 + webpage.getTfValue(word)) / Math.log(2)) * crawler.getIdfValue(word));
        }
    }

    public void getPageRank(){
        double[][] matrixA = new double[crawler.getNumPages()][crawler.getNumPages()];
        double[][] adjacencyMatrix = new double[crawler.getNumPages()][crawler.getNumPages()];
        // initialize values for matrixA
        for (int i = 0; i < crawler.getNumPages(); i++)
            for (int j = 0; j < crawler.getNumPages(); j++)
                matrixA[i][j] = (double) 1 / crawler.getNumPages();

        for (Webpage webpage : crawler.getWebpages().keySet()){
            double[] row = new double[crawler.getNumPages()];
            for (String link : webpage.getReferenceLinks()){
                int index = crawler.getWebpages().get(crawler.getWebpageWithUrl(link));
                row[index] = (double) 1 / webpage.getReferenceLinks().size();
            }
            adjacencyMatrix[crawler.getWebpages().get(webpage)] = row;
        }


    }
}
