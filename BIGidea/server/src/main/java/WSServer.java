import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import enums.MessageType;
import lombok.SneakyThrows;
import managers.LobbyManager;
import model.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import websocket.WSLogic;
import wsmodel.*;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

public class WSServer extends WebSocketServer {

    private HashMap<WebSocket, Player> players;
    private Set<WebSocket> connections;
    private Gson gson;
    private ObjectMapper objMapper;
    private WSLogic logic;
    private List<IGame> games;

    private WSServer() {
        super(new InetSocketAddress(8081));
        players = new HashMap<>();
        connections = new HashSet<>();
        games = new ArrayList<>();
        objMapper = new ObjectMapper();
        logic = new WSLogic();
        gson = new Gson();
    }

    public static void main(String[] args) {
        new WSServer().start();
    }


    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        connections.add(webSocket);
        System.out.println("Connection made");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        Lobby hostLobby = disconnectedHostLobby(webSocket);
        Lobby nonHostLobby = disconnectedNonHostLobby(webSocket);

        if (LobbyManager.getLobbies().size() > 0 && hostLobby != null) {
            List<WebSocket> lobbyConnections = connectionsInLobby(hostLobby);
            LobbyManager.deleteLobby(hostLobby.getName());
            ResponseMessage out = new ResponseMessage(MessageType.HOST_DISCONNECTED);
            sendToChosen(lobbyConnections.stream().filter(ws -> !ws.equals(webSocket)).collect(Collectors.toList()), out);
        } else if (LobbyManager.getLobbies().size() > 0 && nonHostLobby != null) {
            List<WebSocket> lobbyConnections = connectionsInLobby(nonHostLobby);
            nonHostLobby.removePlayer(players.get(webSocket));
            ResponseMessage out = new ResponseMessage(MessageType.NON_HOST_DISCONNECTED, nonHostLobby);
            sendToChosen(lobbyConnections.stream().filter(ws -> !ws.equals(webSocket)).collect(Collectors.toList()), out);
        } else {
            System.out.println("Someone without lobby lost connection, lol.");
        }

        players.remove(webSocket);
        connections.remove(webSocket);
    }

    @SneakyThrows
    @Override
    public void onMessage(WebSocket webSocket, String message) {
        RequestMessage in = objMapper.readValue(message, RequestMessage.class);
        ResponseMessage out;
        Player player;
        Lobby lobby;
        Question q;
        try {
            switch (in.type) {
                case JOIN_LOBBY_REQUEST:
                    player = getPlayer(in.playerName);
                    lobby = player.joinLobby(in.lobbyName);
                    if (lobby == null) {
                        out = new ResponseMessage(MessageType.ERROR, "LOBBY DOESN'T EXISTS OR IS CLOSED");
                        webSocket.send(gson.toJson(out));
                    } else {
                        out = new ResponseMessage(MessageType.JOIN_LOBBY_SUCCESS, lobby);
                        var updatedPlayer = LobbyManager.get(in.lobbyName).getPlayers().stream().filter(p -> p.getUsername().equals(in.playerName)).findFirst().get();
                        players.replace(webSocket, updatedPlayer);
                        sendToLobby(lobby, out);
                    }
                    break;
                case CREATE_LOBBY_REQUEST:
                    player = getPlayer(in.playerName);
                    lobby = player.createLobby(in.lobbyName);
                    if (lobby == null) {
                        out = new ResponseMessage(MessageType.ERROR, "LOBBY ALREADY EXISTS");
                    } else {
                        out = new ResponseMessage(MessageType.CREATE_LOBBY_SUCCESS, lobby);
                        var updatedPlayer = LobbyManager.get(in.lobbyName).getPlayers().stream().filter(p -> p.getUsername().equals(in.playerName)).findFirst().get();
                        players.replace(webSocket, updatedPlayer);
                    }
                    webSocket.send(gson.toJson(out));
                    break;
                case START_GAME_REQUEST:
                    lobby = LobbyManager.get(in.lobbyName);
                    Settings settings = new Settings(in.category, in.questions, in.seconds, in.difficulty);
                    IGame game = new Game(lobby, settings);
                    games.add(game);
                    if (game.questionLeft()) {
                        out = new ResponseMessage(MessageType.START_GAME_SUCCESS, game.getNextQuestion(), game.getCurrentQuestionIndex());
                        System.out.println("first question sent");
                    } else {
                        out = new ResponseMessage(MessageType.ERROR, "NO QUESTIONS AVAILABLE");
                    }
                    sendToLobby(lobby, out);
                    break;
                case CREATE_PLAYER_REQUEST:
                    player = logic.createPlayer(in.playerName);
                    players.put(webSocket, player);
                    out = new ResponseMessage(MessageType.CREATE_PLAYER_SUCCESS, player);
                    webSocket.send(gson.toJson(out));
                    break;
                case NEXT_QUESTION_REQUEST:
                    System.out.println("next question request");
                    lobby = LobbyManager.get(in.lobbyName);
                    game = getGameByLobby(in.lobbyName);
                    if (game.allAnswered()) {
                        if (!game.questionLeft()) {
                            List<Player> players = lobby.getPlayers().stream().sorted(Comparator.comparingInt(Player::getScore)).collect(Collectors.toList());
                            out = new ResponseMessage(MessageType.GAME_OVER, players);
                            System.out.println(out.scoreBoard);
                        } else {
                            out = new ResponseMessage(MessageType.NEXT_QUESTION_SUCCESS, game.getNextQuestion(), game.getCurrentQuestionIndex());
                        }
                        sendToLobby(lobby, out);
                    }
                    break;
                case CHECK_ANSWER_REQUEST:
                    System.out.println("answer request called");
                    player = getPlayer(in.playerName);
                    player.setCurrentAnswer(in.answer);
                    game = getGameByLobby(in.lobbyName);
                    if (game.answerCorrect(player.getCurrentAnswer(), in.questionIndex)) {
                        player.increaseScore();
                        out = new ResponseMessage(MessageType.ANSWER_CORRECT, player);
                    } else {
                        out = new ResponseMessage(MessageType.ANSWER_INCORRECT, player);
                    }

                    webSocket.send(gson.toJson(out));
                    break;
                default:
            }

        } catch (Exception e) {
            ResponseMessage error = new ResponseMessage(MessageType.ERROR, e.toString());
            webSocket.send(gson.toJson(error));
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        System.out.println("You got an error my dude" + e.toString());
    }

    public void sendToLobby(Lobby lobby, ResponseMessage message) {
        for (Player p : lobby.getPlayers()) {
            for (var entry : players.entrySet()) {
                if (p.equals(entry.getValue())) {
                    entry.getKey().send(gson.toJson(message));
                }
            }
        }
    }


    private void sendToChosen(List<WebSocket> connections, ResponseMessage message) {
        for (WebSocket conn : connections) {
            conn.send(gson.toJson(message));
        }
    }

    public IGame getGameByLobby(String lobbyName) {
        return games.stream().filter(g -> g.getLobby().getName().equals(lobbyName)).findFirst().get();
    }

    public List<WebSocket> connectionsInLobby(Lobby lobby) {
        List<WebSocket> toReturn = new ArrayList<>();

        for (Player p : lobby.getPlayers()) {
            for (var set : players.entrySet()) {
                if (p.equals(set.getValue())) {
                    toReturn.add(set.getKey());
                }
            }
        }
        return toReturn;
    }

    private List<WebSocket> lobbyPlayersExceptSender(String lobbyName, WebSocket sender) {
        List<WebSocket> lobbyConnection = new ArrayList<>();
        for (var player : LobbyManager.get(lobbyName).getPlayers()) {
            for (var conn : players.entrySet()) {
                if (player.getUsername().equals(conn.getValue().getUsername())) {
                    lobbyConnection.add(conn.getKey());
                }
            }
        }
        return lobbyConnection.stream().filter(s -> !s.equals(sender)).collect(Collectors.toList());
    }

    private Player getPlayer(String name) {
        return players.entrySet().stream().filter(e -> e.getValue().getUsername().equals(name)).findFirst().get().getValue();
    }

    private Lobby disconnectedHostLobby(WebSocket disconnector) {
        var lobbies = LobbyManager.getLobbies();
        var disconnected = players.get(disconnector);

        for (Lobby lobby : lobbies) {
            for (Player p : lobby.getPlayers()) {
                if (p.getUsername().equals(disconnected.getUsername()) && disconnected.getHost()) {
                    return lobby;
                }
            }
        }
        return null;
    }

    private Lobby disconnectedNonHostLobby(WebSocket disconnector) {
        var lobbies = LobbyManager.getLobbies();
        var disconnected = players.get(disconnector);

        for (Lobby lobby : lobbies) {
            for (Player p : lobby.getPlayers()) {
                if (p.getUsername().equals(disconnected.getUsername()) && !disconnected.getHost()) {
                    return lobby;
                }
            }
        }
        return null;
    }


}
