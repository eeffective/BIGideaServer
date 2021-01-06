package model;

import lombok.Getter;
import lombok.Setter;
import managers.LobbyManager;


public class Player{

    @Getter
    private String username;

    @Getter
    private Boolean host;

    @Getter
    private Integer score;

    @Getter
    @Setter
    private String currentAnswer;


    public Player(String username) {
        this.username = username;
        this.score = 0;
        this.host = false;
        this.currentAnswer = "";
    }

    public Lobby createLobby(String lobbyName) {
        this.host = true;
        return LobbyManager.createLobby(lobbyName, this);
    }

    public Lobby joinLobby(String lobbyName) {
        this.host = false;
        return LobbyManager.joinLobby(lobbyName, this);
    }

    public void increaseScore(){
        this.score++;
    }

    public void clearAnswer() {
        this.currentAnswer = "";
    }
}
