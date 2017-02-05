package restorationministries.hymnal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by Keno on 1/31/2017 for Hymnal
 */

public class IndexFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.index_fragment, container, false);

        //Get Reference to recycler View
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(android.R.id.list);
        //Set Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);

        //Create touch listeners
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //Toast.makeText(getContext(), "Item #" + position + " touched" , Toast.LENGTH_SHORT).show();

                    }
                })
        );

        //Create dividers between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Data for the Recycler View
        Bundle bundle = this.getArguments();
        ArrayList<Song> songs = null;
        if (bundle != null) {
            songs = Parcels.unwrap(getArguments().getParcelable("SongList"));
        }

        //Create an adapter
        SongAdapter adapter = new SongAdapter(songs, getContext());
        //Set adapter
        recyclerView.setAdapter(adapter);
        //Because item list won't be changed, free spped improvement
        recyclerView.setHasFixedSize(true);
        //Set item animator to default
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;

    }
}
