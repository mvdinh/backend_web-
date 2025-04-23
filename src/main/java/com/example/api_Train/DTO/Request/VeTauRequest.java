package com.example.api_Train.DTO.Request;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeTauRequest {

    @NotNull(message = "Mã chuyến tàu không được để trống")
    private Integer maChuyenTau;

    @NotNull(message = "Mã ghế không được để trống")
    private Integer maGhe;

}
