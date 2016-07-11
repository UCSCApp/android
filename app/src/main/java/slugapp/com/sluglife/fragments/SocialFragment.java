package slugapp.com.sluglife.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ListSocialBinding;
import slugapp.com.sluglife.enums.FragmentEnum;
import slugapp.com.sluglife.models.BaseObject;

/**
 * Created by isayyuhh on 6/12/16
 */
public class SocialFragment extends BaseListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.SOCIAL;

    private ListSocialBinding mBinding;

    public static SocialFragment newInstance() {
        return new SocialFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.list_social, container, false);

        this.setListFragment(FRAGMENT, this.mBinding.list,
                new TweetTimelineListAdapter.Builder(this.mContext)
                        .setTimeline(new SearchTimeline.Builder()
                                .query(this.mContext.getString(R.string.social_hashtag_ucsc))
                                .build())
                        .build());

        return this.mBinding.getRoot();
    }

    @Override
    protected void setArgumentFields(Bundle b) {
    }

    @Override
    protected void setFields() {
    }

    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return 0;
    }

    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
