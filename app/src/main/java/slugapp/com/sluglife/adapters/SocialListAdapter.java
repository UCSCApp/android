package slugapp.com.sluglife.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.models.StringObject;

/**
 * Created by isayyuhh_s on 8/29/2015.
 */
public class SocialListAdapter extends BaseListAdapter {
    private static final int id = R.layout.item_social;

    public SocialListAdapter(Context context) {
        super(context, id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(id, null);
        }

        String text = ((StringObject) this.getItem(position)).getString();
        TextView tv = (TextView) convertView.findViewById(R.id.text);
        tv.setText(text);

        return convertView;
    }
}
