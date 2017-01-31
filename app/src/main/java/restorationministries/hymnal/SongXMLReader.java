package restorationministries.hymnal;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Keno on 1/28/2017 for Hymnal
 */

class SongXMLReader {

    private Context context;
    private ArrayList<Song> songs = new ArrayList<>(10);
    private ArrayList<String> verses = new ArrayList<>(5);
    volatile boolean parsingComplete = false;

    SongXMLReader(Context ctx) {
        fetchXml(ctx);
    }

    private void fetchXml(final Context ctx) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    context = ctx;
                    XmlPullParser myParser = ctx.getResources().getXml(R.xml.songs);
                    parseXmlAndStoreIt(myParser);
                }
                catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private void parseXmlAndStoreIt(XmlPullParser myParser) throws XmlPullParserException, IOException
    {
        int event;
        String text = "";
        int verseCount = 0;

        //Vars to be passed into Song object
        String chorus = "";
        String number = "";
        String title = "";
        String verse;
        String author = "";

        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                verse = "";
                String name = myParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        switch (name) {
                            case "number":
                                number = number + text;
                                break;
                            case "title":
                                title = title + text;
                                break;
                            case "author":
                                //TODO: Find better way to do this
                                if (text.length() < 30) author = author + text;
                                break;
                            case "verse":
                                ++verseCount;
                                verse += text;
                                verse = verse.replaceAll("\\s+", " ");
                                verse = verse.replaceAll("\\*\\* ", System.getProperty("line.separator"));
                                verses.add(verse);
                                break;
                            case "chorus":
                                chorus = chorus + text;
                                //Replace extra line breaks 's+' and then replace asterisks + space
                                chorus = chorus.replaceAll("\\s+", " ");
                                chorus = chorus.replaceAll("\\*\\* ", System.getProperty("line.separator"));
                                break;
                            case "song":
                                Song newSong = new Song();
                                newSong.setTitle(title);
                                newSong.setChorus(chorus);
                                newSong.setNumber(number);
                                newSong.setAuthor(author);
                                newSong.setVerseCount(verseCount);
                                newSong.setVerses(new ArrayList<>(verses));
                                songs.add(newSong);
                                verseCount = 0;
                                chorus = "";
                                number = "";
                                title = "";
                                author = "";
                                verses.clear();
                                break;
                        }
                        break;
                }
                event = myParser.next();
            }
            parsingComplete = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<Song> getSongList() {
        return songs;
    }
}
