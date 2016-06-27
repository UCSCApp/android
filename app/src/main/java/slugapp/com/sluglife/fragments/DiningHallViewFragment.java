package slugapp.com.sluglife.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.AttributeEnum;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.Date;
import slugapp.com.sluglife.models.Food;
import slugapp.com.sluglife.models.FoodMenu;

/**
 * Created by isayyuhh_s on 8/8/2015
 */
public class DiningHallViewFragment extends BaseViewFragment {
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

    private static final FragmentEnum fragmentEnum = FragmentEnum.DINING;

    private FoodMenu mFoodMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_dining, container, false);

        this.setViewFragment(view, fragmentEnum, this.mName);

        return view;
    }

    @Override
    protected void setArgumentFields(Bundle b) {
        this.mFoodMenu = this.mCallback.getGson().fromJson(b.getString(this.mContext.getString(R.string.bundle_json)), FoodMenu.class);
        this.mName = b.getString(this.mContext.getString(R.string.bundle_name));
    }

    @Override
    protected void setFields(View view) {
    }

    @Override
    protected void setView(final View view) {
        TextView dateTv = (TextView) view.findViewById(R.id.date);
        String date = Date.getToday().month + " " + Date.getToday().day;
        dateTv.setText(date);

        this.setMenu(view, this.mFoodMenu, (TableLayout) view.findViewById(R.id.meal));
    }

    private void setMenu(View view, FoodMenu menu, TableLayout table) {
        if (menu.isEmpty()) {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.meal);
            TextView failed = (TextView) view.findViewById(R.id.failed);

            layout.setVisibility(View.GONE);
            failed.setVisibility(View.VISIBLE);
            return;
        }

        // for each food item
        for (Food food : menu.getItems()) {
            TableRow row = new TableRow(this.mContext);
            TextView name = new TextView(this.mContext);
            LinearLayout attributes = new LinearLayout(this.mContext);

            // params
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    LINEAR_LAYOUT_PARAMS, LINEAR_LAYOUT_PARAMS);
            TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TABLE_ROW_INIT_WIDTH,
                    TableRow.LayoutParams.WRAP_CONTENT, TABLE_ROW_INIT_WEIGHT);
            rowParams.setMargins(TABLE_ROW_LEFT, TABLE_ROW_TOP, TABLE_ROW_RIGHT, TABLE_ROW_BOTTOM);

            // food name
            name.setText(food.name);
            name.setTextColor(Color.BLACK);
            name.setTextSize(TEXT_SIZE);

            // food attributes
            attributes.setOrientation(LinearLayout.HORIZONTAL);
            for (AttributeEnum attribute : food.attributes) {
                ImageView icon = new ImageView(this.mContext);

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
}
