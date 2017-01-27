package restorationministries.hymnal;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Change from launcher theme to default
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View contentContainer = findViewById(R.id.contentContainer);

        //Set up bottom bar listeners
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favourites) {
                    Snackbar snackbar = Snackbar.make(contentContainer, "Favourites selected", Snackbar.LENGTH_LONG);
                } else if (tabId == R.id.tab_song) {
                    Snackbar snackbar = Snackbar.make(contentContainer, "Song selected", Snackbar.LENGTH_LONG);
                }
            }
        });
    }
}
