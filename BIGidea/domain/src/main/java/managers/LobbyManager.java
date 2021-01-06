package managers;

import lombok.Getter;
import model.Lobby;
import model.Player;

import java.util.*;

public class LobbyManager {
    @Getter
    private static List<Lobby> lobbies = new ArrayList<>();

    public static Lobby joinLobby(String lobbyName, Player nonHost){
        Lobby lobby = lobbies.stream().filter(l -> l.getName().equals(lobbyName)).findAny().get();

        if (lobby != null) {
            lobby.addPlayer(nonHost);
        } else {
            System.out.println("No such lobby exists");
            return null;
        }
        return lobby;
    }

    public static Lobby createLobby(String lobbyName, Player host){
        boolean lobbyExists = lobbies.stream().anyMatch(l -> l.getName().equals(lobbyName));
        if (!lobbyExists) {
            lobbies.add(new Lobby(lobbyName, host));
        } else {
            System.out.println("Already a lobby with such name");
            return null;
        }
        return get(lobbyName);
    }

    public static Lobby get(String lobbyName){
        return lobbies.stream().filter(l -> l.getName().equals(lobbyName)).findFirst().get();
    }

    public static void deleteLobby(String lobbyName){
        Lobby lobby = get(lobbyName);
        List<Player> players = lobby.getPlayers();
        lobby.getPlayers().removeAll(players);
        lobbies.remove(lobby);
    }
}
