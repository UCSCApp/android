package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.Event;

/**
 * Created by simba on 5/31/15.
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(Context context, List<Event> objects) {
        super(context, R.layout.events_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.events_item, null);
        }
        Event e = getItem(position);
        ((TextView)convertView.findViewById(R.id.name)).setText(e.name());
        ((TextView)convertView.findViewById(R.id.date)).setText(e.date());
        ((TextView)convertView.findViewById(R.id.description)).setText(e.desc());
        ((TextView)convertView.findViewById(R.id.url)).setText(e.url());
        Log.e("HERE3", "JERE");
        return convertView;
    }
}
