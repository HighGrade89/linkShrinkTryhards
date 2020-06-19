package com.example.linkshrink.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * DTO получения Weblink по короткой ссылке
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class WeblinkRequestDto {

    @JsonProperty("shortUrl")
    @NotBlank
    private String shortUrl;
}
