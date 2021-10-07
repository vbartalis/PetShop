package io.github.vbartalis.petshop.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class PostPageCriteria {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "creationDate";

    public String getSortBy() {
        switch (sortBy) {
            case "id":
                return "id";
            case "title":
                return "title";
            case "updateDate":
                return "updateDate";
            default:
                return "creationDate";
        }
    }
}
