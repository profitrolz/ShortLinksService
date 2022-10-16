package com.github.shortlinks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String shortLink;

    private String originalLink;

    private long requestsCount;

    public void increaseCountOfRequests() {
        requestsCount++;
    }


}
