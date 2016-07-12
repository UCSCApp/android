package slugapp.com.sluglife.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import slugapp.com.sluglife.R;

/**
 * Created by isayyuhh on 9/23/2015
 */
public class MapFilterDialogFragment extends BaseDialogFragment {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(this.mContext);
        final View mapFilterView = li.inflate(R.layout.dialog_map_filter, null);
        final CheckBox loopCheckBox = (CheckBox) mapFilterView.findViewById(
                R.id.checkbox_loop_buses);
        final CheckBox diningCheckBox = (CheckBox) mapFilterView.findViewById(
                R.id.checkbox_dining_halls);
        final CheckBox libraryCheckBox = (CheckBox) mapFilterView.findViewById(
                R.id.checkbox_libraries);
        final TextView loopTextView = (TextView) mapFilterView.findViewById(
                R.id.textview_loop_buses);
        final TextView diningTextView = (TextView) mapFilterView.findViewById(
                R.id.textview_dining_halls);
        final TextView libraryTextView = (TextView) mapFilterView.findViewById(
                R.id.textview_libraries);

        loopTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loopCheckBox.setChecked(!loopCheckBox.isChecked());
            }
        });
        diningTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diningCheckBox.setChecked(!diningCheckBox.isChecked());
            }
        });
        libraryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libraryCheckBox.setChecked(!libraryCheckBox.isChecked());
            }
        });

        int pref = this.getArguments().getInt(this.mContext.getString(R.string.bundle_markers));
        if ((pref & 0b000001) != 0) loopCheckBox.setChecked(true);
        if ((pref & 0b000100) != 0) diningCheckBox.setChecked(true);
        if ((pref & 0b001000) != 0) libraryCheckBox.setChecked(true);

        return new AlertDialog.Builder(getActivity())
                .setTitle(this.mContext.getString(R.string.title_map_dialog_filter))
                .setView(mapFilterView)

                // Positive button
                .setPositiveButton(this.mContext.getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        int bin = 0;
                        if (loopCheckBox.isChecked()) bin = bin | 0b000001;
                        if (diningCheckBox.isChecked()) bin = bin | 0b000100;
                        if (libraryCheckBox.isChecked()) bin = bin | 0b001000;

                        Intent intent = new Intent();
                        intent.putExtra(mContext.getString(R.string.bundle_markers), bin);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);
                    }
                })

                // Negative Button
                .setNegativeButton(this.mContext.getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                    }
                }).create();
    }
}
