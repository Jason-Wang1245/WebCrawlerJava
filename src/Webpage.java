import java.util.HashMap;
import java.util.HashSet;

public class Webpage {
    private String url;
    private String html;
    private String title;
    private int numWords;
    private HashMap<String, Double> tfValues;
    private HashMap<String, Integer> data;
    private HashSet<String> referenceLinks;
    private HashSet<String> externalLinks;

    // CONSTRUCTORS
    public Webpage(String url) {
        this.url = url;
        data = new HashMap<String, Integer>();
        referenceLinks = new HashSet<String>();
        externalLinks = new HashSet<String>();
        tfValues = new HashMap<String, Double>();
    }

    // GETTERS/SETTERS
    public boolean containsWord(String word){
        return data.containsKey(word);
    }
    public String getHtml() {
        return html;
    }
    public String getUrl() {
        return url;
    }
    public int getNumWords() {
        return numWords;
    }
    public HashSet<String> getReferenceLinks() {
        return referenceLinks;
    }
    public HashMap<String, Integer> getData() {
        return data;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setHtml(String webData) {
        this.html = webData;
    }
    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }

    // OVERRIDE METHODS
    // overriding default equals and hashCode methods to prevent duplicates in hashSet (duplicates are decided based on the objects url attribute)
    @Override
    public boolean equals(Object o) {
        if (o instanceof Webpage)
            return ((Webpage) o).getUrl().equals(this.url);
        else
            return false;
    }
    @Override
    public int hashCode(){
        return url.hashCode();
    }

    // OTHER METHODS
    public void addWord(String word){
        if (data.containsKey(word))
            data.put(word, data.get(word) + 1);
        else
            data.put(word, 1);
    }
    public void addReferenceLink(String link){
        referenceLinks.add(link);
    }
    public void addExternalLink(String link){
        externalLinks.add(link);
    }
    public void addTfValue(String word, double tfValue){
        tfValues.put(word, tfValue);
    }
}
