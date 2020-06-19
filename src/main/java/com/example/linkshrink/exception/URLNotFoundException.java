package com.example.linkshrink.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Исключение, сигнализирующее об отсутствиии запрашиваемой ссылки
 */
@Getter
@AllArgsConstructor
public class URLNotFoundException extends RuntimeException {
    private String shortUrl;
}