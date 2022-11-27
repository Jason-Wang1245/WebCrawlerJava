import java.util.HashMap;
import java.util.HashSet;

public class Webpage {
    private String url;
    private String html;
    private String title;
    private HashMap<String, Integer> data;
    private HashSet<String> referenceLinks;

    // CONSTRUCTORS
    public Webpage(String url) {
        this.url = url;
        data = new HashMap<String, Integer>();
        referenceLinks = new HashSet<String>();
    }

    // GETTERS/SETTERS

    public String getHtml() {
        return html;
    }
    public String getUrl() {
        return url;
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
}
