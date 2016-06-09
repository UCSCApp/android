package slugapp.com.ucscstudentapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.models.Food;
import slugapp.com.ucscstudentapp.enums.AttributeEnum;
import slugapp.com.ucscstudentapp.models.FoodMenu;

/**
 * Created by isayyuhh_s on 8/8/2015.
 */
public class DiningHallDetailFragment extends BaseDetailFragment {
    private FoodMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.menu = this.mCallback.getGson().fromJson(b.getString("json"), FoodMenu.class);
        this.setLayout(b.getString("name"), R.id.dining_button);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dining_hall, container, false);
        this.setView(view);

        return view;
    }

    @Override
    protected void setView(final View view) {
        final TextView dateTv = (TextView) view.findViewById(R.id.date);
        String date = mCallback.getToday().getMonth() + " " + mCallback.getToday().getDay();
        dateTv.setText(date);
        TableLayout layout = (TableLayout) view.findViewById(R.id.meal);
        setMenu(view, this.menu, layout);
        setLegendDialog(view);
    }

    private void setMenu(View view, FoodMenu menu, TableLayout table) {
        if (menu.isEmpty()) {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.table);
            layout.setVisibility(View.GONE);
            TextView failed = (TextView) view.findViewById(R.id.failed);
            failed.setVisibility(View.VISIBLE);
            return;
        }

        // for each food item
        for (Food food : menu.getItems()) {
            TableRow row = new TableRow(getActivity());
            TextView name = new TextView(getActivity());
            LinearLayout attributes = new LinearLayout(getActivity());

            // params
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(75, 75);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(0,
                    TableRow.LayoutParams.WRAP_CONTENT, 1);
            rowParams.setMargins(12, 24, 5, 0);

            // food name
            name.setText(food.name());
            name.setTextColor(Color.BLACK);
            name.setTextSize(14.0f);

            // food attributes
            attributes.setOrientation(LinearLayout.HORIZONTAL);
            for (AttributeEnum attribute : food.attributes()) {
                ImageView icon = new ImageView(getActivity());
                icon.setLayoutParams(iconParams);
                icon.setImageResource(attribute.getIcon());
                attributes.addView(icon);
            }

            // set params
            row.addView(name, 0, rowParams);
            row.addView(attributes, 1, rowParams);
            table.addView(row);
        }
    }

    private void setLegendDialog(View view) {
        // set legend OnClickListener
        ImageView legend = (ImageView) view.findViewById(R.id.legend);
        legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) ft.remove(prev);
                ft.addToBackStack(null);

                // Create and show the dialog.
                DiningLegendDialogFragment dialog = new DiningLegendDialogFragment();
                dialog.show(ft, "dialog");
            }
        });

    }
}
