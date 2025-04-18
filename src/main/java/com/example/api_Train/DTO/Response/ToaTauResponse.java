package com.example.api_Train.DTO.Response;

import lombok.*;
import com.example.api_Train.models.ToaTau;
import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.BangGia;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToaTauResponse {
    private Integer maToa;
    private String tenToa;
    private Integer soGhe;
    private String tenLoaiCho;
    private List<GheResponse> danhSachGhe;

    public static ToaTauResponse mapToaTauResponse(
            ToaTau toaTau,
            ChuyenTau chuyenTau,
            List<BangGia> danhSachBangGia // Danh sách giá của chuyến tàu
    ) {
        return ToaTauResponse.builder()
                .maToa(toaTau.getMaToa())
                .tenToa(toaTau.getTenToa())
                .soGhe(toaTau.getSoLuongGhe())
                .tenLoaiCho(toaTau.getLoaiCho().getTenLoaiCho())
                .danhSachGhe(mapGheToResponse(toaTau, chuyenTau, danhSachBangGia))
                .build();
    }

    private static List<GheResponse> mapGheToResponse(
            ToaTau toaTau,
            ChuyenTau chuyenTau,
            List<BangGia> danhSachBangGia) {
        return toaTau.getDanhSachGhe().stream()
                .map(ghe -> GheResponse.mapGheResponse(ghe, chuyenTau, danhSachBangGia))
                .collect(Collectors.toList());
    }
}