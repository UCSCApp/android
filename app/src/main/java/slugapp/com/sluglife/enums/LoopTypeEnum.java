package slugapp.com.sluglife.enums;

/**
 * Created by isayyuhh on 2/19/16.
 */
public enum LoopTypeEnum {
    LOOP("LOOP"),
    OUT_OF_SERVICE(""),
    UPPER_CAMPUS("UPPER CAMPUS"),
    SPECIAL("SPECIAL"),
    EAST_NIGHT_CORE("EAST NIGHT CORE");

    LoopTypeEnum(String string) {
        this.string = string;
    }

    private String string;

    public String getString() {
        return this.string;
    }
}
