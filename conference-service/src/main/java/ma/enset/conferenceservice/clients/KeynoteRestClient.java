package ma.enset.conferenceservice.clients;

import ma.enset.conferenceservice.model.Keynote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "keynote-service") // Nom exact du service dans Eureka
public interface KeynoteRestClient {
    @GetMapping("/api/keynotes/{id}")
    Keynote getKeynoteById(@PathVariable Long id);
}