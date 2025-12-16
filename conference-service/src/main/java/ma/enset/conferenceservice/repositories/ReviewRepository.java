package ma.enset.conferenceservice.repositories;

import ma.enset.conferenceservice.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}