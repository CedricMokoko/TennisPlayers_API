package com.mokoko.rest;


import com.mokoko.utils.ErrorRecord;
import com.mokoko.model.PlayerModelRecord;
import com.mokoko.model.PlayerModelToSaveRecord;
import com.mokoko.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*On va documenter notre Api*/
@Tag(name = "Tennis Players API")
@RestController
public class PlayerRestController {

    @Autowired
    PlayerService playerService;


    /*Ici l'Api qui va nous retourner l'ensemble des players*/

    /*On va documenter notre Api*/
    @Operation(summary = "Finds players", description = "Ritorna la lista degli players")
    @ApiResponses( value = {
            @ApiResponse(responseCode ="200", description = "Players list",
                    content = {@Content(mediaType = "application/json",
                            /*Ici on renvoir un array*/
                            array = @ArraySchema(schema = @Schema(implementation = PlayerModelRecord.class))
                    )}),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @GetMapping("/players")
    public List<PlayerModelRecord> getAllPlayers(){
        return playerService.getAllPlayers();
    }


    /*On va documenter notre Api*/
    @Operation(summary = "Finds a player", description = "Ritorna un player")
    @ApiResponses( value = {
            /*Response in caso di success*/
            @ApiResponse(responseCode ="200", description = "Player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerModelRecord.class)
                    )}
            ),
            /*Response in caso di errore*/
            @ApiResponse(responseCode ="404", description = "Player with specified cognome was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRecord.class)
                    )}
            ),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @GetMapping("/players/{cognome}")
    public PlayerModelRecord getPlayerByCognome(@PathVariable("cognome") String cognome){
        return playerService.getPlayerByCognome(cognome);
    }


    /*On va documenter notre Api*/
    @Operation(summary = "Creates a player", description = "Crea un player")
    @ApiResponses( value = {
            /*Response in caso di success*/
            @ApiResponse(responseCode ="200", description = "Creates a player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerModelToSaveRecord.class)
                    )}),
            /*Response in caso di errore*/
            @ApiResponse(responseCode ="400", description = "Player with specified cognome already exists.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRecord.class)
                    )}
            ),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @PostMapping("/players")
    public PlayerModelRecord createPlayer(@RequestBody @Valid PlayerModelToSaveRecord playerModelToSaveRecord){
        return playerService.create(playerModelToSaveRecord);
    }

    /*On va documenter notre Api*/
    @Operation(summary = "Updates a player", description = "Aggiorna un player")
    @ApiResponses( value = {
            /*Response in caso di success*/
            @ApiResponse(responseCode ="200", description = "Updates a player",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlayerModelToSaveRecord.class)
                    )}),
            /*Response in caso di errore*/
            @ApiResponse(responseCode ="404", description = "Player with specified cognome was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRecord.class)
                    )}
            ),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @PutMapping("/players")
    public PlayerModelRecord updatePlayer(@RequestBody @Valid PlayerModelToSaveRecord playerModelToSaveRecord){
        return playerService.update(playerModelToSaveRecord);
    }

    /*On va documenter notre Api*/
    @Operation(summary = "Delete a player", description = "Rimuovi un player")
    @ApiResponses( value = {
            /*Response in caso di success*/

            /*Ici code 204 pour dire qu'on est en void et donc on a pas besoin du content
            * nella documentazione poiche non ritorniamo niente*/
            @ApiResponse(responseCode ="204", description = "Player has been deleted"),
            /*Response in caso di errore*/
            @ApiResponse(responseCode ="404", description = "Player with specified cognome was not found.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorRecord.class)
                    )}
            ),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")

    })
    @DeleteMapping("/players/{cognome}")
    public void deleteByCognome(@PathVariable("cognome") String cognome){
        playerService.delete(cognome);
    }
}
