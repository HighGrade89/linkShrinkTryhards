package com.example.linkshrink.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.web.bind.annotation.GetMapping;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

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

    @Column(length = MAX_URL_LENGTH)
    private String fullUrl;

    @Column
    @GeneratedValue(strategy = AUTO)
    private String shortUrlSuffix;

    @Column
    @CreationTimestamp
    private Date addedTime;

    public Weblink(String fullUrl, String shortUrlSuffix) {
        this.fullUrl = fullUrl;
        this.shortUrlSuffix = shortUrlSuffix;
    }

}
