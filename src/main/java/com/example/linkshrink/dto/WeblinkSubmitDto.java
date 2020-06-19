package com.example.linkshrink.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * DTO запроса по полной ссылке
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class WeblinkSubmitDto {

    @NotBlank
    private String fullUrl;
}