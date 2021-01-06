package model;

import enums.Category;
import lombok.Getter;
import services.QuestionService;

import java.util.ArrayList;
import java.util.List;

public class Game {
    @Getter
    private Lobby lobby;

    // TODO: Future feature - multiple rounds
//    @Getter
//    private List<Round> rounds;

    @Getter
    private Round round;

    @Getter
    private Settings settings;

    @Getter
    private Boolean gameOver;


    public Game(Lobby lobby, Settings settings) {
        this.settings = settings;
        this.lobby = lobby;
        this.gameOver = false;
        this.lobby.setClosed(true);
        this.round = new Round(this.settings);
    }

    public Question getQuestion(int x){
        lobby.clearAnswers();
        if (x <= round.getQuestions().size()){
            return round.getQuestions().get(x);
        } else {
            return null;
        }
    }

    public Boolean correctAnswer(String answer, Integer questionIndex) {
        return (round.getQuestions().get(questionIndex).getCorrectAnswer().getContent().equals(answer));
    }

    public Boolean allAnswered(){
        return this.lobby.allAnswered();
    }

}
