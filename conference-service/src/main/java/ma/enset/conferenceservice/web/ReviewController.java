package ma.enset.conferenceservice.web;

import lombok.AllArgsConstructor;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewController {
    private ReviewRepository reviewRepository;
    private ConferenceRepository conferenceRepository;

    @PostMapping
    public Review addReview(@RequestBody Review review, @RequestParam Long conferenceId) {
        Conference conf = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new RuntimeException("Conférence introuvable"));

        review.setConference(conf);
        review.setDate(LocalDate.now()); // Date automatique

        return reviewRepository.save(review);
    }
}