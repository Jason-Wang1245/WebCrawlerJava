import javax.xml.crypto.Data;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Savable {
    public void saveToFile(String fileName, Object o){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("resources" + File.separator + fileName));
            out.writeObject(o);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open file.");
        } catch (IOException e) {
            System.out.println("Cannot write file.");
        }
    }

    public void save(HashMap<?,?> list){
        for (Object o : list.keySet())
            saveToFile(o.toString(), o);
    }

    public void resetDirectory(String path){
        deleteDirectory(new File(path));
        makeDirectory(new File(path));
    }

    public void makeDirectory(File directory){
        if (!directory.exists())
            directory.mkdir();
    }
    public void deleteDirectory(File directory){
        File[] files = directory.listFiles();
        if (files != null)
            for (File file : files)
                deleteDirectory(file);
        directory.delete();
    }
}
