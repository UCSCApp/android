package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.http.ImageHttpRequest;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.Event;

/**
 * Created by simba on 5/31/15
 * Edited by isaiah on 6/27/2015
 * <p/>
 * This file contains an adapter that displays a list of events.
 */
public class EventListAdapter extends BaseListAdapter {
    private static final int id = R.layout.item_event;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public EventListAdapter(Context context) {
        super(context, id);
    }

    /**
     * Gets view of item showing on screen
     *
     * @param position    Position of object on list
     * @param convertView View of list item
     * @param parent      Parent of list item view
     * @return Inflated view with contents
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, null);
        }

        Event event = (Event) this.getItem(position);

        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.summary = (TextView) convertView.findViewById(R.id.summary);
        holder.image = (ImageView) convertView.findViewById(R.id.image);
        convertView.setTag(holder);

        holder.name.setText(event.getShortName());
        holder.date.setText(event.getFullDate());
        holder.summary.setText(event.getShortSummary());
        new ImageHttpRequest(event.image).execute(holder.image);

        if (Date.compareDates(event.date, Date.getToday()) < 1) {
            holder.name.setTextColor(Color.GRAY);
            holder.date.setTextColor(Color.GRAY);
            holder.summary.setTextColor(Color.GRAY);
        }

        return convertView;
    }

    /**
     * Class containing views in list item
     */
    private static class ViewHolder {
        public TextView name;
        public TextView date;
        public TextView summary;
        public ImageView image;
    }
}
