package slugapp.com.sluglife.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.DialogMapFilterBinding;

/**
 * Created by isaiah on 9/23/2015
 * <p>
 * This file contains a dialog fragment that allows the user to control which google map markers
 * appear on the google map.
 */
public class MapFilterDialogFragment extends BaseDialogFragment {
    private static final int DEFAULT = 0;
    private static final int LOOP_BIN = 0b000001;
    private static final int DINING_BIN = 0b000100;
    private static final int LIBRARY_BIN = 0b001000;

    private DialogMapFilterBinding mBinding;

    /**
     * Gets a new instance of fragment
     *
     * @param bin Activity context
     * @return New instance of fragment
     */
    public static MapFilterDialogFragment newInstance(Context context, int bin) {
        MapFilterDialogFragment dialog = new MapFilterDialogFragment();

        Bundle b = new Bundle();
        b.putInt(context.getString(R.string.bundle_markers), bin);
        dialog.setArguments(b);

        return dialog;
    }

    /**
     * Fragment's onCreateDialog method
     *
     * @param savedInstanceState Saved instance state
     * @return Created dialog
     */
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.dialog_map_filter, null, false);

        int pref = this.getArguments().getInt(this.mContext.getString(R.string.bundle_markers));

        this.setCheckBox(pref, LOOP_BIN, this.mBinding.checkboxLoopBuses,
                this.mBinding.textviewLoopBuses);
        this.setCheckBox(pref, DINING_BIN, this.mBinding.checkboxDiningHalls,
                this.mBinding.textviewDiningHalls);
        this.setCheckBox(pref, LIBRARY_BIN, this.mBinding.checkboxLibraries,
                this.mBinding.textviewLibraries);

        return new AlertDialog.Builder(this.getActivity())
                .setTitle(this.mContext.getString(R.string.title_map_dialog_filter))
                .setView(this.mBinding.getRoot())

                // Positive button
                .setPositiveButton(this.mContext.getString(R.string.dialog_positive),
                        new DialogInterface.OnClickListener() {

                    /**
                     * On positive button click
                     *
                     * @param dialog Dialog
                     * @param which Which button was clicked
                     */
                    public void onClick(DialogInterface dialog, int which) {
                        int bin = 0;
                        if (mBinding.checkboxLoopBuses.isChecked()) bin = bin | LOOP_BIN;
                        if (mBinding.checkboxDiningHalls.isChecked()) bin = bin | DINING_BIN;
                        if (mBinding.checkboxLibraries.isChecked()) bin = bin | LIBRARY_BIN;

                        Intent intent = new Intent();
                        intent.putExtra(mContext.getString(R.string.bundle_markers), bin);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), DEFAULT,
                                intent);
                    }
                })

                // Negative Button
                .setNegativeButton(this.mContext.getString(R.string.dialog_negative),
                        new DialogInterface.OnClickListener() {

                    /**
                     * On negative button click
                     *
                     * @param dialog Dialog
                     * @param which Which button was clicked
                     */
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
    }

    /**
     * Sets checkbox and textview
     *
     * @param checkbox Checkbox
     * @param textView Textview
     */
    private void setCheckBox(int pref, int bin, final CheckBox checkbox, TextView textView) {
        if ((pref & bin) != DEFAULT) checkbox.setChecked(true);

        textView.setOnClickListener(new View.OnClickListener() {

            /**
             * On click
             *
             * @param v View
             */
            @Override
            public void onClick(View v) {
                checkbox.setChecked(!checkbox.isChecked());
            }
        });
    }
}
