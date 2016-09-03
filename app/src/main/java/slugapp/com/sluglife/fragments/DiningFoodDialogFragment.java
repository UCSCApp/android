package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.DialogDiningFoodBinding;
import slugapp.com.sluglife.enums.AttributeEnum;
import slugapp.com.sluglife.models.FoodObject;

/**
 * Created by isaiah on 9/23/2015
 * <p/>
 * This file contains a dialog fragment that displays a legend of dining hall attributes.
 */
public class DiningFoodDialogFragment extends BaseDialogFragment {
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

    private DialogDiningFoodBinding mBinding;

    private FoodObject food;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static DiningFoodDialogFragment newInstance(Context context, FoodObject food) {
        DiningFoodDialogFragment fragment = new DiningFoodDialogFragment();

        Bundle b = new Bundle();
        b.putSerializable(context.getString(R.string.bundle_json), food);
        fragment.setArguments(b);

        return fragment;
    }

    /**
     * Fragment's onCreate method
     *
     * @param savedInstanceState Saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getArguments();
        this.food = (FoodObject) b.getSerializable(this.mContext.getString(R.string.bundle_json));
    }

    /**
     * Fragment's onCreateView method
     *
     * @param inflater           Layout inflater
     * @param container          Container of fragment
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.dialog_dining_food, container, false);

        this.setView();

        return this.mBinding.getRoot();
    }

    /**
     * Sets fragment view
     */
    private void setView() {
        this.mBinding.foodName.setText(food.name);

        if (food.attributes.isEmpty()) this.mBinding.list.setVisibility(View.GONE);

        for (AttributeEnum attribute : food.attributes) {
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                    LINEAR_LAYOUT_PARAMS,
                    LINEAR_LAYOUT_PARAMS);
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            imageViewParams.setMargins(ICON_LEFT, ICON_TOP, ICON_RIGHT, ICON_BOTTOM);
            textViewParams.setMargins(TEXTVIEW_LEFT, TEXTVIEW_TOP, TEXTVIEW_RIGHT, TEXTVIEW_BOTTOM);

            LinearLayout linearLayout = new LinearLayout(this.mContext);
            linearLayout.setLayoutParams(linearLayoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView icon = new ImageView(this.mContext);
            icon.setLayoutParams(imageViewParams);
            icon.setImageResource(attribute.icon);
            linearLayout.addView(icon);

            TextView textView = new TextView(this.mContext);
            textView.setText(this.mContext.getString(attribute.name));
            textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.ucsc_blue));
            textView.setTextSize(TEXT_SIZE);
            textView.setLayoutParams(textViewParams);
            linearLayout.addView(textView);

            this.mBinding.list.addView(linearLayout);
        }
    }
}
