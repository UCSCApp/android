package slugapp.com.sluglife.enums;

/**
 * Created by isayyuhh on 2/21/16
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
    public int order;

    MonthEnum(String name, int order) {
        this.name = name;
        this.order = order;
    }
}
