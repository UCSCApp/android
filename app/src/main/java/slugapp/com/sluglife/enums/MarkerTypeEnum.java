package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;

/**
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains an enum containing information about map marker types.
 */
public enum MarkerTypeEnum {
    DINING_HALL("Dining Hall", R.drawable.dining_hall, R.drawable.dining_hall_legend),
    LIBRARY("Library", R.drawable.library, R.drawable.library_legend),
    CAFE("Cafe", R.drawable.cafe, R.drawable.cafe_legend),
    COMPUTER_LAB("Computer Lab", R.drawable.computer_lab, R.drawable.computer_lab_legend),
    CLASSROOM("Classroom", R.drawable.classroom, R.drawable.classroom_legend);

    public String type;
    public int markerImage;
    public int legendImage;

    MarkerTypeEnum(String type, int markerImage, int legendImage) {
        this.type = type;
        this.markerImage = markerImage;
        this.legendImage = legendImage;
    }
}
