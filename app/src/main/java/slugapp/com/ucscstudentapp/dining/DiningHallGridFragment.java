package slugapp.com.ucscstudentapp.dining;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import slugapp.com.ucscstudentapp.R;
import slugapp.com.ucscstudentapp.main.BaseFragment;

/**
 * Created by isayyuhh_s on 9/1/2015.
 */
public class DiningHallGridFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_dining_hall, container, false);
        this.setLayout("Dining Halls", R.id.dining_button);
        this.setView(view);
        return view;
    }

    @Override
    protected void setView(View view) {
        GridView listView = (GridView) view.findViewById(R.id.grid);
        final DiningHallGridAdapter adp = new DiningHallGridAdapter(getActivity());
        listView.setAdapter(adp);

        List<Bitmap> bitmapList = new ArrayList<>();
        Bitmap bitmap;
        for (int i = 0; i < 6; i++) {
            switch (i) {
                case 0:
                    bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dining_college8_oakes);
                    break;
                case 1:
                    bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dining_porter_kresge);
                    break;
                case 2:
                    bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dining_college9_10);
                    break;
                case 3:
                    bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dining_merill_crown);
                    break;
                case 4:
                    bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dining_cowell_stevenson);
                    break;
                case 5: default:
                    bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dining_default);
                    break;
            }
            bitmapList.add(bitmap);
        }

        adp.setData(bitmapList);
        FacilitiesListListener listListener = new FacilitiesListListener();
        listView.setOnItemClickListener(listListener);
    }

    private class FacilitiesListListener implements AdapterView.OnItemClickListener {
        public FacilitiesListListener() {}

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ac.hideKeyboard();

            String name;
            switch (position) {
                case 0:
                    name = "College Eight / Oakes";
                    break;
                case 1:
                    name = "Porter / Kresge";
                    break;
                case 2:
                    name = "College Nine / College Ten";
                    break;
                case 3:
                    name = "Crown / Merrill";
                    break;
                case 4:
                    name = "Cowell / Stevenson";
                    break;
                default:
                    return;
            }

            Bundle b = new Bundle();
            b.putString("name", name);

            FragmentTransaction ft = ac.fm().beginTransaction();
            DiningHallDetail fragment = new DiningHallDetail();
            fragment.setArguments(b);
            ft.replace(R.id.listFragment, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
