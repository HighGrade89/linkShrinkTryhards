package com.example.linkshrink.service.auxiliary;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.repo.WebLinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

@Component
public class DBInit {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WebLinkRepo webLinkRepo;

    @PostConstruct
    public void initDB () {
        String url;
        Checksum checksum = new Adler32();

        url = "http://ya.ru";
        checksum.update(url.getBytes());
        Weblink weblink1 = new Weblink(url, Long.toHexString(checksum.getValue()));
        webLinkRepo.save(weblink1);

        url = "http://google.com";
        checksum.update(url.getBytes());
        Weblink weblink2 = new Weblink(url, Long.toHexString(checksum.getValue()));
        webLinkRepo.save(weblink2);

        url = "http://spring.io";
        checksum.update(url.getBytes());
        Weblink weblink3 = new Weblink(url, Long.toHexString(checksum.getValue()));
        webLinkRepo.save(weblink3);

        url = "https://stackoverflow.com/";
        checksum.update(url.getBytes());
        Weblink weblink4 = new Weblink(url, Long.toHexString(checksum.getValue()));
        webLinkRepo.save(weblink4);

    }
}
