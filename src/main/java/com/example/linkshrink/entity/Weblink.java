package com.example.linkshrink.entity;

import lombok.Data;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.io.Serializable;

@Data
@Entity
public class Weblink implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String fullLink;

    @Column
    @GeneratedValue(strategy = AUTO)
    private int shrinkedLink;

    Weblink() {}

    public Weblink(String linkContent) {
        this.fullLink = linkContent;
    }
}
