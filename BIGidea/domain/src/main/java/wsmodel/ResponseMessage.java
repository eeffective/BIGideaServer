package wsmodel;

import enums.MessageType;
import model.Lobby;
import model.Player;
import model.Question;
import model.Settings;

public class ResponseMessage {
    public MessageType type;
    public Player player;
    public Lobby lobby;
    public Question question;
    public Settings settings;
    public String error;

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
    public ResponseMessage(MessageType type, Question question){
        this.type = type;
        this.question = question;
    }

    // started game & answered/next question
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
