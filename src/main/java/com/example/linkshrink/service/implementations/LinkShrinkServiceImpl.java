package com.example.linkshrink.service.implementations;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.exception.InvalidURLException;
import com.example.linkshrink.exception.URLNotFoundException;
import com.example.linkshrink.repo.WebLinkRepo;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import com.example.linkshrink.service.interfaces.Shrinker;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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

    private Weblink save(String fullURL) {
        Weblink result;

        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(fullURL)) {
            throw new InvalidURLException();
        }

        Weblink someLink = webLinkRepo.findWeblinkByFullURL(fullURL);
        if (someLink == null) {
            Weblink newWebLink = new Weblink(fullURL, shrinker.shrink(fullURL));
            webLinkRepo.save(newWebLink);
            result = newWebLink;
        } else {
            result = someLink;
        }

        return result;
    }

    @Override
    public Weblink add (String iboundFullLink) {
        return save(iboundFullLink);
    }

    @Override
    public Weblink add(Weblink weblink) {
        return save(weblink.getFullURL());
    }

    @Override
    public Weblink resolve(String shrinkedLink) {
        Weblink result;
        Weblink requestedWebLink = webLinkRepo.findWeblinkByShortURL(shrinkedLink);

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
