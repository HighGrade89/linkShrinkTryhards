package com.example.linkshrink.service.interfaces;

import com.example.linkshrink.entity.Weblink;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LinkShrinkService {
    List<Weblink> findAll();

    //Добавляет в базу ссылку, возвращая сокращенное представление
    Weblink add(Weblink weblink);

    Weblink resolve(String shrinkedLink);

    Weblink getById(long id);

}
