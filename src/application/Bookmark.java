package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import static application.Constants.DataConstants.*;

public class Bookmark implements Comparable<Bookmark>{
    private String url;
    private String topic;
    private String title;

    public static ArrayList<Bookmark> loadCSV() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            ArrayList<Bookmark> list = new ArrayList<Bookmark>();
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Bookmark url = new Bookmark(data[0], data[1], data[2]);
                    list.add(url);
                }
                line = br.readLine();
            }
            br.close();
            System.out.println(list);
            return list;
        } catch (Exception e) {
            return new ArrayList<Bookmark>();
        }

    }

    public static void saveCSV(ArrayList<Bookmark> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE));
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i).getUrl() + "," + list.get(i).getTopic() + "," + list.get(i).getTitle() + "\n");
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            return;
        }
    }

    // Getters
    public String getTitle() {
        return this.title;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getUrl() {
        return this.url;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Overridden methods
    @Override
    public String toString() {
        return ("[URL: \"" + this.getUrl() + "\" Title: \"" + this.getTitle() + "\" Topic: \"" + this.getTopic())
                + "\"]";
    }

     @Override public int compareTo(Bookmark url) {
         return this.getTitle().compareTo(url.getTitle());
     }

    // Constructors
    public Bookmark(String url) {
        this.url = url;
        this.topic = "";
        this.title = url;
    }

    public Bookmark(String url, String topic, String title) {
        this.url = url;
        this.topic = topic;
        this.title = title == "" ? url : title;
    }
}