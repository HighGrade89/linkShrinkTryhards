package com.example.linkshrink.service.implementations;

import com.example.linkshrink.service.interfaces.Shrinker;
import org.springframework.stereotype.Component;

import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * Реализация сокращения ссылки алгоритмом Adler32
 */
@Component
public class Adler32ShrinkerImpl implements Shrinker {

    /**
     * Метрод сокращения ссылки
     * @param inboud полная ссылка
     * @return суффик (хэш) короткой ссылки
     */
    @Override
    public String shrink(String inboud) {
        Checksum checksum = new Adler32();
        checksum.update(inboud.getBytes());
        return Long.toHexString(checksum.getValue());
    }
}
