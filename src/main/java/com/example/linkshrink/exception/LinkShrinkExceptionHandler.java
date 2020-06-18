package com.example.linkshrink.exception;

import com.example.linkshrink.constants.ErrorMessages;
import com.example.linkshrink.exception.URLExpiredException;
import com.example.linkshrink.exception.URLInvalidException;
import com.example.linkshrink.exception.URLNotFoundException;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LinkShrinkExceptionHandler {

    @ExceptionHandler(URLNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleURLNotFoundExcpetion(URLNotFoundException exception) {
        String message = ErrorMessages.URL_NOT_FOUND;
        return new ResponseEntity(new JsonExceptionPresenter(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleRuntimeException(Exception exception) {
        return new ResponseEntity(new JsonExceptionPresenter(ErrorMessages.GENERAL_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(URLInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleInvalidUrlExpection(Exception exception){
        return new ResponseEntity(new JsonExceptionPresenter(ErrorMessages.INBOUND_URL_INVALID), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(URLExpiredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleUrlExpiredException(URLExpiredException exception) {
        String message = ErrorMessages.URL_HAS_EXPIRED;
        return new ResponseEntity(new JsonExceptionPresenter(message), HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class JsonExceptionPresenter {
        private String message;
    }
}
