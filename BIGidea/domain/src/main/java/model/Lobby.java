package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    @Getter
    private String name;

    @Getter
    private List<Player> players;

    @Getter
    @Setter
    private Boolean closed;

    public Lobby(String name, Player host){
        this.name = name;
        this.players = new ArrayList<>();
        this.players.add(host);
        this.closed = false;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public boolean allAnswered() {
        return this.players.stream().noneMatch(p -> p.getCurrentAnswer().equals(""));
    }

    public void clearAnswers(){
        for(Player p : this.players){
            p.clearAnswer();
        }
    }
}
