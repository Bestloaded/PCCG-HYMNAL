package restorationministries.hymnal;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Keno on 2/5/2017 for Hymnal
 */

public class SongFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.song_fragment, container, false);

        //Data for the Song
        Bundle bundle = this.getArguments();
        ArrayList<Song> songs = null;
        int songNumber = 0;
        if (bundle != null) {
            songs = Parcels.unwrap(getArguments().getParcelable("SongList"));
            songNumber = getArguments().getInt("Song Number");
        }
        assert songs != null;

        //Find Linear Layout to add song display to
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.songContainer);

        //Create the song header
        TextView titleText = new TextView(getContext());
            titleText.setTextSize(18f);
            titleText.setTypeface(Typeface.DEFAULT_BOLD);
            titleText.setTextColor(Color.BLACK);
            titleText.setGravity(Gravity.CENTER);
            titleText.setText(String.format("%s - %s", songs.get(songNumber).getNumber(), songs.get(songNumber).getTitle()));
            titleText.setPadding(10, 10, 10, 10);

        linearLayout.addView(titleText);

        //Display a new text view for each verse
        for (int i = 0; i < songs.get(songNumber).getVerseCount(); ++i) {
            //TODO: Display each song in a unique way
            //TODO: Notable songs = 131......
            if (i == 1) {
                TextView chorusView = new TextView(getContext());
                    chorusView.setTextSize(14f);
                    chorusView.setTypeface(Typeface.DEFAULT_BOLD);
                    chorusView.setTextColor(Color.BLACK);
                    chorusView.setGravity(Gravity.START);
                    chorusView.setText(fromHtml("<h3>Chorus</h3>"));
                    chorusView.append(songs.get(songNumber).getChorus());
                    chorusView.setPadding(10, 20, 10, 20);
                linearLayout.addView(chorusView);
            }

            TextView verseView = new TextView(getContext());
                verseView.setTextSize(14f);
                verseView.setTypeface(Typeface.DEFAULT);
                verseView.setTextColor(Color.BLACK);
                verseView.setGravity(Gravity.START);
                verseView.setText(fromHtml("<b>" + (i+1) + "</b><br/>"));
                verseView.append(songs.get(songNumber).getVerses().get(i));
                verseView.setPadding(10, 10, 10, 10);
            linearLayout.addView(verseView);
        }

        if (!songs.get(songNumber).getAuthor().equals("")) {
            TextView authorView = new TextView(getContext());
                authorView.setTextSize(14f);
                authorView.setText(fromHtml("- <b><i>" + songs.get(songNumber).getAuthor() + "</i></b>"));
                authorView.setGravity(Gravity.END);
                authorView.setPadding(0, 0, 10, 0);
            linearLayout.addView(authorView);
        }

        return rootView;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
