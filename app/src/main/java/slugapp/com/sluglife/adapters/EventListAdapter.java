package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.http.ImageHttpRequest;
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
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, null);
        }

        Event e = (Event) getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView desc = (TextView) convertView.findViewById(R.id.description);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        name.setText(e.name);
        date.setText(e.date.string);
        desc.setText(e.getShortSummary());
        if (!e.image.isEmpty()) new ImageHttpRequest(e.image).execute(image);
        else image.setVisibility(View.INVISIBLE);

        return convertView;
    }
}
