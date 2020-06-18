package com.example.linkshrink;

import com.example.linkshrink.entity.Weblink;
import com.example.linkshrink.repo.WebLinkRepo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ApplicationTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebLinkRepo webLinkRepo;

    @Test
    public void prepareData() {
    }

}
