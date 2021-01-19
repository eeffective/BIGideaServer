package api.repository;

import api.entity.Player;
import api.entity.Scoreboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findByUsername(String username);
    Player findByScoreboard(Scoreboard scoreboard);
}
