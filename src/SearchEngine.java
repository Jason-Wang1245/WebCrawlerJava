import java.io.File;
import java.util.List;

public class SearchEngine implements ProjectTester{
    private Crawler crawler;
    private CrawlerAnalysis crawlerAnalysis;
    private SearchData searchData;
    private Search search;

    public SearchEngine(){
        crawler = new Crawler();
        crawlerAnalysis = new CrawlerAnalysis(crawler);
        searchData = new SearchData();
        search = new Search();
    }

    @Override
    public void initialize() {
        crawlerAnalysis.resetDirectory("resources");
    }

    @Override
    public void crawl(String seedURL) {
        crawler.crawl(new Webpage(seedURL));
        crawlerAnalysis.analysis();
    }

    @Override
    public List<String> getIncomingLinks(String url) {
        return searchData.getIncomingLinks(url);
    }

    @Override
    public List<String> getOutgoingLinks(String url) {
        return searchData.getOutgoingLinks(url);
    }

    @Override
    public double getPageRank(String url) {
        return searchData.getPageRank(url);
    }

    @Override
    public double getIDF(String word) {
        return searchData.getIdfValue(word);
    }

    @Override
    public double getTF(String url, String word) {
        return searchData.getTfValue(url, word);
    }

    @Override
    public double getTFIDF(String url, String word) {
        return searchData.getTfIdfValue(url, word);
    }

    @Override
    public List<SearchResult> search(String query, boolean boost, int X) {
        return search.search(query, boost, X);
    }
}
