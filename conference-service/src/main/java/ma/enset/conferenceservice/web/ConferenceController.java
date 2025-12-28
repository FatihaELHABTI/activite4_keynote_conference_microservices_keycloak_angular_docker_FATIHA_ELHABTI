package ma.enset.conferenceservice.web;

import lombok.AllArgsConstructor;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.services.ConferenceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/conferences")
@AllArgsConstructor
public class ConferenceController {
    private ConferenceService conferenceService;

    @GetMapping
    public List<Conference> getAll() {
        return conferenceService.getAllConferences();
    }

    @GetMapping("/{id}")
    public Conference getById(@PathVariable Long id) {
        return conferenceService.getConferenceById(id);
    }

    // SEUL L'ADMIN PEUT CREER
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Conference save(@RequestBody Conference conference) {
        return conferenceService.saveConference(conference);
    }

    // SEUL L'ADMIN PEUT MODIFIER
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Conference update(@PathVariable Long id, @RequestBody Conference conference) {
        return conferenceService.updateConference(id, conference);
    }

    // SEUL L'ADMIN PEUT SUPPRIMER
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        conferenceService.deleteConference(id);
    }
}