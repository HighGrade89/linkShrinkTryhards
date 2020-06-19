package com.example.linkshrink.controllers;

import com.example.linkshrink.constants.HostName;
import com.example.linkshrink.dto.WeblinkRequestDto;
import com.example.linkshrink.dto.WeblinkResponseDto;

import com.example.linkshrink.dto.WeblinkSubmitDto;
import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.entity.Weblinks;
import com.example.linkshrink.exception.MappingErrorException;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import com.example.linkshrink.controllers.formhandler.LinkShrinkFormHandler;

import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

/**
 * Основной контроллер
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class LinkShrinkController {

    @Autowired
    private LinkShrinkService linkShrinkService;

    @Autowired
    AmqpTemplate template;

    private final MapperFacade mapperFacade;

    /**
     * Возвращает индексную страницу
     * @param model объект модели
     * @return название шаблона
     */
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("linkShrinkFormHandler", new LinkShrinkFormHandler());
        log.info("Loading index");
        return "index";
    }

    /**
     * Обработчик нажатия кнопки добавления ссылки
     * @param linkShrinkFormHandler объект поддержки формы
     * @return название шаблона
     */
    @PostMapping("/")
    public String fullLinkSubmit(@ModelAttribute(value = "linkShrinkFormHandler") LinkShrinkFormHandler linkShrinkFormHandler) {
        String fullUrl = linkShrinkFormHandler.getInboundFullUrl();
        Weblink weblink = (Weblink) template.convertSendAndReceive("q1", fullUrl);

        log.info("Added "+weblink.getShortUrlSuffix() + " from " + weblink.getFullUrl());

        String shortUrlSuffix = weblink.getShortUrlSuffix();
        String resultingShortUrl = HostName.hostUrl+shortUrlSuffix;

        linkShrinkFormHandler.setInboundFullUrl("");
        linkShrinkFormHandler.setResultingShortUrl(resultingShortUrl);
        return "index";
    }

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
    public WeblinkSubmitDto resolve(@Validated @NotNull @RequestBody WeblinkRequestDto weblinkRequestDto) throws MappingErrorException {
        Weblink shortWebLink = mapperFacade.map(weblinkRequestDto, Weblink.class);

        if (shortWebLink == null) {
            throw new MappingErrorException();
        }

        Weblink weblink = linkShrinkService.resolve(shortWebLink.getShortUrlSuffix());
        log.info("Resolved "+weblink.getFullUrl());
        return mapperFacade.map(weblink, WeblinkSubmitDto.class);
    }

    /**
     * Эндпонт для перехода по короткой ссылке
     * @param shortUrlSuffix - суффикс короткой ссылки
     * @return объект редиректа
     */
    @GetMapping("{shortUrlSuffix}")
    @ResponseBody
    public ModelAndView resolveAndRedirect(@PathVariable String shortUrlSuffix) {
        Weblink weblink = linkShrinkService.resolve(shortUrlSuffix);
        log.info("Redirecting to "+weblink.getFullUrl());
        return new ModelAndView("redirect:"+weblink.getFullUrl());
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