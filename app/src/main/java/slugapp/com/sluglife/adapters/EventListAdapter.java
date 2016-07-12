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
 * Created by simba on 5/31/15.
 * <p/>
 * Edited by isaiah on 6/27/2015.
 * <p/>
 * This file is the Adapter for EventListFragment.java and displays each Event to the ListView.
 */
public class EventListAdapter extends BaseListAdapter {
    private static final int id = R.layout.item_event;

    public EventListAdapter(Context context) {
        super(context, id);
    }

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

    private static class ViewHolder {
        public TextView name;
        public TextView date;
        public TextView summary;
        public ImageView image;
    }
}
