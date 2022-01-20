package com.poly.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizesDTO {
    private Long sizeId;
    @NotEmpty
    private String name;
    private Boolean isEdit;
    @NotNull
    private Boolean status;
}
