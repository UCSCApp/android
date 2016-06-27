package slugapp.com.sluglife.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.AttributeEnum;

/**
 * Created by isayyuhh on 9/23/2015
 */
public class DiningLegendDialogFragment extends BaseDialogFragment {
    private static final int LINEAR_LAYOUT_PARAMS = 75;

    private static final int ICON_LEFT = 12;
    private static final int ICON_TOP = 24;
    private static final int ICON_RIGHT = 5;
    private static final int ICON_BOTTOM = 0;

    private static final int TEXTVIEW_LEFT = 12;
    private static final int TEXTVIEW_TOP = 24;
    private static final int TEXTVIEW_RIGHT = 5;
    private static final int TEXTVIEW_BOTTOM = 0;

    private static final float TEXT_SIZE = 14.0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_dining_legend, container);

        this.setView(view);

        return view;
    }

    private void setView(View view) {
        LinearLayout list = (LinearLayout) view.findViewById(R.id.list);
        for (int i = 0; i < AttributeEnum.values().length; i++) {
            // legend params
            LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    LINEAR_LAYOUT_PARAMS,
                    LINEAR_LAYOUT_PARAMS);
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            iconParams.setMargins(ICON_LEFT, ICON_TOP, ICON_RIGHT, ICON_BOTTOM);
            tvParams.setMargins(TEXTVIEW_LEFT, TEXTVIEW_TOP, TEXTVIEW_RIGHT, TEXTVIEW_BOTTOM);

            // linear layout
            LinearLayout ll = new LinearLayout(this.mContext);
            ll.setLayoutParams(llparams);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // icon
            ImageView icon = new ImageView(this.mContext);
            icon.setLayoutParams(iconParams);
            icon.setImageResource(AttributeEnum.values()[i].getIcon());
            ll.addView(icon);

            // text view
            TextView tv = new TextView(this.mContext);
            tv.setText(AttributeEnum.values()[i].getString());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(TEXT_SIZE);
            tv.setLayoutParams(tvParams);
            ll.addView(tv);

            list.addView(ll);
        }
    }
}
