package api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Getter
    @Setter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Column(name = "username")
    private String username;

    @Getter
    @Column(name = "score")
    private Integer score;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Scoreboard.class)
    @JoinColumn(name = "scoreboard_id", nullable = true)
    @JsonBackReference
    private Scoreboard scoreboard;

    public Player (String username, Integer score){
        this.username = username;
        this.score = score;
    }

}
