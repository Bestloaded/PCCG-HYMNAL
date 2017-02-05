package restorationministries.hymnal;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
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
        final android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Index Fragment
        final IndexFragment indexFragment = new IndexFragment();
        indexFragment.setArguments(bundle);

        //Song Fragment
        final SongFragment songFragment = new SongFragment();
        songFragment.setArguments(bundle);

        //Adds default fragment to the fragment manager
        fragmentTransaction.add(R.id.fragmentContainer, indexFragment, "indexFragment");
        fragmentTransaction.commit();

        //Set up bottom bar listeners
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_index);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Toast.makeText(MainActivity.this, "Favourites Selected", Toast.LENGTH_SHORT).show();
                } else if (tabId == R.id.tab_index) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.fragmentContainer, indexFragment, "indexFragment")
                            .commit();
                } else if (tabId == R.id.tab_song) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.fragmentContainer, songFragment, "songFragment")
                            .commit();
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Toast.makeText(MainActivity.this, "Favourites Re-Selected", Toast.LENGTH_SHORT).show();
                } else if (tabId == R.id.tab_index) {
                    RecyclerView recyclerView = (RecyclerView) findViewById(android.R.id.list);
                    recyclerView.smoothScrollToPosition(0);
                } else if (tabId == R.id.tab_song) {
                    NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.song_nestedScrollView);
                    nestedScrollView.fullScroll(View.FOCUS_UP);
                }
            }
        });
    }
}
