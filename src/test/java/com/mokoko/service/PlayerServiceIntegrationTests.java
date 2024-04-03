package com.mokoko.service;

import com.mokoko.exceptions.PlayerNotFoundException;
import com.mokoko.model.PlayerModelRecord;
import com.mokoko.model.PlayerModelToSaveRecord;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


//                          TESTS D'INTEGRATION ENTRE LA COUCHE SERVICES ET LA COUCHE ENTITIES(DB)

/*Avec l'annotation SpringBootTest on charge le context applicatif
* afin de pouvoir faire des injection avec  @Autowired */
@SpringBootTest
/* @DirtiesContext permet de reinitialiser le context avant chaque methodes pour éviter les interférence
* donc la base de données H2 va etre détruite et recrée avant le test suivant*/
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerServiceIntegrationTests {

    /*Ici on va utiliser la vraie implementation du PlayerService
    et pour pouvoir faire cette injection il faut charger le context applicatif
    con l'annotation @SpringBootTest directement sur la classe de test.*/
    @Autowired
    private PlayerService playerService;

    @Test
    public void shouldCreatePlayer() {
        // Given
        PlayerModelToSaveRecord newPlayer = new PlayerModelToSaveRecord(
                "Emmanuel",
                "Bayito",
                LocalDate.of(2011, Month.JANUARY, 1),
                10000
        );
        // When
        playerService.create(newPlayer);
        /*Ici on verifie que notre joueur à bien été crée*/
        PlayerModelRecord createdPlayer = playerService.getPlayerByCognome("Bayito");

        // Then (Ici on verifie les données de notre newPlayer)
        Assertions.assertThat(createdPlayer.nome()).isEqualTo("Emmanuel");
        Assertions.assertThat(createdPlayer.cognome()).isEqualTo("Bayito");
        Assertions.assertThat(createdPlayer.birthDate()).isEqualTo(LocalDate.of(2011, Month.JANUARY, 1));
        Assertions.assertThat(createdPlayer.rank().punti()).isEqualTo(10000);
        Assertions.assertThat(createdPlayer.rank().position()).isEqualTo(1);
    }

    @Test
    public void shouldUpdatePlayer() {
        // Given
        PlayerModelToSaveRecord playerToUpdate = new PlayerModelToSaveRecord(
                "Cédric",
                "Mokoko",
                LocalDate.of(1997, Month.MARCH, 4),
                1000
        );
        // When
        playerService.update(playerToUpdate);
        /*Ici on verifie que notre joueur à bien été crée*/
        PlayerModelRecord updatedPlayer = playerService.getPlayerByCognome(playerToUpdate.cognome());

        // Then (Ici on verifie les données de notre newPlayer)
       Assertions.assertThat(updatedPlayer.rank().position()).isEqualTo(3);
    }

    @Test
    public void shouldDeletePlayer() {
        // Given
        String playerToDelete = "Mokoko";

        // When
        playerService.delete(playerToDelete);
        List<PlayerModelRecord> allPlayers = playerService.getAllPlayers();

        // Then
        Assertions.assertThat(allPlayers)
                .extracting("cognome", "rank.position")
                .containsExactly(Tuple.tuple("Djokovic", 1), Tuple.tuple("Federer", 2));
                //Ici Tuple car on extrait une paire de valeur
    }

    @Test
    public void shouldFailToDeletePlayer_WhenPlayerDoesNotExist() {
        // Given
        String playerToDelete = "Lol";

        // When / Then
        Exception exception = assertThrows(PlayerNotFoundException.class, () -> {
            playerService.delete(playerToDelete);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("Il player di cognome Lol non è stato trovato.");
    }


}
