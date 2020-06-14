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
@NoArgsConstructor
public class Weblink implements Serializable {

    @Id
    @Getter
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column
    private String fullUrl;

    @Getter
    @Setter
    @Column
    @GeneratedValue(strategy = AUTO)
    private String shortUrlSuffux;

    public Weblink(String fullURL) {
        this.fullUrl = fullURL;
    }

    public Weblink(String fullUrl, String shortUrlSuffux) {
        this.fullUrl = fullUrl;
        this.shortUrlSuffux = shortUrlSuffux;
    }
}
