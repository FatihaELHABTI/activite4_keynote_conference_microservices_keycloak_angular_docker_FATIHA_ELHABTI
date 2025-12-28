# 📚 Conference Management System - Architecture Microservices

## 🎯 Vue d'ensemble du projet

Application complète de gestion de conférences construite avec une **architecture microservices**, utilisant **Spring Boot**, **Spring Cloud**, **Keycloak** pour l'authentification, et **Angular** pour le frontend.

---

## 🏗️ Architecture du Système

```
┌─────────────────────────────────────────────────────────────┐
│                     CLIENT (Angular)                         │
│                    Port: 4200                                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                  KEYCLOAK (Auth Server)                      │
│                    Port: 8080                                │
│                 Realm: sd-conference                         │
└─────────────────────────────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│               SPRING CLOUD GATEWAY                           │
│                    Port: 8888                                │
│              (Routage + CORS + Security)                     │
└──────────────────────┬──────────────────────────────────────┘
                       │
        ┌──────────────┼──────────────┐
        ▼              ▼               ▼
┌─────────────┐ ┌─────────────┐ ┌────────────┐
│   EUREKA    │ │   CONFIG    │ │  Services  │
│   SERVER    │ │   SERVER    │ │  Métiers   │
│  Port: 8761 │ │  Port: 9999 │ │            │
└─────────────┘ └─────────────┘ └────────────┘
                                       │
                        ┌──────────────┴──────────────┐
                        ▼                             ▼
                ┌──────────────┐            ┌──────────────┐
                │   KEYNOTE    │            │  CONFERENCE  │
                │   SERVICE    │◄───────────│   SERVICE    │
                │  Port: 8081  │  OpenFeign │  Port: 8082  │
                │   (H2 DB)    │            │   (H2 DB)    │
                └──────────────┘            └──────────────┘
```

---

## 📦 Technologies Utilisées

### Backend
- **Spring Boot 4.0.0** - Framework principal
- **Spring Cloud 2025.1.0** - Microservices (Eureka, Config, Gateway, OpenFeign)
- **Spring Security + OAuth2 Resource Server** - Sécurité JWT
- **Keycloak** - Serveur d'authentification (SSO)
- **H2 Database** - Base de données en mémoire
- **Lombok** - Réduction du code boilerplate
- **Resilience4j** - Circuit Breaker
- **Maven** - Gestion des dépendances

### Frontend
- **Angular 18+** (Standalone Components)
- **Keycloak JS Adapter** - Intégration SSO
- **HttpClient** - Communication HTTP
- **RxJS** - Programmation réactive
- **TypeScript** - Langage de programmation

---

## 🗂️ Structure du Projet

```
conference-app-distributed/
│
├── config-service/          # Configuration centralisée
│   ├── src/main/java/
│   │   └── ma/enset/configservice/
│   │       └── ConfigServiceApplication.java
│   ├── src/main/resources/
│   │   ├── config-repo/     # Fichiers de config (native)
│   │   └── application.yml
│   └── pom.xml
│
├── discovery-service/        # Service Discovery (Eureka)
│   ├── src/main/java/
│   │   └── ma/enset/discoveryservice/
│   │       └── DiscoveryServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── gateway-service/          # API Gateway (Spring Cloud Gateway)
│   ├── src/main/java/
│   │   └── ma/enset/gatewayservice/
│   │       ├── config/
│   │       │   ├── CorsConfig.java
│   │       │   └── SecurityConfig.java
│   │       └── GatewayServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── keynote-service/          # Microservice Speakers
│   ├── src/main/java/
│   │   └── ma/enset/keynoteservice/
│   │       ├── config/
│   │       │   ├── JwtAuthConverter.java
│   │       │   └── SecurityConfig.java
│   │       ├── dtos/
│   │       │   └── KeynoteDTO.java
│   │       ├── entities/
│   │       │   └── Keynote.java
│   │       ├── mappers/
│   │       │   └── KeynoteMapper.java
│   │       ├── repositories/
│   │       │   └── KeynoteRepository.java
│   │       ├── services/
│   │       │   ├── KeynoteService.java
│   │       │   └── KeynoteServiceImpl.java
│   │       ├── web/
│   │       │   └── KeynoteController.java
│   │       └── KeynoteServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
├── conference-service/       # Microservice Conférences
│   ├── src/main/java/
│   │   └── ma/enset/conferenceservice/
│   │       ├── clients/
│   │       │   └── KeynoteRestClient.java (OpenFeign)
│   │       ├── config/
│   │       │   ├── FeignConfig.java
│   │       │   ├── JwtAuthConverter.java
│   │       │   └── SecurityConfig.java
│   │       ├── entities/
│   │       │   ├── Conference.java
│   │       │   └── Review.java
│   │       ├── model/
│   │       │   └── Keynote.java (DTO)
│   │       ├── repositories/
│   │       │   ├── ConferenceRepository.java
│   │       │   └── ReviewRepository.java
│   │       ├── services/
│   │       │   ├── ConferenceService.java
│   │       │   └── ConferenceServiceImpl.java
│   │       ├── web/
│   │       │   ├── ConferenceController.java
│   │       │   └── ReviewController.java
│   │       └── ConferenceServiceApplication.java
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
│
└── conference-front/         # Application Angular
    ├── src/app/
    │   ├── conference-details/
    │   │   ├── conference-details.component.ts
    │   │   ├── conference-details.component.html
    │   │   └── conference-details.component.css
    │   ├── conferences/
    │   │   ├── conferences.component.ts
    │   │   ├── conferences.component.html
    │   │   └── conferences.component.css
    │   ├── interceptors/
    │   │   └── token.interceptor.ts
    │   ├── models/
    │   │   └── models.ts
    │   ├── services/
    │   │   ├── conference.service.ts
    │   │   ├── keynote.service.ts
    │   │   └── keycloak.service.ts
    │   ├── app.component.ts
    │   ├── app.component.html
    │   ├── app.config.ts
    │   └── app.routes.ts
    ├── angular.json
    ├── package.json
    └── tsconfig.json
```

---

## 🔧 Configuration Détaillée

### 1️⃣ Config Service (Port 9999)

**Rôle** : Centralise toutes les configurations des microservices.

#### application.yml
```yaml
server:
  port: 9999

spring:
  application:
    name: config-service
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          search-locations: classpath:/config-repo

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### ConfigServiceApplication.java
```java
package ma.enset.configservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServiceApplication.class, args);
    }
}
```

#### pom.xml (dépendances clés)
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>
```

---

### 2️⃣ Discovery Service - Eureka (Port 8761)

**Rôle** : Registry de services pour la découverte dynamique.

#### application.yml
```yaml
server:
  port: 8761

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
```

#### DiscoveryServiceApplication.java
```java
package ma.enset.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
```

**Accès Dashboard** : `http://localhost:8761`

---

### 3️⃣ Gateway Service (Port 8888)

**Rôle** : Point d'entrée unique, routage, CORS, sécurité.

#### application.yml
```yaml
server:
  port: 8888

spring:
  application:
    name: gateway-service
  config:
    import: "optional:configserver:http://localhost:9999"
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### CorsConfig.java
```java
package ma.enset.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfig.setMaxAge(3600L);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        
        UrlBasedCorsConfigurationSource source = 
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
        return new CorsWebFilter(source);
    }
}
```

#### SecurityConfig.java
```java
package ma.enset.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchange -> exchange
                .anyExchange().permitAll()
            );
        return http.build();
    }
}
```

**Routes automatiques** :
- `/keynote-service/**` → `http://keynote-service:8081`
- `/conference-service/**` → `http://conference-service:8082`

---

### 4️⃣ Keynote Service (Port 8081)

**Rôle** : Gestion des speakers (intervenants).

#### application.yml
```yaml
server:
  port: 8081

spring:
  application:
    name: keynote-service
  config:
    import: "optional:configserver:http://localhost:9999"
  datasource:
    url: jdbc:h2:mem:keynote-db
    driverClassName: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/sd-conference
          jwk-set-uri: http://localhost:8080/realms/sd-conference/protocol/openid-connect/certs

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### Keynote.java (Entité)
```java
package ma.enset.keynoteservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Keynote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String fonction;
}
```

#### KeynoteController.java
```java
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
```

#### JwtAuthConverter.java
```java
package ma.enset.keynoteservice.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = 
        new JwtGrantedAuthoritiesConverter();
    
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
            jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
            extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        
        return new JwtAuthenticationToken(jwt, authorities, 
            jwt.getClaim("preferred_username"));
    }
    
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> realmAccess;
        Collection<String> roles;
        
        if (jwt.getClaim("realm_access") == null) {
            return Set.of();
        }
        
        realmAccess = jwt.getClaim("realm_access");
        roles = (Collection<String>) realmAccess.get("roles");
        
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
    }
}
```

#### SecurityConfig.java
```java
package ma.enset.keynoteservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;
    
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
            );
        
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }
}
```

#### KeynoteServiceApplication.java (avec données de test)
```java
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
            
            keynoteRepository.findAll().forEach(k ->
                System.out.println("Keynote ajouté : " + k.getNom() + " " + k.getPrenom())
            );
        };
    }
}
```

---

### 5️⃣ Conference Service (Port 8082)

**Rôle** : Gestion des conférences et reviews.

#### application.yml
```yaml
server:
  port: 8082

spring:
  application:
    name: conference-service
  config:
    import: "optional:configserver:http://localhost:9999"
  datasource:
    url: jdbc:h2:mem:conference-db
    driverClassName: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/sd-conference
          jwk-set-uri: http://localhost:8080/realms/sd-conference/protocol/openid-connect/certs

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

#### Conference.java (Entité)
```java
package ma.enset.conferenceservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import ma.enset.conferenceservice.model.Keynote;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String type;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    
    private Integer duree;
    private Integer nombreInscrits;
    private Double score;
    private Long keynoteId; // ID externe
    
    @Transient // Pas persisté en base
    private Keynote keynote;
    
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
```

#### Review.java (Entité)
```java
package ma.enset.conferenceservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String texte;
    private Integer stars;
    
    @ManyToOne
    @JsonIgnore
    private Conference conference;
}
```

#### KeynoteRestClient.java (OpenFeign)
```java
package ma.enset.conferenceservice.clients;

import ma.enset.conferenceservice.model.Keynote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "keynote-service")
public interface KeynoteRestClient {
    @GetMapping("/api/keynotes/{id}")
    Keynote getKeynoteById(@PathVariable Long id);
}
```

#### FeignConfig.java (Propagation JWT)
```java
package ma.enset.conferenceservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = 
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                
                if (attributes != null) {
                    String authHeader = attributes.getRequest()
                        .getHeader("Authorization");
                    if (authHeader != null) {
                        template.header("Authorization", authHeader);
                    }
                }
            }
        };
    }
}
```

#### ConferenceController.java (avec sécurité)
```java
package ma.enset.conferenceservice.web;

import lombok.AllArgsConstructor;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.services.ConferenceService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Conference save(@RequestBody Conference conference) {
        return conferenceService.saveConference(conference);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Conference update(@PathVariable Long id, @RequestBody Conference conference) {
        return conferenceService.updateConference(id, conference);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        conferenceService.deleteConference(id);
    }
}
```

#### ConferenceServiceImpl.java (logique métier)
```java
package ma.enset.conferenceservice.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.conferenceservice.clients.KeynoteRestClient;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.model.Keynote;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final KeynoteRestClient keynoteRestClient;
    
    @Override
    public Conference saveConference(Conference conference) {
        log.info("Enregistrement d'une nouvelle conférence : {}", conference.getTitre());
        return conferenceRepository.save(conference);
    }
    
    @Override
    public Conference getConferenceById(Long id) {
        Conference conference = conferenceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conference introuvable"));
        enrichWithKeynoteData(conference);
        return conference;
    }
    
    @Override
    public List<Conference> getAllConferences() {
        List<Conference> conferences = conferenceRepository.findAll();
        conferences.forEach(this::enrichWithKeynoteData);
        return conferences;
    }
    
    private void enrichWithKeynoteData(Conference conference) {
        if (conference.getKeynoteId() != null) {
            try {
                Keynote keynote = keynoteRestClient.getKeynoteById(conference.getKeynoteId());
                conference.setKeynote(keynote);
            } catch (Exception e) {
                log.error("Erreur lors de la récupération du Keynote id {}", 
                    conference.getKeynoteId(), e);
                conference.setKeynote(null);
            }
        }
    }
}
```

#### ConferenceServiceApplication.java (avec données de test)
```java
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

import java.time.LocalDate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConferenceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConferenceServiceApplication.class, args);
    }
    
    @Bean
    CommandLineRunner start(ConferenceRepository conferenceRepository,
                            ReviewRepository reviewRepository) {
        return args -> {
            Conference c1 = Conference.builder()
                .titre("Architecture Micro-services avec Spring Cloud")
                .type("Academique")
                .date(LocalDate.now())
                .duree(120)
                .nombreInscrits(150)
                .score(4.8)
                .keynoteId(1L)
                .build();
            conferenceRepository.save(c1);
            
            Conference c2 = Conference.builder()
                .titre("Big Data Analytics en Temps Réel")
                .type("Commerciale")
                .date(LocalDate.now())
                .duree(90)
                .nombreInscrits(200)
                .score(4.5)
                .keynoteId(3L)
                .build();
            conferenceRepository.save(c2);
            
            
