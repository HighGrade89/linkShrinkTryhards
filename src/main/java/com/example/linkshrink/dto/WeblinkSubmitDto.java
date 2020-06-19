package com.example.linkshrink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO запроса по полной ссылке
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeblinkSubmitDto {
    private String fullUrl;
}