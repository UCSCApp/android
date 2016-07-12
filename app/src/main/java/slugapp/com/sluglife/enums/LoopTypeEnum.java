package slugapp.com.sluglife.enums;

/**
 * Created by isayyuhh on 2/19/16
 * <p/>
 * This file contains an enum containing information about loop types.
 */
public enum LoopTypeEnum {
    LOOP("LOOP"),
    OUT_OF_SERVICE("OUT OF SERVICE"),
    UPPER_CAMPUS("UPPER CAMPUS"),
    SPECIAL("SPECIAL"),
    LOOP_OUT_OF_SERVICE_AT_BARN_THEATER("LOOP OUT OF SERVICE AT BARN THEATER"),
    EAST_NIGHT_CORE("EAST NIGHT CORE");

    // TODO: implement

    public String name;

    /**
     * Constructor
     *
     * @param name Name of loop type
     */
    LoopTypeEnum(String name) {
        this.name = name;
    }
}
