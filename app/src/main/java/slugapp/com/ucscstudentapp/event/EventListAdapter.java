package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.http.ImageHttpRequest;

/**
 * Created by simba on 5/31/15.
 *
 * Edited by isaiah on 6/27/2015.
 *
 * This file is the Adapter for EventListFragment.java and displays each Event to the ListView.
 */
public class EventListAdapter extends ArrayAdapter<Event> {
    public EventListAdapter(Context context) {
        super(context, R.layout.item_event);
    }

    public void setData(List<Event> events) {
        clear();
        for(Event e : events) add(e);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_event, null);
        }
        Event e = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView desc = (TextView) convertView.findViewById(R.id.description);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        name.setText(e.name());
        date.setText(e.date().getString());
        desc.setText(e.shortDesc());
        new ImageHttpRequest(e.url()).execute(image);
        return convertView;
    }
}
