package com.example.linkshrink.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.validation.annotation.Validated;

/**
 * DTO получения Weblink по короткой ссылке
 */
@NoArgsConstructor
@Getter
@Setter
@Validated
public class WeblinkRequestDto {
    private String shortUrl;
}
