package io.github.vbartalis.petshop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter @Setter
public class UserPage {
    private final int pageNumber = 0;
    private final int pageSize = 10;
    private final Sort.Direction sortDirection = Sort.Direction.ASC;
    private final UserPageSort sortBy = UserPageSort.username;

    public enum UserPageSort {
        id,
        username,
        expiration
    }

    public String getSortBy() {
        switch (sortBy) {
            case id: return "id";
            case username: return "username";
            default: return "expiration";
        }
    }
}
