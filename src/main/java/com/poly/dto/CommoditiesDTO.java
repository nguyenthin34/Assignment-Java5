package com.poly.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommoditiesDTO {
    private Long commodityId;
    private String name;
    private MultipartFile imageFile;
    private String image;
    private Long categoryId;
    private Boolean status;
    private Boolean isEdit;

}
