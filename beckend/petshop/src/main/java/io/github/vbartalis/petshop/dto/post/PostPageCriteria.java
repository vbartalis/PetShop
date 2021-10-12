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
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private String sortDirection = "ASC";
    private String sortBy = "creationDate";

    public Sort.Direction getSortDirection() {
        if (sortDirection.equals("ASC")) return Sort.Direction.ASC;
        else return Sort.Direction.DESC;
    }

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
