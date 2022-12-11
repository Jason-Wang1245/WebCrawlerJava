package model;

import java.io.*;
import java.util.HashMap;
// savable is an abstract class rather than an interface because these implementations are fairly concrete no matter the implementation of the crawler and calculation portions
// certain fields are protected because they are needed within child classes. Making them public would be bad implementation because these methods are not needed within the child classes instances.
public abstract class Savable {
    public Savable(){}
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
    // most of these methods have boolean return statements to check if the saving process was successful
    // saves a double value to file
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
    // saves a singular string value to file
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
    // generic saveList method that will save any list
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

    // resets current cache folder, with path being the name
    // having an argument for the path of the cache folder makes for reusable code
    protected void resetDirectory(String path){
        deleteDirectory(new File(path));
        makeDirectory(new File(path));
    }
    protected void makeDirectory(File directory){
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
