package com.example.linkshrink.service;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.service.interfaces.LinkShrinkService;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Листенер очереди
 */
@EnableRabbit
@Component
public class RabbitMqListenerService {

    @Autowired
    private LinkShrinkService linkShrinkService;

    /**
     * Получение полного URL из очереди и передача в сервис
     * @param fullUrl полный URL
     * @return объект Weblink, содержащий сокращенный URL
     */
    @RabbitListener(queues = "q1")
    public Weblink processQueue(String fullUrl) {
        return linkShrinkService.add(fullUrl);
    }
}
