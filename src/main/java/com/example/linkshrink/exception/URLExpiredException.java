package com.example.linkshrink.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Исключение, сигнализирующее об истечени срока действия ссылки
 */

@Getter
@AllArgsConstructor
public class URLExpiredException extends RuntimeException {
    String fullUrl;
}
