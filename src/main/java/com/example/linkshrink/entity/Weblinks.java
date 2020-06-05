package com.example.linkshrink.entity;

import java.io.Serializable;
import java.util.List;

public class Weblinks implements Serializable {
    private List<Weblink> weblinks;

    public Weblinks() {
    }

    public Weblinks(List<Weblink> weblinks) {
        this.weblinks = weblinks;
    }

    public List<Weblink> getWeblinks() {
        return weblinks;
    }

    public void setWeblinks(List<Weblink> weblinks) {
        this.weblinks = weblinks;
    }
}
