package com.example.linkshrink.controllers;

import com.example.linkshrink.dto.WeblinkRequestDto;
import com.example.linkshrink.dto.WeblinkResponseDto;
import com.example.linkshrink.dto.WeblinkSubmitDto;
import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.entity.Weblinks;
import com.example.linkshrink.exception.MappingErrorException;
import com.example.linkshrink.service.interfaces.LinkShrinkService;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;


/**
 * Основной контроллер
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class LinkShrinkRestController {

    private final LinkShrinkService linkShrinkService;
    private final AmqpTemplate template;
    private final MapperFacade mapperFacade;

    /**
     * Эндпоинт добавления формирования короткой ссылки
     * @param weblinkSubmitDto объект DTO, содержащший полную ссылку
     * @return объект DTO, содержащий короткую ссылку
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public WeblinkResponseDto add(@RequestBody WeblinkSubmitDto weblinkSubmitDto) {
        Weblink weblink = (Weblink) template.convertSendAndReceive("q1", weblinkSubmitDto.getFullUrl());
        log.info("Added "+weblink.getShortUrlSuffix() + " from " + weblink.getFullUrl());
        return mapperFacade.map(weblink, WeblinkResponseDto.class);
    }

    /**
     * Эндпоинт для получения полной ссылки на основе короткой
     * @param weblinkRequestDto объект DTO, содержащший короткую ссылку
     * @return объект DTO, содержащий полную ссылку
     */
    @RequestMapping(value = "/resolve", method = RequestMethod.GET)
    @ResponseBody
    public WeblinkSubmitDto resolve(@Validated @RequestBody WeblinkRequestDto weblinkRequestDto) {
        Weblink shortWebLink = mapperFacade.map(weblinkRequestDto, Weblink.class);

        if (shortWebLink == null) {
            throw new MappingErrorException();
        }

        Weblink weblink = linkShrinkService.resolve(shortWebLink.getShortUrlSuffix());
        log.info("Resolved "+weblink.getFullUrl());
        return mapperFacade.map(weblink, WeblinkSubmitDto.class);
    }

    /**
     * Возвращает все сохраненные объекты Weblinks
     * @return список Weblinks
     */
    @GetMapping(value="/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Weblinks listAll() {
        return new Weblinks(linkShrinkService.findAll());
    }

}