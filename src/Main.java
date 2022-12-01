import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args){
//        Crawler crawler = new Crawler();
//        Webpage webpage = new Webpage("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
//        crawler.crawl(webpage);
//        for (Webpage page : crawler.getWebpages().keySet()){
//            System.out.println(page.getUrl() + " ; " + crawler.getWebpages().get(page));
//        }

        double[][] a = {{1 , 5}, {2, 3}, {1, 7}};
        double[][] b = {{1, 2, 3, 7}, {5, 2, 8, 1}};
        System.out.println(Arrays.deepToString(MatrixMultiplication.multiplyMatrix(a, b)));
    }

    public static void test(int[] list){
        for (int i = 0; i < list.length; i++)
            list[i]++;
    }
}