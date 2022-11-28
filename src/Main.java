public class Main {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();

        crawler.crawl(new Webpage("http://people.scs.carleton.ca/~davidmckenney/tinyfruits/N-4.html"));
//        HashMap<String, Integer> list = new HashMap<String, Integer>();
//        System.out.println(list.containsKey("Hello"));
//        list.put("Hello", 1);
//        System.out.println(list.containsKey("Hello"));
//        list.put("Hello", 2);
//        list.put("Bye", 2);
//        System.out.println(list);
    }
}