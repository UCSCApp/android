package slugapp.com.ucscstudentapp.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import slugapp.com.ucscstudentapp.event.EventListFragment;
import slugapp.com.ucscstudentapp.dining.DiningHallGridFragment;
import slugapp.com.ucscstudentapp.map.MapFragment;
import slugapp.com.ucscstudentapp.settings.Settings;
import slugapp.com.ucscstudentapp.social.joint_login_social_fragment;

/**
 * Created by isayyuhh_s on 7/28/2015.
 */
public class BottomToolbarButton extends ImageButton {
    private static int eventID, diningID, mapID, socialID, settingsID;
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

    public static void setIds(BottomToolbarButton event_button, BottomToolbarButton dining_button,
                              BottomToolbarButton map_button) {
        //, ToggleImageButton social_button, ToggleImageButton settings_button) {
        eventID = event_button.getId();
        diningID = dining_button.getId();
        mapID = map_button.getId();

        /*
        socialID = social_button.getId();
        settingsID = settings_button.getId();
        */
    }

    private final OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == eventID) {
                EventListFragment fragment = new EventListFragment();
                ac.setFragment(fragment);
            } else if (view.getId() == diningID) {
                DiningHallGridFragment fragment = new DiningHallGridFragment();
                ac.setFragment(fragment);
            } else if (view.getId() == mapID) {
                MapFragment fragment = new MapFragment();
                ac.setFragment(fragment);
            } else if (view.getId() == socialID) {
                joint_login_social_fragment fragment = new joint_login_social_fragment();
                ac.setFragment(fragment);
                /*
                //this is if we want to implement logging users in so they can like, favorite, post, etc.
                if() {
                    FragmentTransaction ft = ac.fm().beginTransaction();
                    TwitterLoginFragment llf = new TwitterLoginFragment();
                    ft.replace(R.id.listFragment, llf);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                */
            } else if (view.getId() == settingsID) {
                Settings fragment = new Settings();
                ac.setFragment(fragment);
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(onClickListener);
    }
}
