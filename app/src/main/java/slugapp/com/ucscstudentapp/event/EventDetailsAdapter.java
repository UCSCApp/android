package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 6/27/2015.
 *
 * Adapter for EventDetails.java. Similar function as EventsAdapter.java, maybe hoping to combine the 2.
 */
public class EventDetailsAdapter  extends ArrayAdapter<Event> {
    public EventDetailsAdapter(Context context, List<Event> objects) {
        super(context, R.layout.events_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_details, null);
        }
        Event e = getItem(position);
        if (e.name() == "" && e.date() == "" && e.desc() == "" && e.url() != "") {
            ((ViewManager) convertView).removeView(convertView.findViewById(R.id.text));
            ImageLoader image_loader = ImageLoader.getInstance();
            image_loader.displayImage(e.url(), (ImageView) convertView.findViewById(R.id.image));
        } else {
            ((TextView) convertView.findViewById(R.id.name)).setText(e.name());
            ((TextView) convertView.findViewById(R.id.date)).setText(e.date());
            ((TextView) convertView.findViewById(R.id.description)).setText(e.desc());
            if (e.url() != "") {
                ImageLoader image_loader = ImageLoader.getInstance();
                image_loader.displayImage(e.url(), (ImageView) convertView.findViewById(R.id.image));
            }
        }
        return convertView;
    }
}
