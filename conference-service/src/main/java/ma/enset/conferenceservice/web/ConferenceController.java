package ma.enset.conferenceservice.web;

import lombok.AllArgsConstructor;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.services.ConferenceService;
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
}