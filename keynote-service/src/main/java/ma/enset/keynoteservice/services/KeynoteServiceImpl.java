package ma.enset.keynoteservice.services;

import lombok.AllArgsConstructor;
import ma.enset.keynoteservice.dtos.KeynoteDTO;
import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.mappers.KeynoteMapper;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class KeynoteServiceImpl implements KeynoteService {
    private KeynoteRepository keynoteRepository;
    private KeynoteMapper keynoteMapper;

    @Override
    public KeynoteDTO saveKeynote(KeynoteDTO keynoteDTO) {
        Keynote keynote = keynoteMapper.fromKeynoteDTO(keynoteDTO);
        Keynote savedKeynote = keynoteRepository.save(keynote);
        return keynoteMapper.fromKeynote(savedKeynote);
    }

    @Override
    public KeynoteDTO updateKeynote(KeynoteDTO keynoteDTO) {
        Keynote keynote = keynoteMapper.fromKeynoteDTO(keynoteDTO);
        Keynote saved = keynoteRepository.save(keynote);
        return keynoteMapper.fromKeynote(saved);
    }

    @Override
    public KeynoteDTO getKeynote(Long id) {
        Keynote keynote = keynoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Keynote not found"));
        return keynoteMapper.fromKeynote(keynote);
    }

    @Override
    public List<KeynoteDTO> getAllKeynotes() {
        return keynoteRepository.findAll().stream()
                .map(keynoteMapper::fromKeynote)
                .collect(Collectors.toList());
    }
}
