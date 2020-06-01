package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import static application.Constants.DataConstants.*;

public class Bookmark implements Comparable<Bookmark> {
    // Each bookmark has a url (website), topic (folder), and a title associated with it. These are used for display purposes
    private String url;
    private String topic;
    private String title;

    // getBookmarks returns a sorted nested ArrayList of Bookmarks for use outside this class
    public static ArrayList<ArrayList<Bookmark>> getBookmarks() {
        ArrayList<Bookmark> list = loadCSV();
        ArrayList<ArrayList<Bookmark>> finalList = new ArrayList<ArrayList<Bookmark>>();
        insSort(list);
        HashMap<String, ArrayList<Bookmark>> topics = new HashMap<String, ArrayList<Bookmark>>();

        // Places each Bookmark from the csv file into a hashmap of ArrayLists. The key is the topic, while the value is an ArrayList of Bookmarks
        for (Bookmark i : list) {
            if (!topics.containsKey(i.getTopic()))
                topics.put(i.getTopic(), new ArrayList<Bookmark>());
            topics.get(i.getTopic()).add(i);
        }
        // Moves the hashmap into an ArrayList for easy use
        for (ArrayList<Bookmark> i : topics.values()) {
            finalList.add(i);
        }
        return finalList;
    }

    // removeBookmark removes a Bookmark from the csv file. 
    public static void removeBookmark(Bookmark toRemove) {
        ArrayList<Bookmark> bookmarks = loadCSV();
        for (int i = 0; i < bookmarks.size(); i++) {
            if (bookmarks.get(i).equals(toRemove)) {
                bookmarks.remove(i);
            }
        }
        saveCSV(bookmarks);
    }

    // loadCSV returns an ArrayList of Bookmarks to be used in GetBookmarks & removeBookmark
    public static ArrayList<Bookmark> loadCSV() {
        // Reads data from the file
        try {
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            ArrayList<Bookmark> list = new ArrayList<Bookmark>();
            String line = br.readLine();
            while (line != null) {
                // Enters the data based on 3 scenarios
                String[] data = line.split(",");
                if (data.length == 3) { // No missing data so no special requirements
                    list.add(new Bookmark(data[0], data[1], data[2]));
                } else if (data.length == 2) { // Topic is missing, so it is entered as blank
                    list.add(new Bookmark(data[0], "", data[1]));
                } else if (data.length == 1) { // Only the URL is available, so topic is blank and title is set to URL
                    list.add(new Bookmark(data[0], "", data[0]));
                }
                line = br.readLine();
            }
            br.close();
            return list;
        } catch (Exception e) {
            return new ArrayList<Bookmark>();
        }
    }

    // saveCSV saves the ArrayList of bookmarks back to the datafile
    public static void saveCSV(ArrayList<Bookmark> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE));
            for (int i = 0; i < list.size(); i++) {
                // Writes the file in the format "URL","Topic","Title"
                bw.write(list.get(i).getUrl() + "," + list.get(i).getTopic() + "," + list.get(i).getTitle() + "\n");
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            return;
        }
    }

    // insSort sorts the Bookmarks using insertion sort on the title for use in getBookmarks()
    private static void insSort(ArrayList<Bookmark> arr) {
        for (int i = 1; i < arr.size(); i++) {
            int j = i;
            while (j > 0 && arr.get(j).compareTo(arr.get(j - 1)) < 0) {
                // Swaps Bookmarks at position j & j-1
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
    public String toString() { // Useful for debugging
        return ("[URL: \"" + this.getUrl() + "\" Title: \"" + this.getTitle() + "\" Topic: \"" + this.getTopic())
                + "\"]";
    }

    @Override
    public int compareTo(Bookmark bm) { // Allows bookmarks to be sorted by title
        return this.getTitle().compareTo(bm.getTitle());
    }

    public boolean equals(Bookmark bm) { // Makes it easy to check if 2 bookmarks are the same
        if (!this.getTitle().equals(bm.getTitle())) {
            return false;
        } else if (!this.getTopic().equals(bm.getTopic())) {
            return false;
        } else if (!this.getUrl().equals(bm.getUrl())) {
            return false;
        }
        return true;
    }

    // Constructor (If the topic is empty gives it a default topic.)
    public Bookmark(String url, String topic, String title) {
        // Removes all commas from text fields as this will mess up the CSV file
        topic = topic.replaceAll(",","");
        title = title.replaceAll(",","");
        url = url.replaceAll(",","");

        this.url = url;
        this.topic = !topic.equals("") ? topic : "Misc";
        this.title = !title.equals("") ? title : url.replace("https://", "");
    }
}