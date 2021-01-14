package model;

import lombok.Getter;

import java.util.List;

public class Game implements IGame {
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

    @Getter
    private Integer currentQuestionIndex;


    public Game(Lobby lobby, Settings settings) {
        this.currentQuestionIndex = 0;
        this.settings = settings;
        this.lobby = lobby;
        this.gameOver = false;
        this.lobby.setClosed(true);
        this.round = new Round(this.settings);
    }


    public Question getNextQuestion() {
        lobby.clearAnswers();
        this.currentQuestionIndex++;
        return round.getQuestions().get(currentQuestionIndex - 1);
    }

    public Boolean questionLeft() {
        if (currentQuestionIndex + 1 <= this.round.getQuestions().size()) {
            lobby.clearAnswers();
            return true;
        } else {
            gameOver = true;
            lobby.clearAnswers();
            return false;
        }
    }


    public List<Player> getScoreBoard() {
        return null;
    }

    public Boolean allAnswered() {
        return this.lobby.allAnswered();
    }

    public Boolean answerCorrect(String answer, int qIndex) {
        return (round.getQuestions().get(qIndex - 1).getCorrectAnswer().getContent().equals(answer));
    }

}
