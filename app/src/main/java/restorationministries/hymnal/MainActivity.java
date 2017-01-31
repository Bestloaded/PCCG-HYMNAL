package restorationministries.hymnal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SongXMLReader songDatabase;
    ArrayList<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Change from launcher theme to default
        setTheme(R.style.NoToolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Populate the list of songs
        songDatabase = new SongXMLReader(getApplicationContext());
        while (!songDatabase.parsingComplete) {
            songs = songDatabase.getSongList();
        }

        //Create bundle to be passed around
        Bundle bundle = new Bundle();
        Parcelable listParceable = Parcels.wrap(songs);
        bundle.putParcelable("SongList", listParceable);

        //Create each fragment to be displayed
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IndexFragment indexFragment = new IndexFragment();
        indexFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.contentContainer, indexFragment, "indexFragment");
        fragmentTransaction.commit();

        //Set up bottom bar listeners
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.contentContainer), "Favourites selected", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (tabId == R.id.tab_song) {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.contentContainer), "Song selected", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (tabId == R.id.tab_index) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.contentContainer), "Index selected", Snackbar.LENGTH_LONG);
                snackbar.show();
                }
            }
        });
    }
}
