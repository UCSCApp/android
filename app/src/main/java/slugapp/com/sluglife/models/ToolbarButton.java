package slugapp.com.sluglife.models;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.interfaces.ActivityCallback;

/**
 * Created by isayyuhh_s on 7/28/2015.
 */
public class ToolbarButton extends ImageButton {
    private static final List<FragmentEnum> sTabFragments = Arrays.asList(FragmentEnum.values());

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
            for (FragmentEnum fragmentEnum : sTabFragments) {
                if (view.getId() != fragmentEnum.buttonId) continue;
                mCallback.setFragment(getTabFragment(fragmentEnum));
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setOnClickListener(this.onClickListener);
    }

    private Fragment getTabFragment(FragmentEnum fragmentEnum) {
        try {
            Class<?> fragmentClass = fragmentEnum.fragment;
            Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
            return (Fragment) fragmentConstructor.newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
