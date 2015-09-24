package slugapp.com.ucscstudentapp.social;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import slugapp.com.ucscstudentapp.main.ActivityCallback;
import slugapp.com.ucscstudentapp.social.instagram.Instagram;
import slugapp.com.ucscstudentapp.social.instagram.InstagramRequest;
import slugapp.com.ucscstudentapp.social.instagram.InstagramSession;
import slugapp.com.ucscstudentapp.social.instagram.InstagramUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link joint_login_social_fragment} factory method to
 * create an instance of this fragment.
 */
public class joint_login_social_fragment extends android.support.v4.app.Fragment {
    private InstagramSession mInstagramSession;
    private Instagram mInstagram;
    private ActivityCallback mCallBack;
    //private TwitterLoginButton loginButton;
    private ProgressBar mLoadingPb;
    private GridView mGridView;

    private static final String CLIENT_ID = "2eb0b0a219ba482c9b9121c8287cc4aa";
    private static final String CLIENT_SECRET = "8ad1fd55cdbc483f9b7eafae79b47ed5";
    private static final String REDIRECT_URI = "http://slugroute.com";


    public joint_login_social_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Social");

        // Instagram Implementation
        mInstagram = new Instagram(getActivity(), CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);

        mInstagramSession = mInstagram.getSession();

    }

    /*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginButton = (TwitterLoginButton) getView().findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a session for making API calls
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView;

        if (mInstagramSession.isActive()) {
            rootView = inflater.inflate(R.layout.activity_user, container, false);
            //InstagramUser instagramUser = mInstagramSession.getUser();

            mLoadingPb = (ProgressBar) rootView.findViewById(R.id.pb_loading);
            mGridView = (GridView) rootView.findViewById(R.id.gridView);


            ((Button) rootView.findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagramSession.reset();
                    FragmentTransaction ft = mCallBack.fm().beginTransaction();
                    joint_login_social_fragment llf = new joint_login_social_fragment();
                    ft.replace(R.id.listFragment, llf);
                    ft.addToBackStack(null);
                    ft.commit();
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

        }
        else {
            rootView = inflater.inflate(R.layout.fragment_joint_login_social_fragment, container, false);
            ((Button) rootView.findViewById(R.id.btn_connect)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mInstagram.authorize(mAuthListener);
                }
            });
            ((Button) rootView.findViewById(R.id.twitter_login_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    FragmentTransaction ft = mCallBack.fm().beginTransaction();
                    TwitterTimelineFragment llf = new TwitterTimelineFragment();
                    ft.replace(R.id.listFragment, llf);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.events_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        getActivity().findViewById(R.id.map_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        //getActivity().findViewById(R.id.social_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        //getActivity().findViewById(R.id.settings_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_off));
        //getActivity().findViewById(R.id.social_button).setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_on));
    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    private Instagram.InstagramAuthListener mAuthListener = new Instagram.InstagramAuthListener() {
        @Override
        public void onSuccess(InstagramUser user) {
            FragmentTransaction ft = mCallBack.fm().beginTransaction();
            InstagramFeedFragment llf = new InstagramFeedFragment();
            ft.replace(R.id.listFragment, llf);
            ft.addToBackStack(null);
            ft.commit();
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
        ArrayList<String> photoList;

        protected void onCancelled() {

        }

        protected void onPreExecute() {

        }

        protected Long doInBackground(URL... urls) {
            long result = 0;

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(1);

                params.add(new BasicNameValuePair("count", "10"));

                InstagramRequest request = new InstagramRequest(mInstagramSession.getAccessToken());
                String response = request.requestGet("/tags/UCSC/media/recent", params);

                if (!response.equals("")) {
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    JSONArray jsonData = jsonObj.getJSONArray("data");

                    int length = jsonData.length();

                    if (length > 0) {
                        photoList = new ArrayList<String>();

                        for (int i = 0; i < length; i++) {
                            JSONObject jsonPhoto = jsonData.getJSONObject(i).getJSONObject("images").getJSONObject("low_resolution");

                            photoList.add(jsonPhoto.getString("url"));
                        }
                    }
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
            } else {
                DisplayMetrics dm = new DisplayMetrics();

                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

                int width = (int) Math.ceil((double) dm.widthPixels / 2);
                width = width - 50;
                int height = width;

                slugapp.com.ucscstudentapp.social.instagram.PhotoListAdapter adapter = new slugapp.com.ucscstudentapp.social.instagram.PhotoListAdapter(getActivity());

                adapter.setData(photoList);
                adapter.setLayoutParam(width, height);

                mGridView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBack = (ActivityCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ActivityReference");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }

}
