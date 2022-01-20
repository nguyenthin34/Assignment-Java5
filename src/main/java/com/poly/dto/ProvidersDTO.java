package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvidersDTO {

    private Long providerId;
    @NotBlank
    @Length(min = 6)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 6)
    private String address;
    @NotNull
    private Boolean status;
    private Boolean isEdit;
}
