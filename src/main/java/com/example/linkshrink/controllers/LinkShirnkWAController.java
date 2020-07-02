package com.example.linkshrink.controllers;

import com.example.linkshrink.constants.HostName;
import com.example.linkshrink.controllers.formhandler.LinkShrinkFormHandler;
import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.service.interfaces.LinkShrinkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Контроллер веб-интерфейсаа
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class LinkShirnkWAController {

    private final AmqpTemplate template;
    private final LinkShrinkService linkShrinkService;

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

}
