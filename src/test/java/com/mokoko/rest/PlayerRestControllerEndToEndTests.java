package com.mokoko.rest;

import com.mokoko.entities.PlayerEntity;
import com.mokoko.model.PlayerModelRecord;
import com.mokoko.model.PlayerModelToSaveRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

/* La proprieté "webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT"
* permet d'éviter les conflits de port dans le context d'exécution des tests.
* Elle permet de rendre aleatoire le port d'écoute du serveur d'application.
* Et donc à chaque démarrage de l'application il y a un port d'ecoute du serveur
* qui est randomisé(alléatoire)*/

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class PlayerRestControllerEndToEndTests {

    @LocalServerPort /*Nous permet d'avoir connaissance du port utilisé*/
    private int port;

    /*"TestRestTemplate" est un librarie qui est fournie par Spring Boot Test
    * pour nous permettre d'èxecuter des requètes*/
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCreatePlayer() {
        // Given
        PlayerModelToSaveRecord playerToCreate = new PlayerModelToSaveRecord(
                "Carlos",
                "Alcaraz",
                LocalDate.of(2003, Month.MAY, 5),
                4500
        );
        /* When (Ici on va construire une requete de manière programmatique
        avec "TestRestTemplate" */
        String url = "http://localhost:" + port + "/players";
        HttpEntity<PlayerModelToSaveRecord> request = new HttpEntity<>(playerToCreate);
        ResponseEntity<PlayerModelRecord> playerResponseEntity = this.restTemplate.postForEntity(url, request, PlayerModelRecord.class);
        // Then
        assertThat(playerResponseEntity.getBody().cognome()).isEqualTo("Alcaraz");
        assertThat(playerResponseEntity.getBody().rank().position()).isEqualTo(2);
        Assertions.assertThat(playerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldFailToCreatePlayer_WhenPlayerToCreateIsInvalid() {
        // Given (Ici on passe un jour invalide)
        PlayerModelToSaveRecord playerToCreate = new PlayerModelToSaveRecord(
                "Carlos",
                null, //Ici ça nous sert aussi à verifier notre validation sur l'attribut cognome
                LocalDate.of(2003, Month.MAY, 5),
                4500
        );
        /* When (Ici on va construire une requete de manière programmatique
        avec "TestRestTemplate" */
        String url = "http://localhost:" + port + "/players";
        HttpEntity<PlayerModelToSaveRecord> request = new HttpEntity<>(playerToCreate);
        ResponseEntity<PlayerModelRecord> playerResponseEntity = this.restTemplate.exchange(url, HttpMethod.POST, request, PlayerModelRecord.class);
        // Then
        Assertions.assertThat(playerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldUpdatePlayerRanking() {
        // Given
        PlayerModelToSaveRecord playerToUpdate = new PlayerModelToSaveRecord(
                "Cédric",
                "Mokoko",
                LocalDate.of(1997, Month.MARCH, 4),
                4400
        );
        /* When (Ici on va construire une requete de manière programmatique
        avec "TestRestTemplate" */
        String url = "http://localhost:" + port + "/players";
        HttpEntity<PlayerModelToSaveRecord> request = new HttpEntity<>(playerToUpdate);
        ResponseEntity<PlayerModelRecord> playerResponseEntity = this.restTemplate.exchange(url, HttpMethod.PUT, request, PlayerModelRecord.class);
        // Then
        assertThat(playerResponseEntity.getBody().cognome()).isEqualTo("Mokoko");
        assertThat(playerResponseEntity.getBody().rank().position()).isEqualTo(1);
        Assertions.assertThat(playerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldDeletePlayer() {
        // Given / When
        String url = "http://localhost:" + port + "/players";
        this.restTemplate.exchange(url + "/mokoko", HttpMethod.DELETE, null, PlayerModelRecord.class);
        HttpEntity<List<PlayerModelRecord>> allPlayersResponseEntity = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PlayerModelRecord>>() {}
        );
        // Then
        Assertions.assertThat(allPlayersResponseEntity.getBody())
                .extracting("cognome", "rank.position")
                .containsExactly(tuple("Djokovic", 1), tuple("Federer", 2));
    }

}
