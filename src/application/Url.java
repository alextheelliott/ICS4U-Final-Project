package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static application.Constants.DataConstants.*;

public class Url {
    private String url;
    private String topic;
    private String title;

    public static ArrayList<Url> loadCSV() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            ArrayList<Url> list = new ArrayList<Url>();
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    Url url = new Url(data[0], data[1], data[2]);
                    list.add(url);
                }
                line = br.readLine();
            }
            br.close();
            System.out.println(list);
            return list;
        } catch (Exception e) {
            return new ArrayList<Url>();
        }

    }

    public static void saveCSV(ArrayList<Url> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE));
            for (int i = 0; i < list.size(); i++) {
                bw.write(list.get(i).getUrl() + "," + list.get(i).getTopic() + "," + list.get(i).getTitle()
                        + "\n");
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            return;
        }
    }

    // Getters
    private String getTitle() {
        return this.title;
    }

    private String getTopic() {
        return this.topic;
    }

    private String getUrl() {
        return this.url;
    }

    // Overridden methods
    @Override
    public String toString() {
        return ("[URL: \"" + this.getUrl() + "\" Title: \"" + this.getTitle() + "\" Topic: \"" + this.getTopic())
                + "\"]";
    }

    /*@Override
    public int compareTo(Object url)
        //TODO: Compare To method for sorting
    }*/

    // Constructors
    public Url(String url) {
        this.url = url;
        this.topic = "";
        this.title = url;
    }

    public Url(String url, String topic, String title) {
        this.url = url;
        this.topic = topic;
        this.title = title == "" ? url : title;
    }
}