package com.example.linkshrink.service.interfaces;

import com.example.linkshrink.entity.Weblink;

public interface AMQPListenerService {
    Weblink processQueue(Weblink weblink);
}
