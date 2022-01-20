package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdvourcherdetailsDTO {
    private Long idVourcherDetailId;
    @NotNull
    @PositiveOrZero
    private Double price;
    @NotNull
    @PositiveOrZero
    private Integer quantity;
    private Long productId;
    private Long idVourcherId;
}
