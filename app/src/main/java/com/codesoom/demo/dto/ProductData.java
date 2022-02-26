package com.codesoom.demo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String maker;

    @NotNull
    private Integer price;

    private String imageUrl;

}
