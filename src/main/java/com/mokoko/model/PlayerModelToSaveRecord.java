package com.mokoko.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

/*Ici on va définire notre Objet PlayerToRegisterRecord pour enregistrer un new joueur
* en lui definissant déjà des points et poi nella logica faremo in modo
* che la notre classe Service possa ricalcolare la classifica in base ai punti*/
public record PlayerModelToSaveRecord(
        @NotBlank(message = "nome required")
       String nome,
        @NotBlank (message = "nome required")
       String cognome,
        @NotNull (message = "Birth Date requered") /* @NotNull perché @NotBlank non si puo' applicare alle date*/
       @PastOrPresent(message = "Birth Date must be past or present") /*Validation pour la LocalDate. Pour dire qu'on a bien un attribut de type Date qui est dans le present oppure nel passato*/
       LocalDate birthDate,


       @PositiveOrZero(message = "point must be more than zero")
        int punti) {
}
