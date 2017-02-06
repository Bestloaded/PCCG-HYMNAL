package restorationministries.hymnal;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import br.com.mauker.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {

    SongXMLReader songDatabase;
    ArrayList<Song> songs;
    ArrayList<String> songSuggestions;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Change from launcher theme to default
        setTheme(R.style.NoToolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle("Hymnal App");
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        setSupportActionBar(toolbar);

        //Populate the list of songs
        songDatabase = new SongXMLReader(getApplicationContext());
        while (!songDatabase.parsingComplete) {
            songs = songDatabase.getSongList();
        }

        //Populates the list of song suggestions for searching
        songSuggestions = new ArrayList<>(100);
        for (Song song : songs) {
            songSuggestions.add(song.getTitle());
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
        //Set up bottom bar reselection listeners
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

        //Creates the searchView functions
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.addSuggestions(songSuggestions);
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String suggestion = searchView.getSuggestionAtPosition(position);
                searchView.setQuery(suggestion, true);
                int i = 0;
                for (String title : songSuggestions) {
                    if (title.equals(suggestion)) {
                        Bundle bundle = songFragment.getArguments();
                        bundle.putInt("Song Number", i);
                        //TODO: Ensure correct song is displayed each time

                        bottomBar.selectTabAtPosition(2, false);
                        bottomBar.animate().translationY(0.0f);
                        Fragment songFrag = getSupportFragmentManager().findFragmentByTag("songFragment");
                        FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
                        if (songFrag != null) {
                            ft.detach(songFragment);
                            ft.attach(songFragment);
                        } else {
                            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            ft.replace(R.id.fragmentContainer, songFragment, "songFragment");
                        }
                        ft.commit();
                    }
                    i++;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
                bottomBar.animate().translationY(bottomBar.getHeight());
                //Show the search bar
                searchView.openSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isOpen()) {
            // Close the search on the back button press.
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}