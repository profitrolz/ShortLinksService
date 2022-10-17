package com.github.shortlinks.dao.abstracts.pagination;

import com.github.shortlinks.dao.util.SingleResultUtil;
import com.github.shortlinks.dto.LinkStatDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LinkPaginationDao implements PaginationDao<LinkStatDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LinkStatDto> getItems(int currentPage, int itemsOnPage) {
        return entityManager.createQuery("select new com.github.shortlinks.dto.LinkStatDto (l.shortLink, l.originalLink, " +
                        " (select count (l2.shortLink) + 1 from Link as l2 where l2.requestsCount > l.requestsCount), l.requestsCount) from Link as l " +
                        " order by l.requestsCount desc ", LinkStatDto.class)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public long getItemsCount() {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery("Select (count id) from Link", Long.class)).orElse(0L);
    }
}
