import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Webpage implements Serializable {
    private int id;
    private String url;
    private String html;
    private String title;
    private int numWords;
    private double pageRank;
    private HashMap<String, Double> tfValues;
    private HashMap<String, Double> tfIdfValues;
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
        tfIdfValues = new HashMap<String, Double>();
    }

    // GETTERS
    public boolean containsWord(String word){
        return data.containsKey(word);
    }
    public double getPageRank() {
        return pageRank;
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
    public double getTfValue(String word){
        return tfValues.get(word);
    }
    public double getTfIdfValue(String word){
        return tfIdfValues.get(word);
    }
    public HashSet<String> getReferenceLinks() {
        return referenceLinks;
    }
    public HashMap<String, Integer> getData() {
        return data;
    }
    public HashSet<String> getExternalLinks() {
        return externalLinks;
    }

    // SETTERS
    public void setId(int id){
        this.id = id;
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
    public void setPageRank(double pageRank){
        this.pageRank = pageRank;
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
    // toString for testing purposes
    @Override
    public String toString(){
        return url.substring(7).replace("/", "}");
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
    public void addTfIdfValue(String word, double tfIdfValue){
        tfIdfValues.put(word, tfIdfValue);
    }
}
