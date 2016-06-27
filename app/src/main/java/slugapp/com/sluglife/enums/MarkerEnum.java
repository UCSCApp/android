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

    private MarkerTypeEnum type;
    private int title;
    private int lat;
    private int lng;
    private int snippet;
    private int icon;

    MarkerEnum(MarkerTypeEnum type, int title, int lat, int lng, int snippet, int icon) {
        this.title = title;
        this.type = type;
        this.lat = lat;
        this.lng = lng;
        this.snippet = snippet;
        this.icon = icon;
    }

    public MarkerTypeEnum getType() {
        return this.type;
    }

    public int getTitle() {
        return this.title;
    }

    public int getLat() {
        return this.lat;
    }

    public int getLng() {
        return this.lng;
    }

    public int getSnippet() {
        return this.snippet;
    }

    public int getIcon() {
        return this.icon;
    }
}
