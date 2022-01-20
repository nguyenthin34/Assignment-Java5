package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    @NotBlank
    @Length(min = 6)
    private String username;
    @NotBlank
    @Length(min = 6)
    private String currentPassword;
    @NotBlank
    @Length(min = 6)
    private String newPassword;
    @NotBlank
    @Length(min = 6)
    private String confirmPassword;

}
