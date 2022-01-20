package com.poly.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.poly.domain.Role;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    @NotBlank
    @Length(min = 6)
    private String username;
    @NotBlank
    @Length(min = 6)
    private String fullname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Length(min = 6)
    private String password;
    @NotNull
    private Boolean status;
    @NotNull
    private Long role;
    private Boolean isEdit;
}
