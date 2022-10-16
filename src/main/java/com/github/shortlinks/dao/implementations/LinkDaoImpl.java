package com.github.shortlinks.dao.implementations;

import com.github.shortlinks.dao.abstracts.AbstractGenericDao;
import com.github.shortlinks.dao.abstracts.LinkDao;
import com.github.shortlinks.dao.util.SingleResultUtil;
import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.entity.Link;
import com.github.shortlinks.exceptions.LinkNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class LinkDaoImpl extends AbstractGenericDao<Link, Long> implements LinkDao {

    @Override
    public Optional<Link> getLinkByOriginalLink(String originalLink) {
        TypedQuery<Link> query = entityManager.createQuery("SELECT a from Link as a where a.originalLink=:originalLink", Link.class);
        try {
            return Optional.ofNullable(query
                    .setParameter("originalLink", originalLink)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Link> getLinkByShortLink(String shortLink) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select a from Link as a where a.shortLink=:shortLink", Link.class)
                .setParameter("shortLink", shortLink));
    }

    @Override
    public void increaseStat(Link link) {
        link.increaseCountOfRequests();
        entityManager.merge(link);
    }

    @Override
    public LinkStatDto getLinkStat(String link) {
        LinkStatDto linkStatDto = SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("select new com.github.shortlinks.dto.LinkStatDto (l.shortLink, l.originalLink, 0L, l.requestsCount) from Link as l " +
                        "where l.shortLink = :paramLink", LinkStatDto.class)
                .setParameter("paramLink", link)).orElseThrow(LinkNotFoundException::new);

        long rank = SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("Select count(ls) from Link ls where ls.requestsCount > :paramNumberOfRequest", Long.class)
                .setParameter("paramNumberOfRequest", linkStatDto.getCount())).orElse(0L);
        linkStatDto.setRank(++rank);

        return linkStatDto;
    }

}
