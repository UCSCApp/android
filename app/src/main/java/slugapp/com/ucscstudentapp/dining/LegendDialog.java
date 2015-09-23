package slugapp.com.ucscstudentapp.dining;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh on 9/23/2015.
 */
public class LegendDialog extends DialogFragment {

    public LegendDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_dining_hall_legend, container);
        getDialog().setTitle("Legend");
        setList(view);
        return view;
    }

    private void setList(View view) {
        LinearLayout list = (LinearLayout) view.findViewById(R.id.list);
        for (int i = 0; i < DiningHall.Attribute.values().length; i++) {
            // legend params
            LinearLayout.LayoutParams llparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(75, 75);
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            iconParams.setMargins(12, 10, 0, 10);
            tvParams.setMargins(100, 10, 8, 10);

            // linear layout
            LinearLayout ll = new LinearLayout(getActivity());
            ll.setLayoutParams(llparams);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // icon
            ImageView icon = new ImageView(getActivity());
            icon.setLayoutParams(iconParams);
            icon.setImageResource(DiningHall.Attribute.values()[i].getIcon());
            ll.addView(icon);

            // text view
            TextView tv = new TextView(getActivity());
            tv.setText(DiningHall.Attribute.values()[i].getString());
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(14.0f);
            tv.setLayoutParams(tvParams);
            ll.addView(tv);

            list.addView(ll);
        }
    }
}
