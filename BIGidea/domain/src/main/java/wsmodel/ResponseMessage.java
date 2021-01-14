package wsmodel;

import enums.MessageType;
import model.Lobby;
import model.Player;
import model.Question;
import model.Settings;

import java.util.List;

public class ResponseMessage {
    public MessageType type;
    public Player player;
    public Lobby lobby;
    public Question question;
    public Integer questionIndex;
    public Settings settings;
    public String error;
    public List<Player> scoreBoard;

    // created player
    public ResponseMessage(MessageType type, Player player){
        this.player = player;
        this.type = type;
    }

    // created lobby & joined lobby
    public ResponseMessage(MessageType type, Lobby lobby){
        this.type = type;
        this.lobby = lobby;
    }

    // changed settings
    public ResponseMessage(MessageType type, Settings settings){
        this.type = type;
        this.settings = settings;
    }

    // started game & answered/next question
    public ResponseMessage(MessageType type, Question question, Integer questionIndex){
        this.type = type;
        this.question = question;
        this.questionIndex = questionIndex;
    }

    public ResponseMessage(MessageType type, Question question){
        this.type = type;
        this.question = question;
    }

    // started game & answered/next question
    public ResponseMessage(MessageType type, Question question, Player player, Integer questionIndex){
        this.type = type;
        this.question = question;
        this.player = player;
        this.questionIndex = questionIndex;
    }
    public ResponseMessage(MessageType type, Question question, Player player){
        this.type = type;
        this.question = question;
        this.player = player;
    }

    // error
    public ResponseMessage(MessageType type, String error){
        this.type = type;
        this.error = error;
    }

    public ResponseMessage(MessageType type, String error, Player player){
        this.type = type;
        this.error = error;
        this.player = player;
    }

    // game over >  list of players ordered by score
    public ResponseMessage(MessageType type, List<Player> players){
        this.type = type;
        this.scoreBoard = players;
    }


    // ended game or any other type-only constructor
    public ResponseMessage(MessageType type){
        this.type = type;
    }

    // nonsense
    public ResponseMessage(){

    }
}

// created player (Player)
// created lobby (Lobby)
// joined lobby (Lobby)
// changed settings (Settings)
// started game (Question)
// answered/next question (Question)
// ended game (type is enough I guess)
