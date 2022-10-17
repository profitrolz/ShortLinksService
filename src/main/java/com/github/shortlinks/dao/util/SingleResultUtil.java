package com.github.shortlinks.dao.util;

import javax.persistence.TypedQuery;
import java.util.Optional;

public final class SingleResultUtil {

    private SingleResultUtil() {
    }

    public static <T> Optional<T> getSingleResultOrNull(TypedQuery<T> query) {
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
