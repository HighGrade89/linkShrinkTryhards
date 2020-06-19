package com.example.linkshrink.service.implementations;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.exception.URLExpiredException;
import com.example.linkshrink.exception.URLInvalidException;
import com.example.linkshrink.exception.URLNotFoundException;
import com.example.linkshrink.repo.WeblinkRepo;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import com.example.linkshrink.service.interfaces.Shrinker;
import com.example.linkshrink.service.interfaces.WeblinkValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, реализующий целевой процесс приложения
 */
@Service("linkShrinkService")
@Transactional
public class LinkShrinkServiceImpl implements LinkShrinkService {

    private static int EXPIRATION_TIME_IN_MINUTES = 10;

    @Autowired
    private WeblinkRepo weblinkRepo;

    @Autowired
    private WeblinkValidator weblinkValidator;

    @Autowired
    private Shrinker shrinker;

    /**
     * Добавление полностью заполненной сущности Weblink в базу данных
     * @param fullUrl полный URL, для сокращения и добавления в базу
     * @return сущность Weblink
     */
    @Override
    public Weblink add(String fullUrl) {

        if (!weblinkValidator.isValid(fullUrl)) {
            throw new URLInvalidException();
        }

        Weblink currentWebLink = weblinkRepo.findWeblinkByFullUrl(fullUrl);
        if (currentWebLink != null) {
            weblinkRepo.delete(currentWebLink);
        }

        Weblink newWebLink = new Weblink(fullUrl, shrinker.shrink(fullUrl));
        weblinkRepo.save(newWebLink);
        return newWebLink;
    }

    /**
     * Получение объекта с полной ссылкой по суффиксу короткой ссылки
     * @param shortUrlSuffix суффик сокращенной ссылки
     * @return сущность Weblink
     */
    @Override
    public Weblink resolve(String shortUrlSuffix) {
        Weblink result;
        Weblink requestedWebLink = weblinkRepo.findWeblinkByShortUrlSuffix(shortUrlSuffix);

        if (requestedWebLink == null) {
            throw new URLNotFoundException(shortUrlSuffix);
        }

        Instant creationTimeInstant = requestedWebLink.getAddedTime().toInstant();
        LocalTime expirationTime = LocalTime.ofInstant(creationTimeInstant, ZoneOffset.UTC)
                .plusMinutes(EXPIRATION_TIME_IN_MINUTES);

        if (Duration.between(LocalTime.now(ZoneOffset.UTC), expirationTime).toMinutes() < 0) {
            throw new URLExpiredException();
        }

        result = requestedWebLink;
        return result;
    }

    /**
     * Получение списка всех сохраненных ссылок
     * @return список сущностей Weblink
     */
    @Transactional(readOnly = true)
    public List<Weblink> findAll() {
        return (ArrayList<Weblink>) weblinkRepo.findAll();
    }


}
