package restorationministries.hymnal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Keno on 1/31/2017 for Hymnal
 */

class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private ArrayList<Song> songData;


    SongAdapter(ArrayList<Song> songData) {
        this.songData = songData;
    }

    //Create new views
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, null);
        //Create view holder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get data from songData at this position
        // Replace the contents of the view with that songData
        viewHolder.number.setText(songData.get(position).getNumber());
        viewHolder.name.setText(songData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return songData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView number;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView.findViewById(R.id.tv_songName);
            number = (TextView) itemLayoutView.findViewById(R.id.tv_songNumber);
        }
    }
}