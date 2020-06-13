package com.example.linkshrink.entity;

import lombok.*;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;

@Data
@Entity
@ToString
@EqualsAndHashCode
public class Weblink implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column
    private String fullURL;

    @Getter
    @Setter
    @Column
    @GeneratedValue(strategy = AUTO)
    private String shortURL;

    public Weblink() {
    }

    public Weblink(String fullURL) {
        this.fullURL = fullURL;
    }

    public Weblink(String fullURL, String shortURL) {
        this.fullURL = fullURL;
        this.shortURL = shortURL;
    }
}
