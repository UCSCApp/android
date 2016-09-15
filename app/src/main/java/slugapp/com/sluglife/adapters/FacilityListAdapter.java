package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.objects.FacilityObject;

/**
 * Created by isaiah on 8/29/2015
 * <p/>
 * This file contains an adapter that displays a list of facilities.
 */
public class FacilityListAdapter extends BaseListAdapter {
    private static final int id = R.layout.item_facility;

    /**
     * Constructor
     *
     * @param context Activity context
     */
    public FacilityListAdapter(Context context) {
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

        FacilityObject facilityItem = ((FacilityObject) this.getItem(position));

        ViewHolder holder = new ViewHolder();
        holder.name =  (TextView) convertView.findViewById(R.id.name);
        convertView.setTag(holder);

        holder.name.setText(facilityItem.name);

        return convertView;
    }

    /**
     * Class containing views in list item
     */
    private static class ViewHolder {
        public TextView name;
    }
}
