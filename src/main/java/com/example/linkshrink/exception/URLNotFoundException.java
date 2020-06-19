package com.example.linkshrink.exception;

import lombok.Getter;

/**
 * Исключение, сигнализирующее об отсутствиии запрашиваемой ссылки
 */
@Getter
public class URLNotFoundException extends RuntimeException {
    private String shortUrlSuffix;

    public URLNotFoundException(String shortUrlSuffix) {
        this.shortUrlSuffix = shortUrlSuffix;
    }
}