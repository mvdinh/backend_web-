package com.example.api_Train.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.api_Train.models.VeTau;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeTauResponse {
    private Integer maVe;
    private String tenTau;
    private String tenKhachHang;
    private String soGhe;
    private String gaDen;
    private String gaDi;
    private String tenToa;
    private String tenLoaiCho;
    private LocalDateTime ngayGioKhoiHanh;
    private String trangThaiVe;
    private BigDecimal giaVe;

    public static VeTauResponse fromVeTau(VeTau veTau) {
        return VeTauResponse.builder()
                .maVe(veTau.getMaVe())
                .tenTau(veTau.getChuyenTau().getTau().getTenTau())
                .tenKhachHang(veTau.getHanhKhach().getHoTen())
                .soGhe(veTau.getGhe().getTenGhe())
                .gaDi(veTau.getChuyenTau().getTuyenDuong().getGaDi().getTenGa())
                .gaDen(veTau.getChuyenTau().getTuyenDuong().getGaDen().getTenGa())
                .tenToa(veTau.getGhe().getToaTau().getTenToa())
                .tenLoaiCho(veTau.getGhe().getToaTau().getLoaiCho().getTenLoaiCho())
                .ngayGioKhoiHanh(veTau.getChuyenTau().getNgayGioKhoiHanh())
                .trangThaiVe(veTau.getTinhTrangVe().getTinhTrangVe())
                .giaVe(getGiaVeFromThanhToan(veTau))
                .build();
    }

    private static BigDecimal getGiaVeFromThanhToan(VeTau veTau) {
        try {
            return veTau.getThanhToan().getSoTien();

        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
