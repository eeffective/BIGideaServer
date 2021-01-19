package api.controller;

import api.entity.Player;
import api.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/players")
@CrossOrigin
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service){
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Player> create(@RequestBody Player player){
        try {
            this.service.save(player);
            return new ResponseEntity<>(player, HttpStatus.OK);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Player> update(@RequestBody Player updatePlayer, @PathVariable Integer id){
        return this.service.findById(id)
                .map(player -> {
                    player = updatePlayer;
                    this.service.save(player);
                    return new ResponseEntity<>(player, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    updatePlayer.setId(id);
                    this.service.save(updatePlayer);
                    return new ResponseEntity<>(updatePlayer, HttpStatus.ACCEPTED);
                });
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Player> read(@PathVariable Integer id){
        try {
            return new ResponseEntity<>(this.service.findById(id).get(), HttpStatus.OK);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
