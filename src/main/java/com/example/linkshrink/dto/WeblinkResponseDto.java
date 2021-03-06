package com.example.linkshrink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO возвращения короткой ссылки по длинной ссылке
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeblinkResponseDto {
    private String shortUrl;
}