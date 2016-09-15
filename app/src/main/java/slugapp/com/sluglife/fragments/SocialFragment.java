package slugapp.com.sluglife.fragments;

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
import slugapp.com.sluglife.objects.BaseObject;

/**
 * Created by isaiah on 6/12/16
 * <p/>
 * This file contains a list fragment that displays a list of twitter tweets.
 */
public class SocialFragment extends BaseListFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.SOCIAL;

    private ListSocialBinding mBinding;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static SocialFragment newInstance() {
        return new SocialFragment();
    }

    /**
     * Fragment's onCreateView method
     *
     * @param inflater           Layout inflater
     * @param container          Container of fragment
     * @param savedInstanceState Saved instance state
     * @return Inflated view
     */
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

    /**
     * Sets fields from fragment arguments
     *
     * @param b Bundle from fragment arguments
     */
    @Override
    protected void setArgumentFields(Bundle b) {
    }

    /**
     * Sets fields
     */
    @Override
    protected void setFields() {
    }

    /**
     * Sorts list
     *
     * @param lhs Left operand
     * @param rhs Right operand
     * @return Integer showing which order the operands are in
     */
    @Override
    protected int doSort(BaseObject lhs, BaseObject rhs) {
        return 0;
    }

    /**
     * Does action on list item click
     *
     * @param parent   Parent view of list item
     * @param view     List item view
     * @param position List item position
     * @param id       Id of list item
     */
    @Override
    protected void onClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
