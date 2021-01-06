package model;

import lombok.Getter;

public class Answer {
    @Getter
    private String content;

    @Getter
    private Boolean correct;

    public Answer(String content, Boolean correct) {
        content = content.replace("&quot;", "\"");
        content = content.replace("&deg;", "°");
        this.content = content.replaceAll("&#039;", "\'");
        this.correct = correct;
    }
}
