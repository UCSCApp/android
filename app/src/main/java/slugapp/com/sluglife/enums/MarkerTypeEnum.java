package slugapp.com.sluglife.enums;

import slugapp.com.sluglife.R;

/**
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains an enum containing information about map marker types.
 */
public enum MarkerTypeEnum {
    DEFAULT("none", 0b000000, R.drawable.ic_find, R.drawable.ic_find),
    DINING_HALL("diningHall", 0b000100, R.drawable.dining_hall, R.drawable.dining_hall_legend),
    LIBRARY("library", 0b001000, R.drawable.library, R.drawable.library_legend),
    CAFE("cafe", 0b010000, R.drawable.cafe, R.drawable.cafe_legend),
    COMPUTER_LAB("computerLab", 0b100000, R.drawable.computer_lab, R.drawable.computer_lab_legend),
    CLASSROOM("classroom", 0b1000000, R.drawable.classroom, R.drawable.classroom_legend);

    public String type;
    public int mask;
    public int markerImage;
    public int legendImage;

    MarkerTypeEnum(String type, int mask, int markerImage, int legendImage) {
        this.type = type;
        this.mask = mask;
        this.markerImage = markerImage;
        this.legendImage = legendImage;
    }
}
