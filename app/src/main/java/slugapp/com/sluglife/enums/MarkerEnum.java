package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;

/**
 * Created by isayyuhh on 2/21/16
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
    ),
    OAKES(
            MarkerTypeEnum.LIBRARY,
            R.string.map_oakes_title,
            R.string.map_oakes_lat,
            R.string.map_oakes_lng,
            R.string.map_library_snippet,
            R.drawable.library
    ),
    CROWN(
            MarkerTypeEnum.LIBRARY,
            R.string.map_crown_title,
            R.string.map_crown_lat,
            R.string.map_crown_lng,
            R.string.map_library_snippet,
            R.drawable.library
    ),
    MERRILL(
            MarkerTypeEnum.LIBRARY,
            R.string.map_merrill_title,
            R.string.map_merrill_lat,
            R.string.map_merrill_lng,
            R.string.map_library_snippet,
            R.drawable.library
    ),
    STEVENSON(
            MarkerTypeEnum.LIBRARY,
            R.string.map_stevenson_title,
            R.string.map_stevenson_lat,
            R.string.map_stevenson_lng,
            R.string.map_library_snippet,
            R.drawable.library
    ),
    PAGE_SMITH(
            MarkerTypeEnum.LIBRARY,
            R.string.map_page_smith_title,
            R.string.map_page_smith_lat,
            R.string.map_page_smith_lng,
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
