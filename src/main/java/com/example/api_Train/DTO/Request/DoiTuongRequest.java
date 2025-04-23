package com.example.api_Train.DTO.Request;

import java.math.BigDecimal;
import lombok.*;
import jakarta.validation.constraints.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoiTuongRequest {
    private String tenLoaiKhach;
    private BigDecimal phanTramGiamGia;
}
