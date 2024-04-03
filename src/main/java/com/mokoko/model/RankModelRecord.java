package com.mokoko.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record RankModelRecord(
        @Positive(message = "La posizione deve essere un numero intero positivo")  /*Pour la validation de type int*/
        int position,
        @PositiveOrZero(message = "Il punteggio deve essere un numero intero superiore o uguale a zero")  /*Pour dire qu'on peut accepter anche zero*/
        int punti
) {
}
