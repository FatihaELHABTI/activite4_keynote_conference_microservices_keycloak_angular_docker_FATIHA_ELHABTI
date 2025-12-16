package ma.enset.keynoteservice.services;

import ma.enset.keynoteservice.dtos.KeynoteDTO;
import java.util.List;

public interface KeynoteService {
    KeynoteDTO saveKeynote(KeynoteDTO keynoteDTO);
    KeynoteDTO updateKeynote(KeynoteDTO keynoteDTO);
    KeynoteDTO getKeynote(Long id);
    List<KeynoteDTO> getAllKeynotes();
}