package com.example.linkshrink.controllers;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.entity.Weblinks;
import com.example.linkshrink.exception.InvalidURLException;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class LinkShrinkController {

    @Autowired
    private LinkShrinkService linkShrinkService;


    @RequestMapping(value="/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Weblinks list() {
        return new Weblinks(linkShrinkService.findAll());
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Weblink getById(@PathVariable(name = "id") long id) {
        return linkShrinkService.getById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Weblink add(@RequestBody Weblink weblink) throws InvalidURLException {
        return linkShrinkService.add(weblink);
    }

    @RequestMapping(value = "{shrinked}")
    @ResponseBody
    public ModelAndView resolve(@PathVariable String shrinked) {
        Weblink weblink = linkShrinkService.resolve(shrinked);
        return new ModelAndView("redirect:"+weblink.getFullLink());
    }
}