package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.models.StringObject;

/**
 * Created by isayyuhh_s on 8/29/2015
 */
public class FacilityListAdapter extends BaseListAdapter {
    private static final int id = R.layout.item_facility;

    public FacilityListAdapter(Context context) {
        super(context, id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, null);
        }

        String facilityItem = ((StringObject) this.getItem(position)).string;

        ViewHolder holder = new ViewHolder();
        holder.name =  (TextView) convertView.findViewById(R.id.name);
        convertView.setTag(holder);

        holder.name.setText(facilityItem);

        return convertView;
    }

    private static class ViewHolder {
        public TextView name;
    }
}
