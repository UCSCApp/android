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
 * Created by isayyuhh on 6/20/16
 */
public class MapViewFragment extends BaseViewFragment {
    private static final FragmentEnum FRAGMENT = FragmentEnum.MAP;

    private ViewMapBinding mBinding;
    private String mQuery;

    private boolean searchShowing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mBinding = DataBindingUtil.inflate(this.getActivity().getLayoutInflater(),
                R.layout.view_map, container, false);

        this.setViewFragment(FRAGMENT);

        return this.mBinding.getRoot();
    }

    @Override
    protected void setArgumentFields(Bundle b) {
    }

    @Override
    protected void setFields() {
        this.searchShowing = false;
    }

    @Override
    protected void setView() {
        this.setChildFragment(R.id.map_view, new MapFragment());

        this.mBinding.search.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mQuery = s.toString();

                if (!mQuery.isEmpty()) {
                    FacilityListFragment fragment = new FacilityListFragment();

                    Bundle b = new Bundle();
                    b.putString(mContext.getString(R.string.bundle_query), mQuery);
                    fragment.setArguments(b);

                    setChildFragment(R.id.map_view, fragment);
                } else {
                    setChildFragment(R.id.map_view, new MapFragment());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            int bin = data.getIntExtra(this.mContext.getString(R.string.bundle_markers), 0);

            this.putSharedPrefInt(this.mContext.getString(R.string.bundle_markers), bin);
            this.setChildFragment(R.id.map_view, new MapFragment());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_map_search, menu);
    }

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
                        this.setChildFragment(R.id.map_view, new MapFragment());
                    }
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
