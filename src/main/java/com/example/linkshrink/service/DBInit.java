package com.example.linkshrink.service;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.repo.WebLinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DBInit {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WebLinkRepo webLinkRepo;

    @PostConstruct
    public void initDB () {
        Weblink weblink1 = new Weblink("http://ya.ru");
        webLinkRepo.save(weblink1);

        Weblink weblink2 = new Weblink("http://google.com");
        webLinkRepo.save(weblink2);

        Weblink weblink3 = new Weblink("http://spring.io");
        webLinkRepo.save(weblink3);
    }
}
