package com.example.linkshrink.service.implementations;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.service.interfaces.AMQPListenerService;
import com.example.linkshrink.service.interfaces.LinkShrinkService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMqListenerService implements AMQPListenerService {

    @Autowired
    private LinkShrinkService linkShrinkService;

    @RabbitListener(queues = "queue1")
    public Weblink processQueue(Weblink weblink) {
        return linkShrinkService.add(weblink);
    }

}
