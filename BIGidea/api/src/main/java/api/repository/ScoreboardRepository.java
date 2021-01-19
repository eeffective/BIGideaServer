package api.repository;

import api.entity.Scoreboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreboardRepository extends JpaRepository<Scoreboard, Integer> {
    Scoreboard findByLobbyName(String lobbyName);
}
