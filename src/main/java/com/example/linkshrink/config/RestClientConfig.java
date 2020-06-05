package com.example.linkshrink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.http.HttpClient;

@Configuration
public class RestClientConfig {
    @Autowired
    ApplicationContext ctx;

}
