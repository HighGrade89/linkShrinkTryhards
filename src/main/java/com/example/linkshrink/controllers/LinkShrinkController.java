package com.example.linkshrink.controllers;

import com.example.linkshrink.dto.WeblinkResponseDto;
import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.entity.Weblinks;
import com.example.linkshrink.exception.InvalidURLException;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/")
public class LinkShrinkController {

    @Autowired
    private LinkShrinkService linkShrinkService;

    @Autowired
    AmqpTemplate template;

    private final MapperFacade mapperFacade;

    @PostMapping("/q/add")
    @ResponseBody
    Weblink queuedAdd(@RequestBody Weblink weblink) {
        Weblink result = (Weblink) template.convertSendAndReceive("queue1", weblink);
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Weblink add(@RequestBody WeblinkResponseDto weblinkResponseDto) throws InvalidURLException {
        return linkShrinkService.add(weblinkResponseDto.getFullUrl());
    }

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("linkShrinkFormHandler", new LinkShrinkFormHandler());
        return "index";
    }

    @PostMapping("/")
    public String fullLinkSubmit(@ModelAttribute(value = "linkShrinkFormHandler") LinkShrinkFormHandler linkShrinkFormHandler) {
        Weblink result = linkShrinkService.add(linkShrinkFormHandler.getIboundFullLink());
        String shortUrlSuffu = result.getShortUrlSuffux();
        String resultingShortLink = "http://localhost:8080/"+shortUrlSuffu;

        linkShrinkFormHandler.setIboundFullLink("");
        linkShrinkFormHandler.setResultingShortLink(resultingShortLink);
        return "index";
    }

    @GetMapping(value="/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Weblinks list() {
        return new Weblinks(linkShrinkService.findAll());
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public WeblinkResponseDto resolveById(@PathVariable(name = "id") long id) {
        return mapperFacade.map(linkShrinkService.getById(id), WeblinkResponseDto.class);
    }

    @GetMapping("{shrinked}")
    @ResponseBody
    public ModelAndView resolve(@PathVariable String shrinked) {
        Weblink weblink = linkShrinkService.resolve(shrinked);
        return new ModelAndView("redirect:"+weblink.getFullUrl());
    }
}