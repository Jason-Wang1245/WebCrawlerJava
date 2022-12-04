import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Readable {
    Webpage webpage;
    private void read(String fileName){
        try {
            fileName = fileName.substring(7).replace("/", "}");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("resources" + File.separator + fileName));
            webpage = (Webpage) in.readObject();
            in.close();
        } catch (ClassNotFoundException e){
            System.out.println("File of that fileName not found.");
        } catch (IOException e){
            System.out.println("Cannot read file.");
        }
    }

    public HashSet<String> readList(String fileName, int listType){
        read(fileName);
        if (webpage == null)
            return null;
        if (listType == 0)
            return webpage.getReferenceLinks();
        return webpage.getExternalLinks();
    }

    public double readValue(String fileName, String word, int valueType){
        read(fileName);
        if (webpage == null)
            return -1;
        else if (valueType == 0)
            return webpage.getPageRank();
        else if (valueType == 1)
            return webpage.getTfValue(word);
        return webpage.getTfIdfValue(word);
    }

    public double readIdfValue(String word){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("resources" + File.separator + "idf"));
            Object o = in.readObject();
            if (!(o instanceof HashMap<?,?>))
                return -1;
            HashMap<String, Double> idfValues = (HashMap<String, Double>) o;
            return idfValues.get(word);
        } catch (ClassNotFoundException e){
            System.out.println("File of that fileName not found.");
        } catch (IOException e){
            System.out.println("Cannot read file.");
        }
        return -1;
    }
}
