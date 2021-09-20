package io.github.vbartalis.petshop.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter @Setter
public class Pagination {
    int page = 1;
    int size = 25;
    org.springframework.data.domain.Sort.Direction sort = Sort.Direction.ASC;
}


