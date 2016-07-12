package slugapp.com.sluglife.enums;

/**
 * Created by isaiah on 2/21/16
 * <p/>
 * This file contains an enum containing information about months.
 */
public enum MonthEnum {
    JANUARY(
            "January", 0
    ),
    FEBRUARY(
            "February", 1
    ),
    MARCH(
            "March", 2
    ),
    APRIL(
            "April", 3
    ),
    MAY(
            "May", 4
    ),
    JUNE(
            "June", 5
    ),
    JULY(
            "July", 6
    ),
    AUGUST(
            "August", 7
    ),
    SEPTEMBER(
            "September", 8
    ),
    OCTOBER(
            "October", 9
    ),
    NOVEMBER(
            "November", 10
    ),
    DECEMBER(
            "December", 11
    );

    public String name;
    public int number;

    /**
     * Constructor
     *
     * @param name   Name of month
     * @param number Numerical representation of month
     */
    MonthEnum(String name, int number) {
        this.name = name;
        this.number = number;
    }
}
