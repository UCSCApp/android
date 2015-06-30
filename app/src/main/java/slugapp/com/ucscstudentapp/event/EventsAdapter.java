package slugapp.com.ucscstudentapp.event;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.Event;

/**
 * Created by simba on 5/31/15.
 *
 * This file is the adapter for Events.java. This file basically takes each Event object and displays
 * it to the listview
 */
public class EventsAdapter extends ArrayAdapter<Event> {
    public EventsAdapter(Context context, List<Event> objects) {
        super(context, R.layout.events_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.events_item, null);
        }
        Event e = getItem(position);
        /*
         * If no text, but image, then show banner
         */
        if (e.name() == "" && e.date() == "" && e.desc() == "" && e.url() != "") {
            ((ViewManager) convertView).removeView(convertView.findViewById(R.id.text));
            // Displays image
            ImageLoader image_loader = ImageLoader.getInstance();
            image_loader.displayImage(e.url(), (ImageView) convertView.findViewById(R.id.image));
            /*
             * If text included
             */
        } else {
            String short_description = e.desc().length() > 100 ? (e.desc().substring(0, 100) + "...") : e.desc();
            ((TextView) convertView.findViewById(R.id.name)).setText(e.name());
            ((TextView) convertView.findViewById(R.id.date)).setText(e.date());
            ((TextView) convertView.findViewById(R.id.description)).setText(short_description);
            // If image included, display it
            if (e.url() != "") {
                ImageLoader image_loader = ImageLoader.getInstance();
                image_loader.displayImage(e.url(), (ImageView) convertView.findViewById(R.id.image));
            }
        }
        return convertView;
    }
}
