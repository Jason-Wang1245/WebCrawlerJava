import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args){
        Webpage webpage = new Webpage("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
        Crawler crawler = new Crawler();
        crawler.crawl(webpage);
        CrawlerAnalysis analysis = new CrawlerAnalysis(crawler);
        analysis.resetDirectory("resources");
    }
}