package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCartItem {
    private Integer id;
    private Long commodityId;
    private String name;
    private String image;
    private Double unitPrice;
    private Integer quantity;
    private Long colorId;
    private Long sizeId;
}
