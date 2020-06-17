package com.example.linkshrink.entity;

import lombok.*;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.web.bind.annotation.GetMapping;

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

    private static final int MAX_URL_LENGTH = 2083;

    @Id
    @Getter
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(length = MAX_URL_LENGTH)
    private String fullUrl;

    @Getter
    @Setter
    @Column
    @GeneratedValue(strategy = AUTO)
    private String shortUrlSuffix;

    public Weblink(String fullURL) {
        this.fullUrl = fullURL;
    }

    public Weblink(String fullUrl, String shortUrlSuffix) {
        this.fullUrl = fullUrl;
        this.shortUrlSuffix = shortUrlSuffix;
    }
}
