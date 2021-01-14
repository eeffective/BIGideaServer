package websocket;


import managers.LobbyManager;
import model.*;

import org.java_websocket.WebSocket;
import wsmodel.RequestMessage;

import java.util.HashMap;

public class WSLogic {
    public Player createPlayer(String name){
       return new Player(name);
    }

    public Lobby createLobby(String lobbyName, Player host){
       return new Lobby(lobbyName, host);
    }

    public void joinLobby(RequestMessage in, WebSocket ws, HashMap<WebSocket, Player> players){
        Player player = new Player(in.playerName);
        LobbyManager.joinLobby(in.lobbyName, player);
        players.put(ws, player);
    }

    public Game createGame(RequestMessage in){
        Lobby lobby = LobbyManager.get(in.lobbyName);
        Game game = new Game(lobby, in.settings);
        return game;
    }

    public Boolean answerCorrect(RequestMessage message, Game game){
        Question q = game.getRound().getQuestions().get(message.questionIndex);
        return message.answer.equals(q.getCorrectAnswer().getContent());
    }
}
