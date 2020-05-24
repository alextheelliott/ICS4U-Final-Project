package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static application.Constants.DataConstants.*;

public class Bookmark implements Comparable<Bookmark> {
    private String url;
    private String topic;
    private String title;

    public static ArrayList<ArrayList<Bookmark>> getBookmarks() {
        ArrayList<Bookmark> list = loadCSV();
        ArrayList<ArrayList<Bookmark>> finalList = new ArrayList<ArrayList<Bookmark>>();
        insSort(list);
        HashMap<String, ArrayList<Bookmark>> topics = new HashMap<String, ArrayList<Bookmark>>();

        for (Bookmark i : list) {
            if (!topics.containsKey(i.getTopic()))
                topics.put(i.getTopic(), new ArrayList<Bookmark>());
            topics.get(i.getTopic()).add(i);
        }

        for (ArrayList<Bookmark> i : topics.values()) {
            finalList.add(i);
        }
        return finalList;
    }

    public static void removeBookmark(Bookmark toRemove) {
        ArrayList<Bookmark> bookmarks = loadCSV();
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).equals(toRemove)) {
                bookmarks.remove(i);
            }
        }
        saveCSV(bookmarks);
    }

    public static ArrayList<Bookmark> loadCSV() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            ArrayList<Bookmark> list = new ArrayList<Bookmark>();
            String line = br.readLine();
            while (line != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    list.add(new Bookmark(data[0], data[1], data[2]));
                } else if (data.length == 2) {
                    list.add(new Bookmark(data[0], "", data[1]));
                } else if (data.length == 1) {
                    list.add(new Bookmark(data[0], "", data[0]));
                    System.out.println(Arrays.toString(data));
                }
                line = br.readLine();
            }
            br.close();
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

    public static void insSort(ArrayList<Bookmark> arr) {
        for (int i = 1; i < arr.size(); i++) {
            int j = i;
            while (arr.get(j).compareTo(arr.get(j - 1)) > 0 && j > 1) {
                Bookmark temp = arr.get(j);
                arr.set(j, arr.get(j - 1));
                arr.set(j - 1, temp);
                j--;
            }
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

    @Override
    public int compareTo(Bookmark bm) {
        return this.getTitle().compareTo(bm.getTitle());
    }

    public boolean equals(Bookmark bm) {
        if (!this.getTitle().equals(bm.getTitle())) {
            return false;
        } else if (!this.getTopic().equals(bm.getTopic())) {
            return false;
        } else if (!this.getUrl().equals(bm.getUrl())) {
            return false;
        }
        return true;
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