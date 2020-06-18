package com.example.linkshrink.service.implementations;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.exception.URLExpiredException;
import com.example.linkshrink.exception.URLInvalidException;
import com.example.linkshrink.exception.URLNotFoundException;
import com.example.linkshrink.repo.WebLinkRepo;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import com.example.linkshrink.service.interfaces.Shrinker;
import com.example.linkshrink.service.interfaces.WeblinkValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service("linkShrinkService")
@Transactional
public class LinkShrinkServiceImpl implements LinkShrinkService {

    private static int EXPIRATION_TIME_IN_MINUTES = 10;

    @Autowired
    private WebLinkRepo webLinkRepo;

    @Autowired
    private WeblinkValidator weblinkValidator;

    @Autowired
    private Shrinker shrinker;

    @Transactional(readOnly = true)
    public List<Weblink> findAll() {
        return (ArrayList<Weblink>) webLinkRepo.findAll();
    }

    @Override
    public Weblink add(Weblink weblink) {

        String fullUrl = weblink.getFullUrl();
        if (!weblinkValidator.isValid(fullUrl)) {
            throw new URLInvalidException();
        }

        Weblink currentWebLink = webLinkRepo.findWeblinkByFullUrl(fullUrl);
        if (currentWebLink != null) {
            webLinkRepo.delete(currentWebLink);
        }

        Weblink newWebLink = new Weblink(fullUrl, shrinker.shrink(fullUrl));
        webLinkRepo.save(newWebLink);
        return newWebLink;
    }


    @Override
    public Weblink resolve(String shortUrlSuffix) {
        Weblink result;
        Weblink requestedWebLink = webLinkRepo.findWeblinkByShortUrlSuffix(shortUrlSuffix);

        if (requestedWebLink == null) {
            throw new URLNotFoundException();
        }

        Instant creationTimeInstant = requestedWebLink.getAddedTime().toInstant();
        LocalTime expirationTime = LocalTime.ofInstant(creationTimeInstant, ZoneOffset.UTC)
                .plusMinutes(EXPIRATION_TIME_IN_MINUTES);

        if (Duration.between(LocalTime.now(ZoneOffset.UTC), expirationTime).toMinutes() > 0) {
            throw new URLExpiredException();
        }

        result = requestedWebLink;
        return result;
    }

    @Override
    public Weblink getById(long id) {
        Weblink weblink = webLinkRepo.getWeblinkById(id);
        if (weblink == null) {
            throw new URLNotFoundException();
        }

        return weblink;
    }

}
