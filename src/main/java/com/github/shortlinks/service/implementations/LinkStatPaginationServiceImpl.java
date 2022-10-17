package com.github.shortlinks.service.implementations;

import com.github.shortlinks.dao.abstracts.pagination.PaginationDao;
import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.service.abstracts.LinkStatPaginationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkStatPaginationServiceImpl implements LinkStatPaginationService<List<LinkStatDto>> {
    private final PaginationDao<LinkStatDto> paginationDao;

    public LinkStatPaginationServiceImpl(PaginationDao<LinkStatDto> paginationDao) {
        this.paginationDao = paginationDao;
    }

    @Override
    public List<LinkStatDto> getItems(int currentPage, int itemsOnPage) {
        return paginationDao.getItems(currentPage, itemsOnPage);
    }
}
