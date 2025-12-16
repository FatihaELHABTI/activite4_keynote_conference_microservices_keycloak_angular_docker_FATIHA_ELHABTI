package ma.enset.keynoteservice;

import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class KeynoteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeynoteServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(KeynoteRepository keynoteRepository) {
        return args -> {
            // Création de données de test
            keynoteRepository.save(Keynote.builder()
                    .nom("Hassani")
                    .prenom("Mohamed")
                    .email("med@gmail.com")
                    .fonction("Software Architect")
                    .build());

            keynoteRepository.save(Keynote.builder()
                    .nom("Alami")
                    .prenom("Salma")
                    .email("salma@outlook.com")
                    .fonction("DevOps Engineer")
                    .build());

            keynoteRepository.save(Keynote.builder()
                    .nom("Benali")
                    .prenom("Ahmed")
                    .email("ahmed@yahoo.fr")
                    .fonction("Data Scientist")
                    .build());

            // Affichage pour confirmer
            keynoteRepository.findAll().forEach(k ->
                    System.out.println("Keynote ajouté : " + k.getNom() + " " + k.getPrenom())
            );
        };
    }
}