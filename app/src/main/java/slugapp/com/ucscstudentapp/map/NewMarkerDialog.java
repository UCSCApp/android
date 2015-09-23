package slugapp.com.ucscstudentapp.map;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import slugapp.com.ucscstudentapp.R;

/**
 * Created by isayyuhh_s on 8/17/2015.
 */
public class NewMarkerDialog extends DialogFragment {
    private DialogCallback origFragment;
    private Bundle potentialMarker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        origFragment = (DialogCallback) getTargetFragment();
        potentialMarker = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_new_map_marker, container, false);
        final Button enter = (Button) view.findViewById(R.id.enter);
        final Button cancel = (Button) view.findViewById(R.id.cancel);
        final EditText et = (EditText) view.findViewById(R.id.edit_text);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().toString().trim().length() > 0) {
                    try {
                        origFragment.createMarker(et.getText().toString().trim(),
                                potentialMarker.getFloat("LAT"),
                                potentialMarker.getFloat("LNG"));
                    } catch (IOException ioe) {
                    }
                }
                getActivity().onBackPressed();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    enter.performClick();
                    return true;
                }
                return false;
            }
        });
        getDialog().setTitle("Add new Marker");
        return view;
    }
}

/**
 * Interface for Dialog to communicate with Fragment
 */
interface DialogCallback {
    void createMarker(String title, float lat, float lng) throws IOException;
}
