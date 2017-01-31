package restorationministries.hymnal;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Keno on 1/31/2017 for Hymnal
 */

public class IndexFragment extends ListFragment implements OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        Bundle bundle = this.getArguments();
        ArrayList<Song> songs = null;
        if (bundle != null) {
            songs = Parcels.unwrap(getArguments().getParcelable("SongList"));
        }

        View view = inflater.inflate(R.layout.index_fragment, container, false);
        SongAdapter adapter = new SongAdapter(getActivity(), songs);

        //Attach the adapter to list view
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Snackbar.make(getListView(), "Song Selected!", Snackbar.LENGTH_SHORT);
    }
}
