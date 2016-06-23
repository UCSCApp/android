package slugapp.com.sluglife.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.AttributeEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh on 9/23/2015
 */
public class MapFilterDialogFragment extends DialogFragment {
    private Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater li = LayoutInflater.from(this.mContext);
        final View mapFilterView = li.inflate(R.layout.dialog_map_filter, null);
        final CheckBox diningCheckBox = (CheckBox) mapFilterView.findViewById(
                R.id.checkbox_dining_halls);
        final CheckBox libraryCheckBox = (CheckBox) mapFilterView.findViewById(
                R.id.checkbox_libraries);

        int pref = this.getArguments().getInt("checkbox");
        if ((pref & 0b01) != 0b00) diningCheckBox.setChecked(true);
        if ((pref & 0b10) != 0b00) libraryCheckBox.setChecked(true);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Choose What Locations To Show")
                .setView(mapFilterView)

                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        int bin = 0b00;
                        if (diningCheckBox.isChecked()) bin = bin | 0b01;
                        if (libraryCheckBox.isChecked()) bin = bin | 0b10;

                        Intent intent = new Intent();
                        intent.putExtra("checkbox", bin);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);
                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }
}
