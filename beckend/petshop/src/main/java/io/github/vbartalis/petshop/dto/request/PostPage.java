package io.github.vbartalis.petshop.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter @Setter
public class PostPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "creationDate";

    public String getSortBy() {
        switch (sortBy) {
            case "id": return "id";
            case "title": return "title";
            case "updateDate": return "updateDate";
            default: return "creationDate";
        }
    }
}
