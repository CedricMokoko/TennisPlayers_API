package com.mokoko.rest;

import com.mokoko.exceptions.PlayerAlreadyExistsException;
import com.mokoko.exceptions.PlayerNotFoundException;
import com.mokoko.utils.ErrorRecord;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class PlayerRestControllerAdvice {

    /*ci on dit que lorsqu'il y a une exception de type "PlayerNotFoundException"
    * notre exception custom per la ricerca. Et c'est notre methode "handlePlayerNotFoundException"
    *  qui va s'en occuper*/
    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) /*Au niveau Rest, et quand on utilise le protocolle HTTP la
    semantique veut que quand on ne peut pas trouver un element on renvoie un 404
    per dire que mi hanno chiesto una risorsa passandomi un parametre et non l'ho trovata*/
    public ErrorRecord handlePlayerNotFoundException(PlayerNotFoundException ex){
      return new ErrorRecord(ex.getMessage());
    }

    /*ci on dit que lorsqu'il y a une exception de type "PlayerAlreadyExistsException"
     * notre exception custom per il create. Et c'est notre methode "handlePlayerAlreadyExistsException"
     *  qui va s'en occuper*/
    @ExceptionHandler(PlayerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) /*Code 400*/
    public ErrorRecord handlePlayerAlreadyExistsException(PlayerAlreadyExistsException ex){
        return new ErrorRecord(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  /*Code 400*/
    public Map<String, String> handleValidionException(MethodArgumentNotValidException ex){
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return  errors;
    }
}
