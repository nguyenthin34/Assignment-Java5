package com.poly.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDTO implements Serializable {
    private Long categoryId;
    @NotEmpty
    @Length(min = 4)
    private String name;
    @NotNull
    private Boolean status;
    private Boolean isEdit;
}
