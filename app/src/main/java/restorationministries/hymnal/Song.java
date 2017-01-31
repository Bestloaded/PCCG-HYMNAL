package restorationministries.hymnal;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Keno on 1/28/2017 for Hymnal
 */
@Parcel
public class Song {
    // Public classes for parcel class
    // (private fields cannot be detected during annotation)
    String number;
    String title;
    String chorus;
    String author;
    int verseCount;
    ArrayList<String> verses;

    public Song(String number, String title, String chorus, String author, int verseCount, ArrayList<String> verses) {
        this.number = number;
        this.title = title;
        this.chorus = chorus;
        this.author = author;
        this.verseCount = verseCount;
        this.verses = verses;
    }

    Song() { /*Required empty bean constructor*/ }

    String getNumber() {
        return number;
    }

    String getTitle() {
        return title;
    }

    String getChorus() {
        return chorus;
    }

    String getAuthor() { return author; }

    int getVerseCount() {
        return verseCount;
    }

    ArrayList<String> getVerses() {
        return verses;
    }

    void setNumber(String number) {
        this.number = number;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setChorus(String chorus) {
        this.chorus = chorus;
    }

    void setAuthor(String author) { this.author = author; }

    void setVerseCount(int verseCount) {
        this.verseCount = verseCount;
    }

    void setVerses(ArrayList<String> verses) {
        this.verses = verses;
    }
}
