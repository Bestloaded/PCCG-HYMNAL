package restorationministries.hymnal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Keno on 1/31/2017 for Hymnal
 */

class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private ArrayList<Song> songData;
    private Context context;
    private int lastPosition = -1;

    SongAdapter(ArrayList<Song> songData, Context context) {

        this.songData = songData;
        this.context = context;
    }

    //Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        //Create view holder
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get data from songData at this position
        // Replace the contents of the view with that songData
        viewHolder.number.setText(songData.get(viewHolder.getAdapterPosition()).getNumber());
        viewHolder.name.setText(songData.get(viewHolder.getAdapterPosition()).getTitle());

        //Animates items while scrolling the list
        Animation animation = AnimationUtils.loadAnimation(context,
                (viewHolder.getAdapterPosition() > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        viewHolder.itemView.startAnimation(animation);
        lastPosition = viewHolder.getAdapterPosition();
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return songData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        TextView number;

        ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView.findViewById(R.id.tv_songName);
            number = (TextView) itemLayoutView.findViewById(R.id.tv_songNumber);
        }
    }
}