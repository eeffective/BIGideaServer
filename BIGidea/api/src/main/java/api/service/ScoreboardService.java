package api.service;

import api.entity.Scoreboard;
import api.repository.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ScoreboardService {
    @Autowired
    private final ScoreboardRepository repo;

    public ScoreboardService(ScoreboardRepository repo) {
        this.repo = repo;
    }

    public void save(Scoreboard scoreboard) {
        try {
            repo.save(scoreboard);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Collection<Scoreboard> findAll() {
        try {
            return repo.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Scoreboard findById(Integer id) {
        try {
            return this.repo.findById(id).get();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Scoreboard findByLobbyName(String lobbyName) {
        try {
            return this.repo.findByLobbyName(lobbyName);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
