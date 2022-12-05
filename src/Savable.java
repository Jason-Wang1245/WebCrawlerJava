import javax.xml.crypto.Data;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Savable {
    protected void save(HashMap<Webpage,?> list, String path){
        for (Webpage webpage : list.keySet()){
            makeDirectory(new File(path + File.separator + webpage.getUrl().substring(8).replace("/", "}")));
            saveList("referenceLinks", path, webpage.getUrl(), webpage.getReferenceLinks());
            saveList("externalLinks", path, webpage.getUrl(), webpage.getExternalLinks());
            saveList("tf", path, webpage.getUrl(), webpage.getTfValues());
            saveList("tfidf", path, webpage.getUrl(), webpage.getTfIdfValues());
            saveString("title", path, webpage.getUrl(), webpage.getTitle());
            saveValue("pageRank", path, webpage.getUrl(), webpage.getPageRank());
        }

    }
    private boolean saveValue(String fileName, String path, String url, double value){
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            out.writeDouble(value);
            out.close();
            return true;
        } catch (IOException e){
            return false;
        }
    }
    private boolean saveString(String fileName, String path, String url, String value){
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            out.writeUTF(value);
            out.close();
            return true;
        } catch (IOException e){
            return false;
        }
    }
    protected boolean saveList(String fileName, String path, String url, Object o){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            out.writeObject(o);
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    // saveList override specifically for saving idf HashMap (because it is saved within different directory path)
    protected boolean saveList(String fileName, String path, Object o){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + File.separator + fileName));
            out.writeObject(o);
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // resets current cache folder
    public void resetDirectory(String path){
        deleteDirectory(new File(path));
        makeDirectory(new File(path));
    }
    public void makeDirectory(File directory){
        if (!directory.exists())
            directory.mkdir();
    }
    private void deleteDirectory(File directory){
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files)
                deleteDirectory(file);
        directory.delete();
    }
}
