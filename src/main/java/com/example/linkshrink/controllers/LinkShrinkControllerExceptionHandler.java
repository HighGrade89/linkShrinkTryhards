package com.example.linkshrink.controllers;

import com.example.linkshrink.constants.ErrorMessages;
import com.example.linkshrink.exception.InvalidURLException;
import com.example.linkshrink.exception.ResourceNotFoundException;
import com.example.linkshrink.exception.URLNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LinkShrinkControllerExceptionHandler {

    @ExceptionHandler(URLNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<URLNotFoundException> handleURLNotFoundExcpetion(URLNotFoundException exception) {
        String message = ErrorMessages.URL_NOT_FOUND;
        return new ResponseEntity(new JsonExceptionPresenter(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleRuntimeException(Exception exception) {
        return new ResponseEntity(new JsonExceptionPresenter(ErrorMessages.GENERAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidURLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleInvalidUrlExpection(Exception exception){
        return new ResponseEntity(new JsonExceptionPresenter(ErrorMessages.INBOUND_URL_INVALID), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class JsonExceptionPresenter {
        private String message;
    }
}
