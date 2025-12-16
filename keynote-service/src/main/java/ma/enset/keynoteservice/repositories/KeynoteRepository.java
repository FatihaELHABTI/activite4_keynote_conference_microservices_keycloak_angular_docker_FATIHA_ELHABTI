package ma.enset.keynoteservice.repositories;

import ma.enset.keynoteservice.entities.Keynote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeynoteRepository extends JpaRepository<Keynote, Long> {
}