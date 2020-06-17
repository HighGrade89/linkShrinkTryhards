package com.example.linkshrink.service.implementations;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.exception.InvalidURLException;
import com.example.linkshrink.exception.URLNotFoundException;
import com.example.linkshrink.repo.WebLinkRepo;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import com.example.linkshrink.service.interfaces.Shrinker;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("linkShrinkService")
@Transactional
public class LinkShrinkServiceImpl implements LinkShrinkService {

    @Autowired
    private WebLinkRepo webLinkRepo;

    @Autowired
    private Shrinker shrinker;

    @Transactional(readOnly = true)
    public List<Weblink> findAll() {
        return (ArrayList<Weblink>) webLinkRepo.findAll();
    }

    @Override
    public Weblink add(Weblink weblink) {

        Weblink result;
        String fullUrl = weblink.getFullUrl();

        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(fullUrl)) {
            System.out.println(fullUrl);
            System.out.println("Invalid url");
            throw new InvalidURLException();
        }

        Weblink someLink = webLinkRepo.findWeblinkByFullUrl(fullUrl);
        if (someLink == null) {
            Weblink newWebLink = new Weblink(fullUrl, shrinker.shrink(fullUrl));
            webLinkRepo.save(newWebLink);
            result = newWebLink;
        } else {
            result = someLink;
        }

        return result;
    }

    @Override
    public Weblink resolve(String shortUrlSuffix) {
        Weblink result;
        Weblink requestedWebLink = webLinkRepo.findWeblinkByShortUrlSuffix(shortUrlSuffix);

        if (requestedWebLink == null) {
            throw new URLNotFoundException();
        } else {
            result = requestedWebLink;
        }

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
