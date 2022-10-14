package com.github.shortlinks.dao.implementations;

import com.github.shortlinks.dao.abstracts.AbstractGenericDao;
import com.github.shortlinks.dao.abstracts.ShortLinkDao;
import com.github.shortlinks.entity.ShortLink;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ShortLinkDaoImpl extends AbstractGenericDao<ShortLink, Long> implements ShortLinkDao {

    @Override
    public Optional<ShortLink> getByOriginalLink(String originalLink) {
        TypedQuery<ShortLink> query = entityManager.createQuery("SELECT a from ShortLink as a where a.originalLink=:originalLink", ShortLink.class);
        try {
            return Optional.ofNullable(query
                    .setParameter("originalLink", originalLink)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String getOriginalLinkByShort(String shortLink) {
        return entityManager.createQuery("select a from ShortLink as a where a.shortLink=:shortLink", ShortLink.class)
                .setParameter("shortLink", shortLink)
                .getSingleResult().getOriginalLink();
    }
}
