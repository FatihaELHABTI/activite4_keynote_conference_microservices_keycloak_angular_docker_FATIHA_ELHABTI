package ma.enset.conferenceservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.conferenceservice.model.Keynote; // Voir plus bas
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String type;
    @Temporal(TemporalType.DATE)
    private Date date;
    private int duree;
    private int nombreInscrits;
    private double score;
    private Long keynoteId; // ID externe

    @Transient // Pas persisté en base
    private Keynote keynote;

    @OneToMany(mappedBy = "conference")
    private List<Review> reviews;
}