public class Main {
    public static void main(String[] args) {
        Webpage page = new Webpage("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-0.html");
        Crawler crawler = new Crawler(page);
        System.out.println(crawler.getPageData());
    }
}