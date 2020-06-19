package com.example.linkshrink.mapper;

import com.example.linkshrink.constants.HostName;
import com.example.linkshrink.dto.WeblinkRequestDto;
import com.example.linkshrink.dto.WeblinkResponseDto;
import com.example.linkshrink.entity.Weblink;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class WeblinkRequestMapper extends AbstractMapperRegister{

    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(WeblinkRequestDto.class, Weblink.class)
                .byDefault()
                .customize(new CustomMapper<WeblinkRequestDto, Weblink>() {
                               @Override
                               public void mapAtoB(WeblinkRequestDto weblinkRequestDto, Weblink weblink, MappingContext context) {
                                   String shortUrl = weblinkRequestDto.getShortUrl();
                                   weblink.setShortUrlSuffix(shortUrl.substring(shortUrl.length()-8));
                               }
                           }
                )
                .register();
    }
}
