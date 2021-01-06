package model;

import enums.Category;
import enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

public class Settings {
    @Getter
    @Setter
    private Category category;

    @Getter
    @Setter
    private Integer questions;

    @Getter
    @Setter
    private Difficulty difficulty;

    @Getter
    @Setter
    private Integer seconds;

    //TODO: Future feature - multiple rounds
    private Integer rounds;

    public Settings(Category category, Integer questions, Integer seconds, Difficulty difficulty) {
        this.category = category;
        this.questions = questions;
        this.seconds = seconds;
        this.difficulty = difficulty;
    }
}
