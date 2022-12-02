import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args){
        Webpage webpage = new Webpage("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
        Crawler crawler = new Crawler();
        crawler.crawl(webpage);
        CrawlerAnalysis analysis = new CrawlerAnalysis(crawler);
        analysis.analysis();


        System.out.println(crawler.idfValues);
        System.out.println();
        for (Webpage page : crawler.getWebpages().keySet()){
            System.out.print(page.getId());
            System.out.println(page);
        }

    }

    public static void test(int[] list){
        for (int i = 0; i < list.length; i++)
            list[i]++;
    }
}