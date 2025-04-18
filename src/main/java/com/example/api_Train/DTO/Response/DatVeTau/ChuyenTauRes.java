package com.example.api_Train.DTO.Response.DatVeTau;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.ToaTau;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.BangGia;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChuyenTauRes {
    private Integer maChuyenTau;
    private String tenTau;
    private LocalDateTime ngayGioKhoiHanh;
    private TuyenDuongResponse tuyenDuong;

    public static ChuyenTauRes mapChuyenTauResponse(ChuyenTau chuyenTau) {
        return ChuyenTauRes.builder()
                .maChuyenTau(chuyenTau.getMaChuyenTau())
                .tenTau(chuyenTau.getTau().getTenTau())
                .ngayGioKhoiHanh(chuyenTau.getNgayGioKhoiHanh())
                .tuyenDuong(TuyenDuongResponse.fromTuyenDuongResponse(chuyenTau.getTuyenDuong()))
                .build();
    }

}