package com.example.linkshrink.controllers.formhandler;

import lombok.*;

/**
 * Класс поддержи предавления Thymeleaf
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LinkShrinkFormHandler {
    private String inboundFullUrl;
    private String resultingShortUrl;
}
