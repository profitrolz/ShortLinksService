package com.github.shortlinks.dao.abstracts.pagination;

import java.util.List;

public interface PaginationDao<T> {

    List<T> getItems(int currentPage, int itemsOnPage);

    long getItemsCount();

}
