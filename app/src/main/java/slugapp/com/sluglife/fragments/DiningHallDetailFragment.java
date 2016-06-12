package slugapp.com.sluglife.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.models.Food;
import slugapp.com.sluglife.enums.AttributeEnum;
import slugapp.com.sluglife.models.FoodMenu;

/**
 * Created by isayyuhh_s on 8/8/2015
 */
public class DiningHallDetailFragment extends BaseDetailFragment {
    private static final int LINEAR_LAYOUT_PARAMS = 75;

    private static final int TABLE_ROW_INIT_WIDTH = 0;
    private static final int TABLE_ROW_INIT_WEIGHT = 1;

    private static final int TABLE_ROW_LEFT = 12;
    private static final int TABLE_ROW_TOP = 24;
    private static final int TABLE_ROW_RIGHT = 5;
    private static final int TABLE_ROW_BOTTOM = 0;

    private static final int TABLE_COLUMN_FOOD = 0;
    private static final int TABLE_COLUMN_ATTRIBUTES = 1;

    private static final float TEXT_SIZE = 14.0f;

    private String mName;
    private FoodMenu mMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        this.mName = b.getString(this.mContext.getString(R.string.name));
        this.mMenu = this.mCallback.getGson().fromJson(b.getString(this.mContext.getString(R.string.json)), FoodMenu.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_dining_hall, container, false);
        this.setLayout(this.mName, R.id.dining_button);
        this.setView(view);

        return view;
    }

    @Override
    protected void setView(final View view) {
        final TextView dateTv = (TextView) view.findViewById(R.id.date);

        String date = this.mCallback.getToday().getMonth() + " " + mCallback.getToday().getDay();
        TableLayout layout = (TableLayout) view.findViewById(R.id.meal);

        dateTv.setText(date);

        this.setMenu(view, this.mMenu, layout);
        this.setLegendDialog(view);
    }

    private void setMenu(View view, FoodMenu menu, TableLayout table) {
        if (menu.isEmpty()) {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.table);
            TextView failed = (TextView) view.findViewById(R.id.failed);

            layout.setVisibility(View.GONE);
            failed.setVisibility(View.VISIBLE);

            return;
        }

        // for each food item
        for (Food food : menu.getItems()) {
            TableRow row = new TableRow(getActivity());
            TextView name = new TextView(getActivity());
            LinearLayout attributes = new LinearLayout(getActivity());

            // params
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    LINEAR_LAYOUT_PARAMS, LINEAR_LAYOUT_PARAMS);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TABLE_ROW_INIT_WIDTH,
                    TableRow.LayoutParams.WRAP_CONTENT, TABLE_ROW_INIT_WEIGHT);
            rowParams.setMargins(TABLE_ROW_LEFT, TABLE_ROW_TOP, TABLE_ROW_RIGHT, TABLE_ROW_BOTTOM);

            // food name
            name.setText(food.getName());
            name.setTextColor(Color.BLACK);
            name.setTextSize(TEXT_SIZE);

            // food attributes
            attributes.setOrientation(LinearLayout.HORIZONTAL);
            for (AttributeEnum attribute : food.getAttributes()) {
                ImageView icon = new ImageView(getActivity());

                icon.setLayoutParams(iconParams);
                icon.setImageResource(attribute.getIcon());
                attributes.addView(icon);
            }

            // set params
            row.addView(name, TABLE_COLUMN_FOOD, rowParams);
            row.addView(attributes, TABLE_COLUMN_ATTRIBUTES, rowParams);
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
                Fragment prev = getFragmentManager().findFragmentByTag(mContext.getString(R.string.dialog));

                if (prev != null) ft.remove(prev);
                ft.addToBackStack(null);

                // Create and show the dialog.
                DiningLegendDialogFragment dialog = new DiningLegendDialogFragment();
                dialog.show(ft, mContext.getString(R.string.dialog));
            }
        });

    }
}
