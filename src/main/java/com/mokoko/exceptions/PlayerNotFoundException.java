package com.mokoko.exceptions;

/*Ici c'est une exception qui se verifie au RunTime quand
* l'application s'exécute*/
public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String cognome){
        super("Il player di cognome "+ cognome + " non è stato trovato.");
    }
}
