package com.poly.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrvourcherdetailsDTO {
	
//    private Long irVourcherDetailId;
//    @NotNull
//    private Long irvoucherId;
    @NotNull
    private Long commodityId;
    @NotNull
    private Long sizeId;
    @NotNull
    private Long colorId;
    @NotNull
    private Double unitPrice;
    @NotNull
    private Integer quantity;
}
