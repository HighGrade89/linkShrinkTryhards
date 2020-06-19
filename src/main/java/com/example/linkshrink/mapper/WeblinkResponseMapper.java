package com.example.linkshrink.mapper;

import com.example.linkshrink.constants.HostName;
import com.example.linkshrink.dto.WeblinkResponseDto;
import com.example.linkshrink.entity.Weblink;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Маппер сущности в DTO короткой ссылки
 */
@Component
public class WeblinkResponseMapper extends AbstractMapperRegister{
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(Weblink.class, WeblinkResponseDto.class)
                .byDefault()
                .customize(new CustomMapper<Weblink, WeblinkResponseDto>() {
                               @Override
                               public void mapAtoB(Weblink weblink, WeblinkResponseDto weblinkResponseDto, MappingContext context) {
                                   weblinkResponseDto.setShortUrl(HostName.hostUrl + weblink.getShortUrlSuffix());
                               }
                           }
                )
                .register();
    }
}
