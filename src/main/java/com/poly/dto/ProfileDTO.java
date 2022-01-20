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
public class ProfileDTO {
    @NotBlank
    @Length(min = 6)
    private String username;
    @NotBlank
    @Length(min = 6)
    private String fullname;
    @NotBlank
    @Email
    private String email;
    private Boolean isEdit;
}
