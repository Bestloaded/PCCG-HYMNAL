package restorationministries.hymnal;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IndexFragment indexFragment = new IndexFragment();
        indexFragment.setArguments(bundle);

        //Adds them to the fragment manager
        fragmentTransaction.add(R.id.fragmentContainer, indexFragment, "indexFragment");
        fragmentTransaction.commit();

        //Set up bottom bar listeners
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_index);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Toast.makeText(MainActivity.this, "Favourites Selected", Toast.LENGTH_SHORT).show();
                } else if (tabId == R.id.tab_song) {
                    Toast.makeText(MainActivity.this, "Song Selected", Toast.LENGTH_SHORT).show();
                } else if (tabId == R.id.tab_index) {
                    Toast.makeText(MainActivity.this, "Index Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
