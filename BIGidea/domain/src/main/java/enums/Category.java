package enums;

import lombok.Getter;

public enum Category {
    GENERAL(23),
    SPORTS(21),
    MUSIC(12),
    GEOGRAPHY(22),
    CELEBS(26),
    ART(0),
    HISTORY(9);

    @Getter
    private final Integer value;

    Category(Integer value){
        this.value = value;
    }
}

/*

 case SPORT -> categoryInt = 21;
            case ART -> categoryInt = 0;
            case CELEBS -> categoryInt = 26;
            case MUSIC -> categoryInt = 12;
            case HISTORY -> categoryInt = 9;
            case GEOGRAPHY -> categoryInt = 22;
            default -> categoryInt = 23; // GENERAL
 */
