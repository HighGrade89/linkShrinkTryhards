package com.example.linkshrink.service.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface Shrinker {
    public String shrink(String inboud);
}
