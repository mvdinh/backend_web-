package com.example.api_Train.DTO.Response;

import java.math.BigDecimal;
import lombok.*;
import com.example.api_Train.models.BangGia;
import com.example.api_Train.models.DoiTuong;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoiTuongResponse {
    private Integer maLoaiKhach;
    private String tenLoaiKhach;
    private BigDecimal phanTramGiamGia;

    public static DoiTuongResponse fromDoiTuongponse(DoiTuong doiTuong) {
        return DoiTuongResponse.builder()
                .maLoaiKhach(doiTuong.getMaLoaiKhach())
                .phanTramGiamGia(doiTuong.getPhanTramGiamGia())
                .tenLoaiKhach(doiTuong.getTenLoaiKhach())
                .build();
    }
}
