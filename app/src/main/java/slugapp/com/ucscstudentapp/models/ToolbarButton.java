package slugapp.com.ucscstudentapp.models;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Constructor;

import slugapp.com.ucscstudentapp.enums.FragmentEnum;
import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;

/**
 * Created by isayyuhh_s on 7/28/2015.
 */
public class ToolbarButton extends ImageButton {
    private ActivityCallback mCallback;

    public ToolbarButton(Context context) {
        super(context);
        this.mCallback = (ActivityCallback) context;
    }

    public ToolbarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCallback = (ActivityCallback) context;
    }

    public ToolbarButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCallback = (ActivityCallback) context;
    }

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            for (FragmentEnum fragmentEnum : mCallback.getFragments()) {
                if (view.getId() != fragmentEnum.getButtonId()) continue;
                mCallback.setFragment(mCallback.getTabFragment(fragmentEnum));
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(onClickListener);
    }
}
