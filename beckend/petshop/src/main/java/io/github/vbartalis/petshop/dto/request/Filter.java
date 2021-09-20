package io.github.vbartalis.petshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Filter {
    private List<String> filters;
}
