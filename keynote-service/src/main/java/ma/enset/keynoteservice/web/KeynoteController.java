package ma.enset.keynoteservice.web;

import lombok.AllArgsConstructor;
import ma.enset.keynoteservice.dtos.KeynoteDTO;
import ma.enset.keynoteservice.services.KeynoteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/keynotes")
@AllArgsConstructor
public class KeynoteController {
    private KeynoteService keynoteService;

    @GetMapping
    public List<KeynoteDTO> getAll() {
        return keynoteService.getAllKeynotes();
    }

    @GetMapping("/{id}")
    public KeynoteDTO getById(@PathVariable Long id) {
        return keynoteService.getKeynote(id);
    }

    @PostMapping
    public KeynoteDTO save(@RequestBody KeynoteDTO keynoteDTO) {
        return keynoteService.saveKeynote(keynoteDTO);
    }
}