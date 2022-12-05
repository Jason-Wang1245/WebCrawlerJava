import java.util.HashMap;
import java.util.HashSet;

public class CrawlerAnalysis extends Savable{
    private Crawler crawler;
    private HashMap<String, Double> idfValues;

    // CONSTRUCTOR
    public CrawlerAnalysis(Crawler crawler){
        this.crawler = crawler;
        idfValues = new HashMap<String, Double>();
    }

    public HashMap<String, Double> getIdfValues() {
        return idfValues;
    }

    public void analysis(){
        for (String word : crawler.getFoundWords())
            getIdf(word);
        saveToFile("idf", idfValues);
        for (Webpage webpage : crawler.getWebpages().keySet()){
            getExternalLinks(webpage);
            getPageTf(webpage);
            getTfIdf(webpage);
        }
        getPageRank();
        save(crawler.getWebpages());
    }
    // OTHER METHODS
    // with argument webpage, adds all the external references for the webpage based on crawled data in private attribute crawler
    private void getExternalLinks(Webpage webpage){
        for (Webpage otherWebpage : crawler.getWebpages().keySet())
            if (otherWebpage.getReferenceLinks().contains(webpage.getUrl()))
                webpage.addExternalLink(otherWebpage.getUrl());
    }
    // with argument webpage, calculates and adds all tfValue based on the webpages data attribute
    private void getPageTf(Webpage webpage){
        for (String word : webpage.getData().keySet())
            webpage.addTfValue(word, webpage.getData().get(word) / (double) webpage.getNumWords());
    }
    // with argument word, calculates and adds the according idf values to the hashmap of idValues in the crawler attribute
    private void getIdf(String word){
        int counter = 0;
        for (Webpage webpage : crawler.getWebpages().keySet())
            if (webpage.containsWord(word))
                counter++;
        idfValues.put(word, Math.log(crawler.getNumPages() / (double) (1 + counter)) / Math.log(2));
    }
    // with argument webpage, calculates and adds all tfIdfValue based on the webpages data attribute
    private void getTfIdf(Webpage webpage){
        for (String word : webpage.getData().keySet())
            webpage.addTfIdfValue(word, (Math.log(1 + webpage.getTfValue(word)) / Math.log(2)) * idfValues.get(word));
    }

    private void getPageRank(){
        // creates the initial vector with sum of all elements adding up to 1 (later used to calculate euclidean distance for page rank score)
        double[][] vectorA = new double[1][crawler.getNumPages()];
        double[][] adjacencyMatrix = new double[crawler.getNumPages()][1];
        double euclideanDistance = 1;
        // let N represent the number of webpages
        // creates a matrix that has N indices
        for (int i = 0; i < crawler.getNumPages(); i++)
            vectorA[0][i] = (double) 1 / crawler.getNumPages();

        for (Webpage webpage : crawler.getWebpages().keySet()){
            double[] row = new double[crawler.getNumPages()];
            for (String link : webpage.getReferenceLinks()){
                int index = crawler.getWebpages().get(crawler.getWebpageWithUrl(link));
                row[index] = (double) 1 / webpage.getReferenceLinks().size();
            }
            adjacencyMatrix[crawler.getWebpages().get(webpage)] = row;
        }

        adjacencyMatrix = MatrixMultiplication.scalarMultiplication(adjacencyMatrix, 1 - 0.1);

        for (int i = 0; i < crawler.getNumPages(); i++)
            for (int j = 0; j < crawler.getNumPages(); j++)
                adjacencyMatrix[i][j] += 0.1 / crawler.getNumPages();

        double[][] vectorB = vectorA.clone();
        while (euclideanDistance > 0.0001){
            vectorA = vectorB.clone();
            vectorB = MatrixMultiplication.multiplyMatrix(vectorA, adjacencyMatrix);
            euclideanDistance = MatrixMultiplication.euclideanDistance(vectorA, vectorB);
        }

        for (Webpage webpage : crawler.getWebpages().keySet())
            webpage.setPageRank(vectorB[0][crawler.getWebpages().get(webpage)]);
    }
}
