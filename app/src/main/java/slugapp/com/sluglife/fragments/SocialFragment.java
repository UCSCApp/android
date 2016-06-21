package slugapp.com.sluglife.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by isayyuhh on 6/12/16
 */
public class SocialFragment extends BaseListFragment {
    private FragmentEnum fragmentEnum = FragmentEnum.SOCIAL;

    private SearchTimeline mTimeline;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_social, container, false);

        this.setListFragment(view, this.fragmentEnum,
                new TweetTimelineListAdapter.Builder(this.mContext)
                .setTimeline(this.mTimeline)
                .build());

        return view;
    }

    @Override
    protected void setFields(View view) {
        this.mTimeline = new SearchTimeline.Builder()
                .query(this.mContext.getString(R.string.social_hashtag_ucsc))
                .build();
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return 0;
    }

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
