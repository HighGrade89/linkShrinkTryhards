package com.example.linkshrink.exception;

import com.example.linkshrink.constants.ErrorMessages;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Обработчик исключений
 */

@Slf4j
@ControllerAdvice
public class LinkShrinkExceptionHandler {

    @ExceptionHandler(URLNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleURLNotFoundExcpetion(URLNotFoundException exception) {
        String message = ErrorMessages.URL_NOT_FOUND + " " + exception.getShortUrlSuffix();
        return new ResponseEntity(new JsonExceptionPresenter(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleURLNotFoundExcpetion(MethodArgumentNotValidException exception) {
        return new ResponseEntity(new JsonExceptionPresenter(ErrorMessages.MAPPER_ERROR), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleRuntimeException(Exception exception) {
        String message = ErrorMessages.GENERAL_ERROR;
        return new ResponseEntity(new JsonExceptionPresenter(message), HttpStatus.INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler(MappingErrorException.class)
    public ResponseEntity handleMappingErrorException(Exception e) {
        String message = ErrorMessages.MAPPER_ERROR;
        return new ResponseEntity(new JsonExceptionPresenter(message), HttpStatus.BAD_REQUEST);
    }

    @Data
    private static class JsonExceptionPresenter {
        private String message;

        public JsonExceptionPresenter(String message) {
            this.message = message;
            log.error(message);
        }
    }
}
