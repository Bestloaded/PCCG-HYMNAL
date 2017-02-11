package restorationministries.hymnal;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    public SongFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
            titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            titleText.setTypeface(Typeface.create("sans-serif", 1));
            titleText.setTextColor(Color.BLACK);
            titleText.setGravity(Gravity.START);
            titleText.setTextColor(Color.parseColor("#0f4983"));
            titleText.setText(String.format("%s - %s", songs.get(songNumber).getNumber(), songs.get(songNumber).getTitle()));
            titleText.setPadding(30, 20, 30, 20);

        linearLayout.addView(titleText);

        //Display a new text view for each verse
        for (int i = 0; i < songs.get(songNumber).getVerseCount(); ++i) {
            //TODO: Display each song in a unique way
            //TODO: Notable songs = 131......
            if (i == 1) {
                TextView chorusView = new TextView(getContext());
                    chorusView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    chorusView.setGravity(Gravity.START);
                    SpannableStringBuilder chorus = new SpannableStringBuilder("Chorus\n");
                    chorus.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    chorus.setSpan(new ForegroundColorSpan(Color.parseColor("#0f4983")), 0, 6, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    chorus.setSpan(new RelativeSizeSpan(1.1f), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    chorusView.setText(chorus);
                    chorusView.append(songs.get(songNumber).getChorus());
                    chorusView.setPadding(30, 20, 30, 20);
                linearLayout.addView(chorusView);
            }

            TextView verseView = new TextView(getContext());
                verseView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                verseView.setGravity(Gravity.START);
                SpannableStringBuilder verse = new SpannableStringBuilder("Verse " + (i+1) + "\n");
                verse.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                verse.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), 0, 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                verse.setSpan(new RelativeSizeSpan(1.1f), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                verseView.setText(verse);
                verseView.append(songs.get(songNumber).getVerses().get(i));
                verseView.setPadding(30, 20, 30, 20);
            linearLayout.addView(verseView);
        }

        if (!songs.get(songNumber).getAuthor().equals("")) {
            TextView authorView = new TextView(getContext());
                authorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                authorView.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
                SpannableStringBuilder author = new SpannableStringBuilder("- " + songs.get(songNumber).getAuthor());
                authorView.setText(author);
                authorView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
                authorView.setGravity(Gravity.END);
                authorView.setPadding(0, 0, 30, 20);
            linearLayout.addView(authorView);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.song_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourite:
                //Add to favourites list
                return true;
            case R.id.action_resize_12:
                resizeText(12);
                return true;
            case R.id.action_resize_14:
                resizeText(14);
                return true;
            case R.id.action_resize_16:
                resizeText(16);
                return true;
            case R.id.action_resize_18:
                resizeText(18);
                return true;
            case R.id.action_resize_20:
                resizeText(20);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void resizeText(int fontSize) {
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.songContainer);
        final int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == 0) {
                TextView tv = (TextView) linearLayout.getChildAt(0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize + 6);
            } else {
                TextView tv = (TextView) linearLayout.getChildAt(i);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            }
        }
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
