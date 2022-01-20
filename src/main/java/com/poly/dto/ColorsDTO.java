package com.poly.dto;

import javax.validation.constraints.NotEmpty;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorsDTO implements Serializable {
    private Long colorId;
    @NotEmpty
    private String name;
    private Boolean isEdit;
    @NotNull
    private Boolean status;
}
