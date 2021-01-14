package model;

public interface IGame {
    Boolean allAnswered();
    Boolean answerCorrect(String answer, int qIndex);
    Lobby getLobby();
    Question getNextQuestion();
    Integer getCurrentQuestionIndex();
    Boolean questionLeft();
}
