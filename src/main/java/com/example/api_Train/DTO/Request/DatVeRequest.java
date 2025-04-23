package com.example.api_Train.DTO.Request;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatVeRequest {
    @NotNull(message = "Thông tin người đặt vé không được để trống")
    private NguoiDatVeRequest nguoiDatVe;

    @NotEmpty(message = "Danh sách vé đặt không được để trống")
    private List<ChiTietDatVeRequest> chiTietDatVe;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChiTietDatVeRequest {
        @NotNull(message = "Thông tin hành khách không được để trống")
        private HanhKhachRequest hanhKhach;

        @NotNull(message = "Thông tin vé tàu không được để trống")
        private VeTauRequest veTau;
    }

}