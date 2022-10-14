package com.github.shortlinks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String shortLink;

    private String originalLink;


}
