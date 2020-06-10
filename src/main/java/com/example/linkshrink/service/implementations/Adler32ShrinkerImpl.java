package com.example.linkshrink.service.implementations;

import com.example.linkshrink.service.interfaces.Shrinker;
import org.springframework.stereotype.Component;

import java.util.zip.Adler32;
import java.util.zip.Checksum;

@Component
public class Adler32ShrinkerImpl implements Shrinker {

    @Override
    public String shrink(String inboud) {
        Checksum checksum = new Adler32();
        checksum.update(inboud.getBytes());
        return Long.toHexString(checksum.getValue());
    }
}
