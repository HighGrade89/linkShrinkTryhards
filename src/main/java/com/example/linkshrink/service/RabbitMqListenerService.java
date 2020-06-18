package com.example.linkshrink.service;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.service.interfaces.LinkShrinkService;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@EnableRabbit
@Component
public class RabbitMqListenerService {

    @Autowired
    private LinkShrinkService linkShrinkService;

    @Autowired
    private AmqpAdmin rabbitAdmin;

    @RabbitListener(queues = "q1")
    public Weblink processQueue(Weblink weblink) {
        Properties properties = rabbitAdmin.getQueueProperties("q1");
        System.out.println(Integer.parseInt(properties.get("QUEUE_MESSAGE_COUNT").toString()));
        return linkShrinkService.add(weblink);
    }

}
