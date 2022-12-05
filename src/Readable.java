import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Readable {
    Webpage webpage;
    public Readable(){
        webpage = new Webpage("");
    }
    // general read method that gets the object from file based on the give url
    // returns true if the object was found and set to webpage
    // returns false if any exceptions were encountered
    private boolean read(String url){
        try {
            if (webpage.getUrl().equals(url))
                return true;
            url = url.substring(8).replace("/", "}");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("resources" + File.separator + url));
            webpage = (Webpage) in.readObject();
            in.close();
            return true;
        } catch (IOException | ClassNotFoundException e){
            return false;
        }
    }
    // calls read and returns null if false is returned from read. If true is returned and ...
    // listType == 0, a HashSet of incoming links will be returned
    // listType == 1, a HashSet of outgoing links will be returned
    public HashSet<String> readList(String fileName, int listType){
        if (!read(fileName))
            return null;
        if (listType == 0)
            return webpage.getReferenceLinks();
        return webpage.getExternalLinks();
    }
    // same as previous method, but meant for retrieving double values
    public double readValue(String fileName, String word, int valueType){
        // if read returned false, return -1 if the value being retrieved is pageRank, return 0 if the value being retrieved is anything else
        if (!read(fileName))
            if (valueType == 0)
                return -1;
            else
                return 0;
        else if (valueType == 0)
            return webpage.getPageRank();
        else if (valueType == 1)
            return webpage.getTfValue(word);
        return webpage.getTfIdfValue(word);
    }
    // retrieves the entire HashMap of idf list. This method returns the entire map because it is needed later in Search
    public HashMap<String, Double> readIdfList(){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("resources" + File.separator + "idf"));
            Object o = in.readObject();
            if (!(o instanceof HashMap<?,?>))
                throw new ClassNotFoundException();
            return (HashMap<String, Double>) o;
        } catch (ClassNotFoundException | IOException e){
            return null;
        }
    }
    // returns the entire tfIdfList because it is needed within Search
    public HashMap<String, Double> readTfIdfList(String url){
        if (!read(url))
            return null;
        return webpage.getTfIdfValues();
    }
    // returns he title of the given webpage. Needed in Search
    public String getTitle(String url){
        if (!read(url))
            return "";
        return webpage.getTitle();
    }
}
