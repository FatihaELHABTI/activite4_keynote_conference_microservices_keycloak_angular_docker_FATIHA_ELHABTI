package ma.enset.conferenceservice;

import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableDiscoveryClient // S'enregistre dans Eureka
@EnableFeignClients   // Active la communication avec les autres micro-services
public class ConferenceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ConferenceRepository conferenceRepository,
                            ReviewRepository reviewRepository) {
        return args -> {
            // 1. Création des conférences (On utilise les IDs 1, 2, 3 des Keynotes créés plus haut)

            Conference c1 = Conference.builder()
                    .titre("Architecture Micro-services avec Spring Cloud")
                    .type("Academique")
                    .date(new Date())
                    .duree(120)
                    .nombreInscrits(150)
                    .score(4.8)
                    .keynoteId(1L) // Mohamed Hassani
                    .build();
            conferenceRepository.save(c1);

            Conference c2 = Conference.builder()
                    .titre("Big Data Analytics en Temps Réel")
                    .type("Commerciale")
                    .date(new Date())
                    .duree(90)
                    .nombreInscrits(200)
                    .score(4.5)
                    .keynoteId(3L) // Ahmed Benali
                    .build();
            conferenceRepository.save(c2);

            // 2. Création de Reviews pour la conférence 1
            reviewRepository.save(Review.builder()
                    .date(new Date())
                    .texte("Conférence très intéressante et bien expliquée !")
                    .stars(5)
                    .conference(c1)
                    .build());

            reviewRepository.save(Review.builder()
                    .date(new Date())
                    .texte("Bon contenu mais un peu long.")
                    .stars(4)
                    .conference(c1)
                    .build());

            // 3. Création de Reviews pour la conférence 2
            reviewRepository.save(Review.builder()
                    .date(new Date())
                    .texte("Exemples concrets pertinents.")
                    .stars(5)
                    .conference(c2)
                    .build());

            System.out.println("Données de test conférences et reviews chargées...");
        };
    }
}