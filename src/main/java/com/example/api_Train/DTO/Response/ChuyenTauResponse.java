package com.example.api_Train.DTO.Response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api_Train.models.*;

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

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ToaTauResponse {
        private Integer maToa;
        private String tenToa;
        private String loaiToa;
        private List<GheResponse> danhSachGhe;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GheResponse {
        private Integer maGhe;
        private String tenGhe;
        private String trangThai;
        private BigDecimal giaVe;
    }

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
        List<ToaTau> danhSachToa = chuyenTau.getTau().getDanhSachToaTau();
        List<BangGia> bangGias = chuyenTau.getDanhSachBangGia();

        return danhSachToa.stream()
                .map(toa -> {
                    LoaiCho loaiCho = toa.getLoaiCho();

                    BigDecimal giaVe = bangGias.stream()
                            .filter(bg -> bg.getLoaiCho().equals(loaiCho)
                                    && bg.getTau().equals(chuyenTau.getTau()))
                            .findFirst()
                            .map(BangGia::getGiaTien)
                            .orElse(BigDecimal.ZERO);

                    return ToaTauResponse.builder()
                            .maToa(toa.getMaToa())
                            .tenToa(toa.getTenToa())
                            .loaiToa(loaiCho.getTenLoaiCho())
                            .danhSachGhe(mapGheToResponse(toa, giaVe, chuyenTau))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public static List<GheResponse> mapGheToResponse(ToaTau toa, BigDecimal giaVe, ChuyenTau chuyenTau) {
        return toa.getDanhSachGhe().stream()
                .map((Ghe ghe) -> {
                    // Find the seat status for this specific train trip
                    String trangThai = chuyenTau.getGheChuyenTaus().stream()
                            .filter(gheChuyenTau -> gheChuyenTau.getGhe().getMaGhe().equals(ghe.getMaGhe()))
                            .findFirst()
                            .map(GheChuyenTau::getTrangThai)
                            .orElse("Null"); // Default if not found

                    return GheResponse.builder()
                            .maGhe(ghe.getMaGhe())
                            .tenGhe(ghe.getTenGhe())
                            .trangThai(trangThai)
                            .giaVe(giaVe)
                            .build();
                })
                .collect(Collectors.toList());
    }
}