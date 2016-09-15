package slugapp.com.sluglife.wrappers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import slugapp.com.sluglife.objects.FacilityObject;

/**
 * Created by isaiah on 9/14/16
 * <p/>
 * This file contains a facility object wrapper.
 */
public class FacilityListWrapper extends ArrayList<FacilityObject> {

    /**
     * Constructor
     *
     * @param context Activity context
     * @param array   Json array
     * @throws JSONException
     */
    public FacilityListWrapper(Context context, JSONArray array) throws JSONException {
        super(array.length());
        for (int i = 0; i < array.length(); ++i) {
            try {
                this.add(new FacilityWrapper(context, array.getJSONObject(i)));
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }
    }
}
