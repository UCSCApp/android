package slugapp.com.ucscstudentapp.models;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Constructor;

import slugapp.com.ucscstudentapp.enums.FragmentEnum;
import slugapp.com.ucscstudentapp.fragments.DiningHallListFragment;
import slugapp.com.ucscstudentapp.fragments.EventListFragment;
import slugapp.com.ucscstudentapp.fragments.MapFragment;
import slugapp.com.ucscstudentapp.interfaces.ActivityCallback;
import slugapp.com.ucscstudentapp.settings.SettingsFragment;
import slugapp.com.ucscstudentapp.social.InstagramFeedFragment;

/**
 * Created by isayyuhh_s on 7/28/2015.
 */
public class BottomToolbarButton extends ImageButton {
    private ActivityCallback ac;

    public BottomToolbarButton(Context context) {
        super(context);
        this.ac = (ActivityCallback) context;
    }

    public BottomToolbarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ac = (ActivityCallback) context;
    }

    public BottomToolbarButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.ac = (ActivityCallback) context;
    }

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                for (FragmentEnum fragmentEnum : ac.getFragments()) {
                    if (view.getId() != fragmentEnum.getButtonId()) continue;
                    Class<?> fragmentClass = fragmentEnum.getFragment();
                    Constructor<?> fragmentConstructor = fragmentClass.getConstructor();
                    Fragment fragment = (Fragment) fragmentConstructor.newInstance();
                    ac.setFragment(fragment);
                }
            } catch (Exception e) {
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(onClickListener);
    }
}
