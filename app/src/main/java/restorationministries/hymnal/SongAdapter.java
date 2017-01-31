package restorationministries.hymnal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Keno on 1/31/2017 for Hymnal
 */

class SongAdapter extends ArrayAdapter<Song> {
    //View lookup cache (performance improvement)
    private static class ViewHolder {
        TextView name;
        TextView number;
    }

    SongAdapter(Context context, ArrayList<Song> songs) {
        super(context, 0, songs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        //Get the song's position number
        Song song = getItem(position);

        //Store views in cache
        ViewHolder viewHolder;

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_song, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_songName);
            viewHolder.number = (TextView) convertView.findViewById(R.id.tv_songNumber);

            //Cache the view holder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Populate the data from the data object via the View Holder object
        //into the template view.
        viewHolder.name.setText(song != null ? song.getTitle() : null);
        viewHolder.number.setText(song != null ? song.getNumber() : null);

        //Return the completed view to render on screen
        return convertView;
    }
}
