package com.mokoko.rest;

import com.mokoko.exceptions.PlayerNotFoundException;
import com.mokoko.service.PlayerService;
import com.mokoko.utils.PlayerList;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*Ici on ne va tester que la partie Rest et on ne va pas instancier
tout un serveur d'application, puisqu'on pourrait démarer tout un serveur
d'application et venir consommer l'API, sauf que dans le cas de Test Unitaire
on ne veut pas faire tout ça puisqu'on veut juste charger le minimum nécessaire
pour tester le composant de la couche que l'on veut tester

@WebMvcTest nous permet de tester une couche rest sans le serveur
d'application. Grace à cette dernière on dit qu'il  faut cahrger un context d'application
mais sans le serveur.

"controllers = PlayerRestController.class" nous permet de préciser
le controller qu'on veut tester*/
@WebMvcTest(controllers = PlayerRestController.class)
public class PlayerRestControllerUnitTests {

    /*On injecte MockMvc pour exécuter des requetes
     * sur notre controllerRest qui est instancié qui nous fournit
     * des Utilitaires pour pouvoir déclencher des requètes */
    @Autowired
    private MockMvc mockMvc;

    /*On Mocker le PlayerService, grace @MockBean on remplace
     * sont injection par une instance Mockito*/
    @MockBean
    private PlayerService playerService;

    @Test
    public void shouldListAllPlayers() throws Exception {
        // Given
        Mockito.when(playerService.getAllPlayers()).thenReturn(PlayerList.ALL);

        // When / Then
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].cognome", CoreMatchers.is("Mokoko")))
                .andExpect(jsonPath("$[1].cognome", CoreMatchers.is("Matokou")))
                .andExpect(jsonPath("$[2].cognome", CoreMatchers.is("Nadal")));
    }

    @Test //Sur la même base, on peut tester le cas nominal de cette fonctionnalité
    public void shouldRetrievePlayer() throws Exception {
        // Given
        String playerToRetrieve = "mokoko";
        Mockito.when(playerService.getPlayerByCognome(playerToRetrieve)).thenReturn(PlayerList.CEDRIC_MOKOKO);

        // When / Then
        mockMvc.perform(get("/players/mokoko"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cognome", CoreMatchers.is("Mokoko"))) //CaseSensitive
                .andExpect(jsonPath("$.rank.position", CoreMatchers.is(1)));
    }

    @Test // Comportement du contrôleur lorsque le service lève une exception :
    public void shouldReturn404NotFound_WhenPlayerDoesNotExist() throws Exception {
        // Given
        String playerToRetrieve = "doe";
        Mockito.when(playerService.getPlayerByCognome(playerToRetrieve)).thenThrow(new PlayerNotFoundException("Il player di cognome doe non è stato trovato."));

        // When / Then
        mockMvc.perform(get("/players/doe"))
                .andExpect(status().isNotFound());
    }
}