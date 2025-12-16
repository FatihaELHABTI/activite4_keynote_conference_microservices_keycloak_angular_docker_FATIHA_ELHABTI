package ma.enset.conferenceservice.repositories;

import ma.enset.conferenceservice.entities.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    // Trouver toutes les conférences d'un Keynote spécifique
    List<Conference> findByKeynoteId(Long keynoteId);

    // Trouver les conférences par type (ex: "Academique", "Commerciale")
    List<Conference> findByType(String type);
}