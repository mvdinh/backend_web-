package com.example.api_Train.DTO.Response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.ToaTau;
import com.example.api_Train.models.BangGia;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChuyenTauResponse {
    private Integer maChuyenTau;
    private String tenTau;
    private LocalDateTime ngayGioKhoiHanh;
    private TuyenDuongResponse tuyenDuong;
    private List<ToaTauResponse> danhSachToaTau;

    public static ChuyenTauResponse mapChuyenTauResponse(ChuyenTau chuyenTau) {
        return ChuyenTauResponse.builder()
                .maChuyenTau(chuyenTau.getMaChuyenTau())
                .tenTau(chuyenTau.getTau().getTenTau())
                .ngayGioKhoiHanh(chuyenTau.getNgayGioKhoiHanh())
                .tuyenDuong(TuyenDuongResponse.fromTuyenDuongResponse(chuyenTau.getTuyenDuong()))
                .danhSachToaTau(mapToaTauToResponse(chuyenTau))
                .build();
    }

    private static List<ToaTauResponse> mapToaTauToResponse(ChuyenTau chuyenTau) {
        return chuyenTau.getTau().getDanhSachToaTau().stream()
                .map(toa -> ToaTauResponse.mapToaTauResponse(
                        toa,
                        chuyenTau,
                        chuyenTau.getDanhSachBangGia() // Truyền danh sách giá từ chuyến tàu
                ))
                .collect(Collectors.toList());
    }
}