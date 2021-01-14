import enums.Category;
import enums.Difficulty;
import managers.LobbyManager;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    Player john;
    Player jane;
    Lobby lobby;
    Game game;
    List<Question> questions;
    Settings settings;

    @BeforeEach
    public void setup() {
        createPlayers();
        createLobby(john);
        joinLobby(jane);
        setQuiz();
    }

    @AfterEach
    public void cleanUp() {
        LobbyManager.deleteLobby("Anonymous");
    }

    @Test
    public void Get_First_Question() {
        Question expectedQuestion = questions.get(0);

        assertEquals(expectedQuestion, game.getNextQuestion());
    }

    @Test
    public void John_Has_Correct_Answer() {
        Question q = questions.get(1);
        john.setCurrentAnswer(q.getCorrectAnswer().getContent());
        assertTrue(game.answerCorrect(john.getCurrentAnswer(), questions.indexOf(q) + 1));
    }

    @Test
    public void All_Players_Answered(){
        john.setCurrentAnswer("Cheese");
        jane.setCurrentAnswer("Tomato");

        assertTrue(game.allAnswered());
    }

    @Test
    public void Answers_Cleared_On_Next_Question(){
        john.setCurrentAnswer("Cheese");
        jane.setCurrentAnswer("Tomato");

        game.getNextQuestion();

        assertFalse(game.allAnswered());
    }

    @Test
    public void Lobby_Equals(){
        assertEquals(game.getLobby(), lobby);
    }

    @Test
    public void Settings_Equals(){
        assertEquals(game.getSettings(), settings);
    }

    @Test
    public void New_Game_Is_Not_Over(){
        assertFalse(game.getGameOver());
    }

    public void createPlayers() {
        jane = new Player("Jane");
        john = new Player("John");
    }

    public void createLobby(Player p) {
        lobby = p.createLobby("Anonymous");
    }

    public void joinLobby(Player p) {
        p.joinLobby(lobby.getName());
    }

    public void setQuiz() {
        settings = new Settings(Category.GENERAL, 5, 10, Difficulty.EASY);
        game = new Game(lobby, settings);
        questions = game.getRound().getQuestions();
    }

}
