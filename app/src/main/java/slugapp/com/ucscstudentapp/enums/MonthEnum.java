package slugapp.com.ucscstudentapp.enums;

/**
 * Created by isayyuhh on 2/21/16.
 */
public enum MonthEnum {
    JANUARY(
            "January", 1
    ),
    FEBRUARY(
            "February", 2
    ),
    MARCH(
            "March", 3
    ),
    APRIL(
            "April", 4
    ),
    MAY(
            "May", 5
    ),
    JUNE(
            "June", 6
    ),
    JULY(
            "July", 7
    ),
    AUGUST(
            "August", 8
    ),
    SEPTEMBER(
            "September", 9
    ),
    OCTOBER(
            "October", 10
    ),
    NOVEMBER(
            "November", 11
    ),
    DECEMBER(
            "December", 12
    );

    private String month;
    private int order;

    MonthEnum(String month, int order) {
        this.month = month;
        this.order = order;
    }

    public String getVal() {
        return this.month;
    }

    public int getOrder() {
        return this.order;
    }
}
