package model;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args){
//        Webpage webpage = new Webpage("https://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
//        Crawler crawler = new Crawler();
//        crawler.crawl(webpage);
//        CrawlerAnalysis analysis = new CrawlerAnalysis(crawler);
//        analysis.resetDirectory("resources");
//        analysis.analysis();

//        SearchData searchData = new SearchData();
//        System.out.println(searchData.getIdfValue("peach"));
        SearchEngine searchEngine = new SearchEngine();

        for (SearchResult result : searchEngine.search("banana apple", true, 10))
            System.out.println(result);
    }
}