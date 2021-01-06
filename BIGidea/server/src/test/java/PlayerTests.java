import managers.LobbyManager;
import model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {
    private Player player;
    private Player host;

    @BeforeEach
    public void setup(){
        player = new Player("John Doe");
        player.setCurrentAnswer("Answer");

        host = new Player("Host");
        host.createLobby("hostLobby");
    }

    @AfterEach
    public void cleanUp(){
        LobbyManager.deleteLobby("hostLobby");
    }

    @Test
    public void Player_Can_Set_Name(){
        String expectedName = "John";
        Player p = new Player(expectedName);

        assertEquals(expectedName, p.getUsername());
    }

    @Test
    public void Player_Can_Increase_Score(){
        int expectedScore = 1;
        player.increaseScore();

        assertEquals(expectedScore, player.getScore());
    }

    @Test
    public void Player_Can_Clear_Answer(){
        String expectedAnswer = "";
        player.clearAnswer();

        assertEquals(expectedAnswer, player.getCurrentAnswer());
    }

    @Test
    public void Player_Host_When_Creating_Lobby(){
        String lobbyName = "1";

        player.createLobby(lobbyName);

        assertTrue(player.getHost());
    }

    @Test
    public void Player_Non_Host_When_Joining_Lobby(){
        String lobbyName = "hostLobby";

        player.joinLobby(lobbyName);

        assertFalse(player.getHost());
    }
}
