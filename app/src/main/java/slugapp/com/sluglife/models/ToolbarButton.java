package slugapp.com.sluglife.models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

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
