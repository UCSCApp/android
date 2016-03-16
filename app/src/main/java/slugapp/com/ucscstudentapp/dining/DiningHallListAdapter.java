package slugapp.com.ucscstudentapp.dining;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class DiningHallListAdapter extends ArrayAdapter<String> {
    public DiningHallListAdapter(Context context) {
        super(context, R.layout.item_facilities);
    }

    public void setData(List<String> diningHalls) {
        clear();
        for (String s : diningHalls) {
            add(s);
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
        String diningHall = getItem(position);
        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(diningHall);
        return convertView;
    }
}
