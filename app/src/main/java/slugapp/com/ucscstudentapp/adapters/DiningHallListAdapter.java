package slugapp.com.ucscstudentapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.models.StringListItem;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class DiningHallListAdapter extends BaseListAdapter {
    public DiningHallListAdapter(Context context) {
        super(context, R.layout.item_facilities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_facilities, null);
        }
        String diningHall = ((StringListItem) getItem(position)).string;
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(diningHall);
        return convertView;
    }
}
