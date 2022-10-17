package com.github.shortlinks.service.abstracts;


public interface LinkStatPaginationService <T> {
    T getItems(int currentPage, int itemsOnPage);
}
