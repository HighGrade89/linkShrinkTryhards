package com.example.linkshrink.mapper.config;

import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.CollectionUtils;
import com.example.linkshrink.mapper.MapperRegister;

import java.util.List;

/**
 * Конфигурация Orika
 */
@Configuration
@RequiredArgsConstructor
public class OrikaAutoConfiguration {

    private final List<MapperRegister> mapperRegisters;

    @Bean("mapperFactory")
    @ConditionalOnMissingBean(MapperFactory.class)
    public MapperFactory mapperFactory() {
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        if (!CollectionUtils.isEmpty(mapperRegisters)) {
            mapperRegisters.forEach(mapper -> mapper.register(mapperFactory));
        }
        return mapperFactory;
    }

    @Bean
    @DependsOn("mapperFactory")
    @ConditionalOnMissingBean(MapperFacade.class)
    public MapperFacade mapperFacade(final MapperFactory mapperFactory) {
        return mapperFactory.getMapperFacade();
    }
}
