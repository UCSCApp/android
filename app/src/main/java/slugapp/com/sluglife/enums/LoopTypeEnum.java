package slugapp.com.sluglife.enums;

/**
 * Created by isayyuhh on 2/19/16
 */
public enum LoopTypeEnum {
    LOOP("LOOP"),
    OUT_OF_SERVICE("OUT OF SERVICE"),
    UPPER_CAMPUS("UPPER CAMPUS"),
    SPECIAL("SPECIAL"),
    LOOP_OUT_OF_SERVICE_AT_BARN_THEATER("LOOP OUT OF SERVICE AT BARN THEATER"),
    EAST_NIGHT_CORE("EAST NIGHT CORE");

    public String string;

    LoopTypeEnum(String string) {
        this.string = string;
    }
}
