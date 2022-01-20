package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusesDTO {
    private Long statusId;
    @NotBlank
    private String name;
    @NotNull
    private Boolean status;
    private Boolean isEdit;
}
