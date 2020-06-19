package com.example.linkshrink.mapper;

import com.example.linkshrink.controllers.formhandler.LinkShrinkFormHandler;
import com.example.linkshrink.entity.Weblink;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Маппер объекта поддержки формы в сущность
 */
@Component
public class LinkShrinkFormHandlerMapper extends AbstractMapperRegister {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(LinkShrinkFormHandler.class, Weblink.class)
                .byDefault()
                .customize(new CustomMapper<LinkShrinkFormHandler, Weblink>() {
                               @Override
                               public void mapAtoB(LinkShrinkFormHandler linkShrinkFormHandler, Weblink weblink, MappingContext context) {
                                   weblink.setFullUrl(linkShrinkFormHandler.getInboundFullUrl());
                               }
                           }
                )
                .register();
    }
}
