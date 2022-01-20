package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IrvouchersDTO {

    private Long irVoucherId;
//    @NotEmpty
//    private Long providerId;
//    @NotNull
//    @PositiveOrZero
//    private Double total;
//    @FutureOrPresent
//    @NotNull
//    private LocalDate createDate;
//    private String creator;
//    @NotNull
//    private Boolean status;


}
