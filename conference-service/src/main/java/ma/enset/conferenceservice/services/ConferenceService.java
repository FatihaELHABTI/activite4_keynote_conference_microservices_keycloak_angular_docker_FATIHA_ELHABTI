package ma.enset.conferenceservice.services;

import ma.enset.conferenceservice.entities.Conference;
import java.util.List;

public interface ConferenceService {

    // Enregistrer une nouvelle conférence
    Conference saveConference(Conference conference);

    // Mettre à jour une conférence existante
    Conference updateConference(Long id, Conference conference);

    // Récupérer une conférence par son ID (avec les détails du Keynote via Feign)
    Conference getConferenceById(Long id);

    // Récupérer toutes les conférences
    List<Conference> getAllConferences();

    // Récupérer les conférences par type
    List<Conference> getConferencesByType(String type);

    // Supprimer une conférence
    void deleteConference(Long id);
}