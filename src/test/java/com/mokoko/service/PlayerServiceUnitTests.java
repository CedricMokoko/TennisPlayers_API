package com.mokoko.service;

import com.mokoko.exceptions.PlayerNotFoundException;
import com.mokoko.utils.PlayerEntityList;
import com.mokoko.model.PlayerModelRecord;
import com.mokoko.repository.PlayerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

//                          TESTS UNITAIRES DANS LA COUCHE SERVICES


/*Sachant que PlayerService depend dell'Interfaccia PlayerRepository, on va
* donc devoir Mocker PlayerRepository, cioè mettre en place une fausse implementation
* de PlayerRepository, on va donc pouvoir piloter son comportement et c'est ce qui nous
* permettra de tester en isolation donc de maniere unitaire PlayerService.
* Donc on fait abstraction de la connection al database, e quindi possiamo fare
* il nostro test unitario su PlayerService.   */
public class PlayerServiceUnitTests {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    /*Donc au debut du test on va dire à Mockito di inizializzare i Mocks
    * di  questa classe PlayerServiceTest. (Per questo mettiamo "this")
    * Et on va donc pouvoir injecter notre Mock dans playerService*/
    @BeforeEach /*Cosi da generate un nuovo Mock prima di ogni metodo di test*/
    public  void setUp(){
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    // 1 - Test de getAllPlayers()

    @Test
    public void shouldReturnPlayersRanking(){
        //Given

        /*Donc quand la methode findAll() de playerRepository sera
        * appelée, notre Mockito dovra ritornare La liste de player
        * fitizia che ci siamo creati per simolare il database*/
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);

        //When
        List<PlayerModelRecord> allPlayers =
                playerService.getAllPlayers();

        //Then  (Dans cette partie on fait des controls)
        Assertions.assertThat(allPlayers)
                .extracting("cognome")
                .containsExactly("Mokoko", "Nadal", "Matokou"); /* è case sensitive*/
    }

    // 2- Test de getPlayerByCognome()

    @Test // (Con valid input )
    public void shouldRetrievePlayerByCognome(){
        //Given
        String playerToRetrieve = "mokoko";
        Mockito.when(playerRepository.findOneByCognomeIgnoreCase(playerToRetrieve)).thenReturn(Optional.of(PlayerEntityList.CEDRIC_MOKOKO));

        //When
        PlayerModelRecord playerRetrieved = playerService.getPlayerByCognome(playerToRetrieve);

        //Then (Dans cette partie on fait des controls)
        Assertions.assertThat(playerRetrieved.cognome()).isEqualTo("Mokoko");
        Assertions.assertThat(playerRetrieved.nome()).isEqualTo("Cédric");
        Assertions.assertThat(playerRetrieved.rank().position()).isEqualTo(1);
    }

    @Test // (Con invalid input )
    public void shouldFailToRetrieve_WhenPlayerByCognomeDoesNotExists() {
        //Given
        String unknownPlayer = "lol";
        Mockito.when(playerRepository.findOneByCognomeIgnoreCase(unknownPlayer)).thenReturn(Optional.empty());

        //When /Then
        Exception exception = assertThrows(PlayerNotFoundException.class, () ->
                playerService.getPlayerByCognome(unknownPlayer));
        Assertions.assertThat(exception.getMessage()).isEqualTo("Il player di cognome lol non è stato trovato.");
    }
}
