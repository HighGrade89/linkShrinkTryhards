package com.example.linkshrink.service;

import com.example.linkshrink.entity.Weblink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LinkShrinkService {
    List<Weblink> findAll();

    //Добавляет в базу ссылку, возвращая сокращенное представление
    Weblink shrinkLink(Weblink weblink);

    Weblink resolveLink(Weblink weblink);

}
