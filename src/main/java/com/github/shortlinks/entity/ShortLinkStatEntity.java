package com.github.shortlinks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "link_stat")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ShortLinkStatEntity {

    @Id
    private String shortLink;

    private long numberOfRequest;

    public void increaseNumberOfRequests() {
        numberOfRequest++;
    }

}
