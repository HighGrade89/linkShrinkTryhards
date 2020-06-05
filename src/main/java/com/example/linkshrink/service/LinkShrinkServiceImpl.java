package com.example.linkshrink.service;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.repo.WebLinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("linkShrinkService")
@Transactional
public class LinkShrinkServiceImpl implements LinkShrinkService {

    @Autowired
    private WebLinkRepo webLinkRepo;

    @Transactional(readOnly = true)
    public List<Weblink> findAll() {
        return (ArrayList<Weblink>) webLinkRepo.findAll();
    }

    @Override
    public Weblink shrinkLink(Weblink weblink) {
        Weblink someLink = webLinkRepo.findWeblinkByFullLink(weblink.getFullLink());
        Optional<Weblink> opWeblink = Optional.ofNullable(someLink);
        opWeblink.ifPresentOrElse(
                (wlink) -> {System.out.println("yes " + wlink.getFullLink());},
                () -> {System.out.println("no");}
        );
        return weblink;
    }

    @Override
    public Weblink resolveLink(Weblink weblink) {
        return null;
    }

}
