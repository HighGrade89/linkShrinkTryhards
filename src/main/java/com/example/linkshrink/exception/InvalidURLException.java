package com.example.linkshrink.exception;

import com.example.linkshrink.constants.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = ErrorMessages.GENERAL_ERROR)
public class InvalidURLException extends RuntimeException {
}
