package com.example.linkshrink.controllers;

import com.example.linkshrink.dto.WeblinkRequestDto;
import com.example.linkshrink.dto.WeblinkResponseDto;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.entity.Weblinks;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import com.example.linkshrink.controllers.formhandler.LinkShrinkFormHandler;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class LinkShrinkController {

    @Autowired
    private LinkShrinkService linkShrinkService;

    @Autowired
    AmqpTemplate template;

    private final MapperFacade mapperFacade;

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("linkShrinkFormHandler", new LinkShrinkFormHandler());
        return "index";
    }

    @PostMapping("/")
    public String fullLinkSubmit(@ModelAttribute(value = "linkShrinkFormHandler") LinkShrinkFormHandler linkShrinkFormHandler) {
        Weblink weblink = mapperFacade.map(linkShrinkFormHandler, Weblink.class);
        Weblink result = (Weblink) template.convertSendAndReceive("q1", weblink);

        String shortUrlSuffix = result.getShortUrlSuffix();
        String resultingShortUrl = "http://localhost:8080/"+shortUrlSuffix;

        linkShrinkFormHandler.setInboundFullUrl("");
        linkShrinkFormHandler.setResultingShortUrl(resultingShortUrl);
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Weblink add(@RequestBody Weblink weblink) {
        return (Weblink) template.convertSendAndReceive("q1", weblink);
    }

    @RequestMapping(value = "/resolve", method = RequestMethod.GET)
    @ResponseBody
    public WeblinkResponseDto resolve(@RequestBody WeblinkRequestDto weblinkRequestDto) {
        Weblink weblink = linkShrinkService.resolve(weblinkRequestDto.getShortUrlSuffix());
        return mapperFacade.map(weblink, WeblinkResponseDto.class);
    }

    @GetMapping("{shortUrl}")
    @ResponseBody
    public ModelAndView resolveAndRedirect(@PathVariable String shortUrl) {
        Weblink weblink = linkShrinkService.resolve(shortUrl);
        return new ModelAndView("redirect:"+weblink.getFullUrl());
    }

    //-- for ez debugging --

    @GetMapping("/id/{id}")
    @ResponseBody
    public WeblinkResponseDto resolveById(@PathVariable(name = "id") long id) {
        return mapperFacade.map(linkShrinkService.getById(id), WeblinkResponseDto.class);
    }

    @GetMapping(value="/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Weblinks listAll() {
        return new Weblinks(linkShrinkService.findAll());
    }

}