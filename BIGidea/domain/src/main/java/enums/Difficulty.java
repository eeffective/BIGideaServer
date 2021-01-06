package enums;

import lombok.Getter;

public enum Difficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");

    @Getter
    private final String value;

    Difficulty(String value){
        this.value = value;
    }
}
