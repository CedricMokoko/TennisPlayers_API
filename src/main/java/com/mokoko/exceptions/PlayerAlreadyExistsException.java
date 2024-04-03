package com.mokoko.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String cognome) {
        super("Il player di cognome " + cognome + " already exists.");
    }
}
