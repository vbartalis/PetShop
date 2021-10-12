package io.github.vbartalis.petshop.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * This class is used as a Data Transfer Object between the client and the server.
 */
@Getter
@Setter
public class UserPageCriteria {
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortDirection = "ASC";
    private String sortBy = "username";

    public Sort.Direction getSortDirection() {
        if (Objects.equals(sortDirection, "ASC")) return Sort.Direction.ASC;
        else return Sort.Direction.DESC;
    }

    public String getSortBy() {
        switch (sortBy) {
            case "id":
                return "id";
            case "expiration":
                return "expiration";
            default:
                return "username";
        }
    }
}
