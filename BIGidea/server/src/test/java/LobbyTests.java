import managers.LobbyManager;
import model.Lobby;
import model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LobbyTests {
    Player john;
    Player jane;
    Lobby lobby;

    @BeforeEach
    public void setup() {
        john = new Player("John");
        jane = new Player("Jane");

        lobby = john.createLobby("Anonymous");
        jane.joinLobby("Anonymous");

    }

    @AfterEach
    public void cleanUp() {
        LobbyManager.deleteLobby("Anonymous");
    }

    @Test
    public void Clear_Lobby_Answers() {
        john.setCurrentAnswer("Answer");
        jane.setCurrentAnswer("Answer");
        String expectedAnswer = "";

        lobby.clearAnswers();

        assertEquals(expectedAnswer ,john.getCurrentAnswer());
    }

    @Test
    public void All_Players_Have_Answered(){
        john.setCurrentAnswer("Answer");
        jane.setCurrentAnswer("Answer");

        assertTrue(lobby.allAnswered());
    }

    @Test
    public void All_Players_Have_Not_Answered(){
        jane.setCurrentAnswer("Answer");

        assertFalse(lobby.allAnswered());
    }

    @Test
    public void Player_Can_Be_Removed(){
        int expectedPlayers = 1;

        lobby.removePlayer(jane);

        assertEquals(expectedPlayers, lobby.getPlayers().size());
    }

    @Test
    public void Lobby_Can_Be_Closed(){
        lobby.setClosed(true);

        assertTrue(lobby.getClosed());
    }


}
