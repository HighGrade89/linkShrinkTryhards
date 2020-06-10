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
    private String fullLink;

    @Getter
    @Setter
    @Column
    @GeneratedValue(strategy = AUTO)
    private String shrinkedLink;

    public Weblink() {
    }

    public Weblink(String fullLink) {
        this.fullLink = fullLink;
    }

    public Weblink(String fullLink, String shrinkedLink) {
        this.fullLink = fullLink;
        this.shrinkedLink = shrinkedLink;
    }
}
