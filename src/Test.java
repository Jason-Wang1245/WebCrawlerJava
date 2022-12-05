import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {
    public static void main(String[] args){
        Webpage webpage = new Webpage("asdasdasdas");
        Crawler crawler = new Crawler();
        crawler.crawl(webpage);


    }
}
