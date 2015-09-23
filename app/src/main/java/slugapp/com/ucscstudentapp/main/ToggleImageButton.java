package slugapp.com.ucscstudentapp.main;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.event.EventList;
import slugapp.com.ucscstudentapp.dining.DiningHallGrid;
import slugapp.com.ucscstudentapp.map.Map;
import slugapp.com.ucscstudentapp.settings.Settings;
import slugapp.com.ucscstudentapp.social.joint_login_social_fragment;

/**
 * Created by isayyuhh_s on 7/28/2015.
 */
public class ToggleImageButton extends ImageButton {
    private static int eventID, diningID, mapID, socialID, settingsID;
    private ActivityReference mCallBack;

    public ToggleImageButton(Context context) {
        super(context);
        this.mCallBack = (ActivityReference) context;
    }

    public ToggleImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCallBack = (ActivityReference) context;
    }

    public ToggleImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCallBack = (ActivityReference) context;
    }

    public static void setIds(ToggleImageButton event_button, ToggleImageButton dining_button,
                              ToggleImageButton map_button) {
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
            FragmentTransaction ft = mCallBack.fm().beginTransaction();

            if (view.getId() == eventID) {
                EventList fragment = new EventList();
                ft.replace(R.id.listFragment, fragment);
            }
            else if (view.getId() == diningID) {
                DiningHallGrid fragment = new DiningHallGrid();
                ft.replace(R.id.listFragment, fragment);
            }
            else if (view.getId() == mapID) {
                Map fragment = new Map();
                ft.replace(R.id.listFragment, fragment);
            }
            else if (view.getId() == socialID) {
                //Can be used to check if there is a TwitterSession currently running
                /*TwitterSession session = Twitter.getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;*/

                //this is to view the tweets with "#UCSC". It requires no login just
                //the Twitter Authorization done in MainActivity
                    joint_login_social_fragment fragment = new joint_login_social_fragment();
                    ft.replace(R.id.listFragment, fragment);
                /*
                //this is if we want to implement logging users in so they can like, favorite, post, etc.
                if() {
                    FragmentTransaction ft = mCallBack.fm().beginTransaction();
                    TwitterLoginFragment llf = new TwitterLoginFragment();
                    ft.replace(R.id.listFragment, llf);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                */
            }
            else if (view.getId() == settingsID) {
                Settings fragment = new Settings();
                ft.replace(R.id.listFragment, fragment);
            }
            ft.addToBackStack(null);
            ft.commit();
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnClickListener(onClickListener);
    }
}
