package com.mokoko.rest;

import com.mokoko.model.HealthCheckModelRecord;
import com.mokoko.service.HealthCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*On va documenter notre Api*/
@Tag(name = "HealthCheck API")
//Niveau 4 (après le service)
@RestController
public class HealthCheckRestController {

    /*Et donc on va pouvoir ici venir injecter notre classe service ici dans notre RestController */
    @Autowired /*On va venire injecter une instance de HealthCheckService lorsque l'appliactione va s'exécuter */
    private HealthCheckService service;

    /*On va documenter notre Api*/
    @Operation(summary = "Ritorna lo stato dell'applicazione", description = "Ritorna lo stato dell'applicazione")
    @ApiResponses( value = {
            @ApiResponse(responseCode ="200", description = "Lo stato di salute con alcuni dettagli",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = HealthCheckModelRecord.class)
            )})
    })
    @GetMapping("/healthCheck")
    public HealthCheckModelRecord healthCheckRecord(){
        return service.healthCheckRecord();
    }
}