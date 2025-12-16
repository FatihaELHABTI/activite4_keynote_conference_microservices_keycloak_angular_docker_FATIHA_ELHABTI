package ma.enset.keynoteservice.dtos;

import lombok.Data;

@Data
public class KeynoteDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}