package wsmodel;

import enums.Category;
import enums.Difficulty;
import enums.MessageType;
import model.Settings;

public class RequestMessage {
    public MessageType type;
    public String playerName;
    public String lobbyName;
    public Integer rounds;
    public Difficulty difficulty;
    public Category category;
    public Integer questions;
    public Integer seconds;
    public Integer roundIndex;
    public Integer questionIndex;
    public String answer;
    public Settings settings;


    // General constructor
    public RequestMessage(){

    }



}

// create player (playerName) - check
// create lobby (playerName, lobbyName) - check
// join lobby (playerName, lobbyName) - check
// change settings (playerName, lobbyName, settings) - check
// start game (lobbyName, questionIndex)
// answer question (player, questionIndex, answer)
// end game (type is enough I guess)
