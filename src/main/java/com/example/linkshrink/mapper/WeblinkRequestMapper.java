package com.example.linkshrink.mapper;

import com.example.linkshrink.dto.WeblinkResponseDto;
import com.example.linkshrink.entity.Weblink;
import ma.glasnost.orika.MapperFactory;

public class WeblinkRequestMapper extends AbstractMapperRegister {
    @Override
    public void register(MapperFactory mapperFactory) {
        mapperFactory.classMap(Weblink.class, WeblinkResponseDto.class)
                .byDefault()
                .register();
    }
}
