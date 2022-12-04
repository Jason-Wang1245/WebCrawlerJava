import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args){
        Webpage webpage = new Webpage("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
        Crawler crawler = new Crawler();
        CrawlerAnalysis analysis = new CrawlerAnalysis(crawler);
        crawler.crawl(webpage);
        analysis.analysis();

        SearchData search = new SearchData();
        System.out.println(search.getPageRank("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-9.html"));
    }
}