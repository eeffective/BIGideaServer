package api.service;

import api.entity.Player;
import api.entity.Scoreboard;
import api.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private final PlayerRepository repo;

    public PlayerService(PlayerRepository repo){
        this.repo = repo;
    }

    public void save(Player player){
        this.repo.save(player);
    }

    public Optional<Player> findById(Integer id){
        return this.repo.findById(id);
    }

    public Player findByUsername(String username){
        return this.repo.findByUsername(username);
    }

    public Player findByScoreboard(Scoreboard scoreboard){
        return this.repo.findByScoreboard(scoreboard);
    }
}
