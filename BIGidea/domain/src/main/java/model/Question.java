package model;

import lombok.Getter;

import java.util.List;

public class Question {
    @Getter
    private String content;

    @Getter
    private List<Answer> answers;

    public Question(String content, List<Answer> answers){
        content = content.replace("&quot;", "\"");
        content = content.replace("&deg;", "°");
        content = content.replace("&ouml;", "ö");
        this.content = content.replaceAll("&#039;", "\'");
        this.answers = answers;
    }

    public Answer getCorrectAnswer(){
        return answers.stream().filter(ans -> ans.getCorrect()).findAny().get();
    }
}
