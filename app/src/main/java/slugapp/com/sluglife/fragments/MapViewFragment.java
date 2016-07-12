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

    // TODO: match with events search

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
                if (searchShowing) {
                    this.mBinding.search.searchBar.setVisibility(View.GONE);
                    this.searchShowing = false;
                    this.mBinding.search.searchEditText.setText("");
                }

                int bin = getSharedPrefInt(this.mContext.getString(R.string.bundle_markers), 0);

                Bundle b = new Bundle();
                b.putInt(this.mContext.getString(R.string.bundle_markers), bin);

                MapFilterDialogFragment dialog = new MapFilterDialogFragment();
                dialog.setArguments(b);

                this.setDialogFragment(dialog);
                return true;
            }
            case R.id.search: {
                if (!this.searchShowing) {
                    this.mBinding.search.searchBar.setVisibility(View.VISIBLE);
                    this.searchShowing = true;
                } else {
                    this.mBinding.search.searchBar.setVisibility(View.GONE);
                    this.mBinding.search.searchEditText.setText("");
                    this.searchShowing = false;

                    if (!this.mQuery.isEmpty()) {
                        this.setChildFragment(R.id.map_view, MapFragment.newInstance());
                    }
                }
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
        if (requestCode == 0) {
            int bin = data.getIntExtra(this.mContext.getString(R.string.bundle_markers), 0);

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
                    setChildFragment(R.id.map_view, MapFacilityListFragment.newInstance(mContext,
                            mQuery));
                } else {
                    setChildFragment(R.id.map_view, MapFragment.newInstance());
                }
            }
        });
    }
}
