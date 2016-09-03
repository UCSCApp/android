package slugapp.com.sluglife.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import slugapp.com.sluglife.R;
import slugapp.com.sluglife.databinding.ViewMapBinding;
import slugapp.com.sluglife.enums.FragmentEnum;

/**
 * Created by isaiah on 6/20/16
 * <p/>
 * This file contains a view fragment that displays google map information.
 */
public class MapViewFragment extends BaseViewFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.MAP;

    private ViewMapBinding mBinding;
    private String mQuery;

    private boolean searchShowing;
    private boolean resultsShowing;

    /**
     * Gets a new instance of fragment
     *
     * @return New instance of fragment
     */
    public static MapViewFragment newInstance() {
        return new MapViewFragment();
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
                R.layout.view_map, container, false);

        this.setViewFragment(FRAGMENT);

        return this.mBinding.getRoot();
    }

    /**
     * Fragment's onCreateOptionsMenu method
     *
     * @param menu     Menu
     * @param inflater View inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_map_search, menu);
    }

    /**
     * Does action on toolbar item click
     *
     * @param item Toolbar item
     * @return Boolean if toolbar item is clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter: {
                this.mCallback.hideKeyboard();

                if (this.searchShowing) {
                    this.hideViews(this.mBinding.search.searchBar);
                    this.searchShowing = false;
                    this.mBinding.search.searchEditText.setText("");
                }

                int bin = this.getSharedPrefInt(this.mContext.getString(R.string.bundle_markers));
                this.setDialogFragment(MapFilterDialogFragment.newInstance(this.mContext, bin));

                return true;
            }
            case R.id.search: {
                if (!this.searchShowing) {
                    this.showViews(this.mBinding.search.searchBar);

                    this.mCallback.showKeyboard(this.mBinding.search.searchEditText);
                } else {
                    this.mCallback.hideKeyboard();

                    this.mBinding.search.searchEditText.setText(EMPTY_STRING);

                    this.hideViews(this.mBinding.search.searchBar);

                    if (this.resultsShowing) {
                        this.resultsShowing = false;

                        this.setChildFragment(R.id.map_view, MapFragment.newInstance());
                    }
                }
                this.searchShowing = !this.searchShowing;

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Fragment's onActivityResult method
     *
     * @param requestCode Request code
     * @param resultCode  Result code
     * @param data        Intent data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DEFAULT_REQUEST_CODE) {
            int bin = data.getIntExtra(this.mContext.getString(R.string.bundle_markers), DEFAULT_REQUEST_CODE);

            this.putSharedPrefInt(this.mContext.getString(R.string.bundle_markers), bin);
            this.setChildFragment(R.id.map_view, MapFragment.newInstance());
        }
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
        this.searchShowing = false;
        this.resultsShowing = false;
    }

    /**
     * Sets fragment view
     */
    @Override
    protected void setView() {
        this.setChildFragment(R.id.map_view, MapFragment.newInstance());

        this.mBinding.search.searchEditText.addTextChangedListener(new TextWatcher() {

            /**
             * Before edit text changed
             *
             * @param s Edit text character sequence
             * @param start Edit text start index
             * @param count Edit text current length
             * @param after Edit text length after change
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            /**
             * On edit text changed
             *
             * @param s Edit text character sequence
             * @param start Edit text start index
             * @param before Edit text length before change
             * @param count Edit text current length
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            /**
             * After edit text changed
             *
             * @param s Edit text character sequence
             */
            @Override
            public void afterTextChanged(Editable s) {
                mQuery = s.toString();

                if (!mQuery.isEmpty()) {
                    resultsShowing = true;

                    setChildFragment(R.id.map_view, MapFacilityListFragment.newInstance(mContext,
                            mQuery));
                } else if (resultsShowing) {
                    resultsShowing = false;

                    setChildFragment(R.id.map_view, MapFragment.newInstance());
                }
            }
        });
    }
}
