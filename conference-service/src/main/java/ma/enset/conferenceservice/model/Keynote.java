package ma.enset.conferenceservice.model;
import lombok.Data;

@Data
public class Keynote {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}