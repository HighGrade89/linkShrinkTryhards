package com.example.linkshrink.exception;

import com.example.linkshrink.constants.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ErrorMessages.URL_NOT_FOUND)
public class URLNotFoundException extends RuntimeException {
}

