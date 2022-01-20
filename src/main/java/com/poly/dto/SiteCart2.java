package com.poly.dto;

import com.poly.domain.Color;
import com.poly.domain.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteCart2 implements Serializable {
    @NotNull
    @PositiveOrZero
    private Long productId;
    @NotBlank
    private String name;
    @NotBlank
    private String image;
    private Double price;
    @NotNull
    @Positive
    private Integer quantity;
    private Long color;
    private long size;
    private Double total;
}
