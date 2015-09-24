package slugapp.com.ucscstudentapp.dining;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class DiningHallGridAdapter extends ArrayAdapter<Bitmap> {
    public DiningHallGridAdapter(Context context) {
        super(context, R.layout.item_facilities);
    }

    public void setData(List<Bitmap> diningHalls) {
        clear();
        for (Bitmap b : diningHalls) {
            add(b);
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
        Bitmap bitmap = getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.name);
        imageView.setImageBitmap(bitmap);
        imageView.setAdjustViewBounds(true);
        return convertView;
    }
}
