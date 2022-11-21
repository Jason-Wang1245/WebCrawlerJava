public class Webpage {
    // attributes
    private String url;

    // constructor
    public Webpage(String url){
        this.url = url;
    }

    // setters/getters
    public String getUrl(){
        return url;
    }
    public String getFormattedUrl(){
        return url.substring(7).replace("/", "}");
    }

    // other methods
}
