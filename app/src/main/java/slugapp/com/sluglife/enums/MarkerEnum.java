package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;

/**
 * Created by isayyuhh on 2/21/16.
 */

public enum MarkerEnum {
    MCHENRY(
            MarkerTypeEnum.LIBRARY,
            R.string.map_mchenry_title,
            R.string.map_mchenry_lat,
            R.string.map_mchenry_lng,
            R.string.map_library_snippet,
            R.drawable.library
    ),
    SNE(
            MarkerTypeEnum.LIBRARY,
            R.string.map_sne_title,
            R.string.map_sne_lat,
            R.string.map_sne_lng,
            R.string.map_library_snippet,
            R.drawable.library
    );

    public MarkerTypeEnum type;
    public int title;
    public int lat;
    public int lng;
    public int snippet;
    public int icon;

    MarkerEnum(MarkerTypeEnum type, int title, int lat, int lng, int snippet, int icon) {
        this.title = title;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.snippet = snippet;
        this.icon = icon;
    }
}
