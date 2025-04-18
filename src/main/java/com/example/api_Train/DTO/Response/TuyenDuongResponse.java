package com.example.api_Train.DTO.Response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.example.api_Train.models.TuyenDuong;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuyenDuongResponse {
    private Integer maTuyenDuong;
    private String gaDi;
    private String gaDen;
    private Integer KhoangCach;
    private String thoiGianDuKien;

    public static TuyenDuongResponse fromTuyenDuongResponse(TuyenDuong tuyenDuong) {
        return TuyenDuongResponse.builder()
                .maTuyenDuong(tuyenDuong.getMaTuyen())
                .gaDi(tuyenDuong.getGaDi().getTenGa())
                .gaDen(tuyenDuong.getGaDen().getTenGa())
                .KhoangCach(tuyenDuong.getKhoangCach())
                .thoiGianDuKien(tuyenDuong.getThoiGianDuKien()).build();
    }
}
