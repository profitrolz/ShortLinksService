package com.github.shortlinks.dao.abstracts;

import com.github.shortlinks.dto.LinkStatDto;
import com.github.shortlinks.entity.ShortLinkStatEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortLinkStatsDao extends GenericDao<ShortLinkStatEntity, String> {

    void increaseStat(String link);

    LinkStatDto getLinkStat(String link);

}
