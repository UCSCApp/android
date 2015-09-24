package slugapp.com.ucscstudentapp.social;

//
//  TwitterTimelineFragment.java
//  SlugRoute
//
//  Created by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.ActivityCallback;

//http://docs.fabric.io/android/twitter/show-timelines.html


public class TwitterTimelineFragment extends ListFragment {

    private ActivityCallback mListener;

    // TODO: Rename and change types and number of parameters
    public static TwitterTimelineFragment newInstance(String param1, String param2) {
        TwitterTimelineFragment fragment = new TwitterTimelineFragment();

        return fragment;
    }

    public TwitterTimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#UCSC")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(searchTimeline)
                .build();
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_timeline, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (ActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ActivityReference");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
