package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdvourchersDTO {
    @NotBlank
    @Length(min = 10)
    @Length(max = 15)
    private String phone;
    @NotBlank
    private String address;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;

}
