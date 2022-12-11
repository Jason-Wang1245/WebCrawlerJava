package model;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class Search extends Readable{
    private final SearchData search = new SearchData();
    private ArrayList<String> vectorOrder;
    private ArrayList<Double> queryVector;
    private ArrayList<SearchResult> searchResults;
    private double queryDenominator;

    // CONSTRUCTOR
    public Search(){
        vectorOrder = new ArrayList<String>();
        queryVector = new ArrayList<Double>();
        searchResults = new ArrayList<SearchResult>();
    }

    // SELECTION SORT
    // sorts search results based on SearchResult interface specifications
    private void selectionSort(int x){
        DecimalFormat format = new DecimalFormat("#.###");
        int n = searchResults.size();
        if (x <= n)
            n = x;

        for (int i = 0; i < n; i++) {
            int maxIndex = i;
            String maxValue = format.format(searchResults.get(i).getScore());
            for (int j = i + 1; j < searchResults.size(); j++){
               String jValue = format.format(searchResults.get(j).getScore());
               if (jValue.compareTo(maxValue) > 0){
                   maxIndex = j;
                   maxValue = jValue;
               } else if (jValue.equals(maxValue)){
                   if (searchResults.get(j).getTitle().compareTo(searchResults.get(maxIndex).getTitle()) < 0) {
                       maxIndex = j;
                       maxValue = jValue;
                   }
               }
            }

            SearchResult temp = searchResults.get(maxIndex);
            searchResults.set(maxIndex, searchResults.get(i));
            searchResults.set(i, temp);
        }
    }

    // SEARCH METHODS
    public List<SearchResult> search(String query, boolean boosted, int x){
        reset();
        getQueryVector(query);
        getPageScores(boosted);
        selectionSort(x);
        return searchResults.subList(0, x);
    }
    // HELPER METHODS
    // calculates the query vector, and also instantiates the order of the vectors
    private void getQueryVector(String query){
        String[] querySplit = query.strip().split(" ");
        HashMap<String, Integer> uniqueWords = new HashMap<String, Integer>();
        // count the instances of unique words within the query
        for (String word : querySplit){
            if (uniqueWords.containsKey(word))
                uniqueWords.replace(word, uniqueWords.get(word) + 1);
            else
                uniqueWords.put(word, 1);
        }

        for (String word : uniqueWords.keySet()) {
            double idf = search.getIdfValue(word);
            if (idf != 0) {
                double queryTfIdf = (Math.log(1 + ((double) uniqueWords.get(word) / querySplit.length)) / Math.log(2)) * idf;
                // instantiates values for the vector later used when calculating the score for each webpage crawled
                vectorOrder.add(word);
                queryVector.add(queryTfIdf);
                queryDenominator += Math.pow(queryTfIdf, 2);
            }
        }
        queryDenominator = Math.sqrt(queryDenominator);
    }
    // calculates the cosine similarity for each webpage with provided query vector results
    private void getPageScores(boolean boosted){
        File directory = new File("resources");
        if (!directory.isDirectory())
            return;
        File[] files = directory.listFiles();
        // iterates through all the webpages found during the crawl process
        for (File file : files){
            if (file.getName().equals("idf"))
                continue;
            String url = "https://" + file.getName().replace("}", "/");
            // gets the tfidf HashMap of the webpage
            HashMap<String, Double> tfIdfList = readHashMap("tfidf", "resources", url);
            if (tfIdfList == null)
                break;
            double numerator = 0;
            double pageDenominator = 0;
            double cosineSimilarity;
            // obtains pieces of the cosine similarity equation
            for (int i = 0; i < vectorOrder.size(); i++) {
                String word = vectorOrder.get(i);
                if (tfIdfList.containsKey(word)) {
                    double tfIdf = tfIdfList.get(word);
                    numerator += tfIdf * queryVector.get(i);
                    pageDenominator += Math.pow(tfIdf, 2);
                }
            }

            if (pageDenominator == 0) {
                cosineSimilarity = 0;
            } else {
                cosineSimilarity = numerator / (queryDenominator * Math.sqrt(pageDenominator));
                if (boosted)
                    cosineSimilarity *= search.getPageRank(url);
            }
            searchResults.add(new WebpageResult(search.getTitle(url), cosineSimilarity, url));
        }
    }
    // clears all previously calculated query values and search results for new search
    private void reset(){
        vectorOrder.clear();
        queryVector.clear();
        searchResults.clear();
        queryDenominator = 0;
    }
}
