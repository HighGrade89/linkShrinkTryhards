package com.example.linkshrink.service.interfaces;

import com.example.linkshrink.entity.Weblink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LinkShrinkService {
    List<Weblink> findAll();

    Weblink add(Weblink weblink);

    Weblink add(String fullUrl);

    Weblink resolve(String shortLinkSuffix);

    Weblink getById(long id);

}
