package model;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
// this class being abstract over an interface is the same reasoning as the Savable class
public abstract class Readable {
    public Readable(){}
    // reads a hashset from file and returns
    protected HashSet<String> readHashSet(String fileName, String path, String url){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            Object o = in.readObject();
            in.close();
            if (!(o instanceof HashSet<?>))
                return null;
            return (HashSet<String>) o;
        } catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
    // reads a hashmap from file with double values and returns it
    protected HashMap<String, Double> readHashMap(String fileName, String path, String url){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            Object o = in.readObject();
            in.close();
            if (!(o instanceof HashMap<?,?>))
                return null;
            return (HashMap<String, Double>) o;
        } catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
    // reads a file with a singular double value and returns it
    protected double readValue(String fileName, String path, String url){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            double value = in.readDouble();
            in.close();
            return value;
        } catch (IOException e){
            return -1;
        }
    }
    // reads a file with a string value and returns it
    protected String readString(String fileName, String path, String url){
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(path + File.separator + url.substring(8).replace("/", "}") + File.separator + fileName));
            String word = in.readUTF();
            in.close();
            return word;
        } catch (IOException e){
            return "";
        }
    }
    // reads the idf list, because it is stored within a different path than other HashMaps
    protected HashMap<String, Double> readIdfList(String path){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + File.separator + "idf"));
            Object o = in.readObject();
            in.close();
            if (!(o instanceof HashMap<?,?>))
                return null;
            return (HashMap<String, Double>) o;
        } catch (IOException | ClassNotFoundException e){
            return null;
        }
    }
}
