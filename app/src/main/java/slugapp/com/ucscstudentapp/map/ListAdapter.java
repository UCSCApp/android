package slugapp.com.ucscstudentapp.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class ListAdapter extends ArrayAdapter<String> {
    public ListAdapter(Context context) {
        super(context, R.layout.item_facilities);
    }

    public void setData(String[] facilities) {
        clear();
        for (String e : facilities) {
            add(e);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_facilities, null);
        }
        String facility = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(facility);
        return convertView;
    }
}
