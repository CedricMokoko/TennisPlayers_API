package com.mokoko.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record PlayerModelRecord(
        @NotBlank(message = "nome required")
        String nome,
        @NotBlank (message = "cognome required")
        String cognome,
        @NotNull(message = "Birth Date requered") /* @NotNull perché @NotBlank non si puo' applicare alle date*/
        @PastOrPresent(message = "Birth Date must be past or present") /*Validation pour la LocalDate. Pour dire qu'on a bien un attribut de type Date qui est dans le present oppure nel passato*/
                LocalDate birthDate,

        /*On valide aussi RankRecord dans la classe PlayerRecord car on lui a appliqué
         * des contrainte sur ses attributs*/
        @Valid RankModelRecord rank
) {
}
