package api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "scoreboard")
public class Scoreboard {
    @Getter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Column(name = "players")
    @OneToMany(mappedBy = "scoreboard", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Player> players = new HashSet<>();

    @Getter
    @Column(name = "created_at")
    private LocalDate createdAt;

    @Getter
    @Column(name = "lobby")
    private String lobbyName;

    public Scoreboard() {
        this.createdAt = LocalDate.now();
    }

}

