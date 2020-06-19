package com.example.linkshrink.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Исключение, сигнализирующее о некорректности ссылки
 */
@Getter
@AllArgsConstructor
public class URLInvalidException extends RuntimeException {
    private String fullUrl;
}
