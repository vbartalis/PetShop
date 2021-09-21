package io.github.vbartalis.petshop.dto.request;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class PostPage {
    private final int pageNumber = 0;
    private final int pageSize = 10;
    private final Sort.Direction sortDirection = Sort.Direction.ASC;
    private final PostPageSort sortBy = PostPageSort.creationDate;

    public enum PostPageSort {
        id,
        title,
        creationDate,
        updateDate
    }

    public String getSortBy() {
        switch (sortBy) {
            case id: return "id";
            case title: return "title";
            case updateDate: return "updateDate";
            default: return "creationDate";
        }
    }
}
