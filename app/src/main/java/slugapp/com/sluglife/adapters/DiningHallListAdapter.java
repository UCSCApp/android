package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.models.StringListItem;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class DiningHallListAdapter extends BaseListAdapter {
    private static final int id = R.layout.item_facilities;

    public DiningHallListAdapter(Context context) {
        super(context, id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, null);
        }

        String diningHall = ((StringListItem) this.getItem(position)).getString();
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(diningHall);

        return convertView;
    }
}
