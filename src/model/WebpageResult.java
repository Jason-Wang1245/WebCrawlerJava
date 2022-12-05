package model;

public class WebpageResult implements SearchResult{
    private String url;
    private String title;
    private double score;

    // CONSTRUCTOR
    public WebpageResult(String title, double score, String url){
        this.title = title;
        this.score = score;
        this.url = url;
    }

    // GETTERS
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getScore() {
        return score;
    }
}
