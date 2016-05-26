package slugapp.com.ucscstudentapp.social;
//
//  InstagramFeedFragment.java
//  SlugRoute
//
//  Edited by Karol Josef Bustamante. <karoljosefb@gmail.com>
//  Copyright (c) 2015 UCSC Android. All rights reserved.
//

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.fragments.BaseFragment;
import slugapp.com.ucscstudentapp.social.instagram.Instagram;
import slugapp.com.ucscstudentapp.social.instagram.InstagramRequest;
import slugapp.com.ucscstudentapp.social.instagram.InstagramSession;
import slugapp.com.ucscstudentapp.social.instagram.InstagramUser;
import slugapp.com.ucscstudentapp.social.instagram.PhotoListAdapter;


public class InstagramFeedFragment extends BaseFragment {
    private InstagramSession mInstagramSession;
    private Instagram mInstagram;

    private ProgressBar mLoadingPb;
    private GridView mGridView;

    private static final String CLIENT_ID = "1022d9b4b6ae4b0297a0f1b5d1f711e0";
    private static final String CLIENT_SECRET = "1f67be1ee8e942578f2eb8d47d9ce9c7";
    private static final String REDIRECT_URI = "http://s3-us-west-1.amazonaws.com/slug-app/index.html";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstagram = new Instagram(getActivity(), CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
        mInstagramSession = mInstagram.getSession();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        //this.setLayout("Instagram", R.id.social_button);

        if (mInstagramSession.isActive()) {
            rootView = inflater.inflate(R.layout.activity_user, container, false);
            //InstagramUser instagramUser = mInstagramSession.getUser();

            mLoadingPb = (ProgressBar) rootView.findViewById(R.id.pb_loading);
            mGridView = (GridView) rootView.findViewById(R.id.gridView);

            rootView.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagramSession.reset();
                    InstagramFeedFragment fragment = new InstagramFeedFragment();
                    ac.setFragment(fragment);
                }
            });

            DisplayImageOptions displayOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_user)
                    .showImageForEmptyUri(R.drawable.ic_user)
                    .showImageOnFail(R.drawable.ic_user)
                    .cacheInMemory(true)
                    .cacheOnDisc(false)
                    .considerExifParams(true)
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                    .writeDebugLogs()
                    .defaultDisplayImageOptions(displayOptions)
                    .build();

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

            AnimateFirstDisplayListener animate = new AnimateFirstDisplayListener();
            new DownloadTask().execute();

        } else {
            rootView = inflater.inflate(R.layout.instagram_login, container, false);
            rootView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagram.authorize(mAuthListener);
                }
            });
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {
            InstagramFeedFragment fragment = new InstagramFeedFragment();
            ac.setFragment(fragment);
        }

        @Override
        public void onError(String error) {
            showToast(error);
        }

        @Override
        public void onCancel() {
        }
    };

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    public class DownloadTask extends AsyncTask<URL, Integer, Long> {
        private ArrayList<String> photoList;

        protected void onCancelled() {
        }

        protected void onPreExecute() {
        }

        protected Long doInBackground(URL... urls) {
            long result = 0;

            try {
                List<NameValuePair> params = new ArrayList<>(1);
                params.add(new BasicNameValuePair("count", "10"));

                InstagramRequest request = new InstagramRequest(mInstagramSession.getAccessToken());
                String response = request.requestGet("/tags/ucsc/media/recent", params);
                if (response.equals("")) return result;

                JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                JSONArray jsonData = jsonObj.getJSONArray("data");

                int length = jsonData.length();
                if (length <= 0) return result;

                photoList = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonImages = jsonData.getJSONObject(i).getJSONObject("images");
                    JSONObject jsonPhoto = jsonImages.getJSONObject("low_resolution");

                    photoList.add(jsonPhoto.getString("url"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
            mLoadingPb.setVisibility(View.GONE);

            if (photoList == null) {
                Toast.makeText(getActivity(), "No Photos Available", Toast.LENGTH_LONG).show();
                return;
            }

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            int length = (int) Math.ceil((double) dm.widthPixels / 2) - 50;

            PhotoListAdapter adapter = new PhotoListAdapter(getActivity());
            adapter.setData(photoList);
            adapter.setLayoutParam(length, length);
            mGridView.setAdapter(adapter);
        }
    }
}
