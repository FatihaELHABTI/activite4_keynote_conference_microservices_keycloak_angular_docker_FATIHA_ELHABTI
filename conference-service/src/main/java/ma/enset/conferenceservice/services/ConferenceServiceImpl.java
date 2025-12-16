package ma.enset.conferenceservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.conferenceservice.clients.KeynoteRestClient;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.model.Keynote;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j // Pour les logs
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final KeynoteRestClient keynoteRestClient;

    @Override
    public Conference saveConference(Conference conference) {
        log.info("Enregistrement d'une nouvelle conférence : {}", conference.getTitre());
        // Ici, on pourrait vérifier si le keynoteId existe vraiment via le RestClient avant de sauvegarder
        // Mais pour l'instant, on sauvegarde directement.
        return conferenceRepository.save(conference);
    }

    @Override
    public Conference updateConference(Long id, Conference conference) {
        log.info("Mise à jour de la conférence ID : {}", id);

        Conference existingConference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference introuvable avec l'id " + id));

        // Mise à jour des champs (on ne touche pas à l'ID)
        if (conference.getTitre() != null) existingConference.setTitre(conference.getTitre());
        if (conference.getType() != null) existingConference.setType(conference.getType());
        if (conference.getDate() != null) existingConference.setDate(conference.getDate());
        if (conference.getDuree() > 0) existingConference.setDuree(conference.getDuree());
        if (conference.getScore() > 0) existingConference.setScore(conference.getScore());
        if (conference.getKeynoteId() != null) existingConference.setKeynoteId(conference.getKeynoteId());

        return conferenceRepository.save(existingConference);
    }

    @Override
    public Conference getConferenceById(Long id) {
        Conference conference = conferenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conference introuvable"));

        // Appel au micro-service Keynote pour récupérer les détails
        enrichWithKeynoteData(conference);

        return conference;
    }

    @Override
    public List<Conference> getAllConferences() {
        List<Conference> conferences = conferenceRepository.findAll();

        // Pour chaque conférence, on récupère les infos du Keynote
        // Note : Dans un vrai système prod, attention au problème "N+1".
        // On préfèrerait parfois récupérer les IDs et faire un appel groupé,
        // mais ici c'est pour la démo de la communication.
        conferences.forEach(this::enrichWithKeynoteData);

        return conferences;
    }

    @Override
    public List<Conference> getConferencesByType(String type) {
        List<Conference> conferences = conferenceRepository.findByType(type);
        conferences.forEach(this::enrichWithKeynoteData);
        return conferences;
    }

    @Override
    public void deleteConference(Long id) {
        log.info("Suppression de la conférence ID : {}", id);
        if (!conferenceRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Conference introuvable");
        }
        conferenceRepository.deleteById(id);
    }

    /**
     * Méthode utilitaire pour charger les données du Keynote via OpenFeign.
     * Gère le cas où le KeynoteId est null.
     */
    private void enrichWithKeynoteData(Conference conference) {
        if (conference.getKeynoteId() != null) {
            try {
                Keynote keynote = keynoteRestClient.getKeynoteById(conference.getKeynoteId());
                conference.setKeynote(keynote);
            } catch (Exception e) {
                // Si le service Keynote est en panne ou le keynote n'existe pas,
                // on ne veut pas faire planter tout l'appel conférence.
                log.error("Erreur lors de la récupération du Keynote id {}", conference.getKeynoteId(), e);
                // On met un objet vide ou null selon le besoin
                conference.setKeynote(null);
            }
        }
    }
}