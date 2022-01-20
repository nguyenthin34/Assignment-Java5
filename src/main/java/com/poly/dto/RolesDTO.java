package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesDTO {
    private Long roleId;
    @NotBlank
    private String name;
    @NotNull
    private Boolean status;
    private Boolean isEdit;
}
