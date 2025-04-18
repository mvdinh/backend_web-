package com.example.api_Train.DTO.Response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.Ghe;
import com.example.api_Train.enums.TrangThaiGhe;
import com.example.api_Train.models.BangGia;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GheResponse {
    private Integer maGhe;
    private String tenGhe;
    private BigDecimal giaTien;
    private String trangThai;

    // Sử dụng Builder Pattern hoặc constructor tùy bạn
    public static GheResponse mapGheResponse(
            Ghe ghe,
            ChuyenTau chuyenTau,
            List<BangGia> danhSachBangGia) {
        // Lấy loại chỗ từ toa của ghế
        Integer maLoaiCho = ghe.getToaTau().getLoaiCho().getMaLoaiCho();

        // Tìm giá tương ứng với chuyến tàu và loại chỗ
        BigDecimal giaTien = danhSachBangGia.stream()
                .filter(bg -> bg.getChuyenTau().getMaChuyenTau().equals(chuyenTau.getMaChuyenTau()) &&
                        bg.getLoaiCho().getMaLoaiCho().equals(maLoaiCho))
                .findFirst()
                .map(BangGia::getGiaTien)
                .orElse(BigDecimal.ZERO); // Default giá 0 nếu không tìm thấy

        return GheResponse.builder()
                .maGhe(ghe.getMaGhe())
                .tenGhe(ghe.getTenGhe())
                .trangThai(ghe.getTrangThai())
                .giaTien(giaTien)
                .build();
    }
}