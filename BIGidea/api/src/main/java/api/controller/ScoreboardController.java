package api.controller;

import api.entity.Player;
import api.entity.Scoreboard;
import api.service.PlayerService;
import api.service.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping(path = "/scoreboard")
public class ScoreboardController {
    @Autowired
    private final ScoreboardService boardService;
    @Autowired
    private final PlayerService playerService;

    public ScoreboardController(ScoreboardService boardService, PlayerService playerService) {
        this.boardService = boardService;
        this.playerService = playerService;
    }

    @GetMapping()
    public Collection<Scoreboard> readAll() {
        try {
            return this.boardService.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Scoreboard> read(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(this.boardService.findById(id), HttpStatus.OK);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PostMapping
    public ResponseEntity<Scoreboard> create(@RequestBody Scoreboard scoreboard){
        try {
            this.boardService.save(scoreboard);
            return new ResponseEntity<>(scoreboard, HttpStatus.OK);
        } catch (Exception ex) {
            throw ex;
        }
    }


    @GetMapping(path = "/lobby/{lobbyName}")
    public ResponseEntity<Scoreboard> readByLobbyName(@PathVariable String lobbyName){
        try {
            return new ResponseEntity<>(this.boardService.findByLobbyName(lobbyName), HttpStatus.OK);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
