package com.example.api_Train.DTO.Response.DatVeTau;

import lombok.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.example.api_Train.models.HanhKhach;
import com.example.api_Train.models.NguoiDatVe;
import com.example.api_Train.models.VeTau;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatVeResponse {
    private Integer maDatVe;
    private Date ngayDatVe;
    private String trangThai;
    private NguoiDatVeResponse nguoiDatVe;
    private List<ChiTietVeResponse> chiTietVeList;
    private BigDecimal tongTien;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor

    public static class NguoiDatVeResponse {
        private Integer maNguoiDat;
        private String hoTen;
        private String email;
        private String soDienThoai;
        private String cccd;

        public static NguoiDatVeResponse mapNguoiDatVeResponse(NguoiDatVe NguoiDatVe) {
            return NguoiDatVeResponse.builder()
                    .maNguoiDat(NguoiDatVe.getMaNguoiDat())
                    .hoTen(NguoiDatVe.getHoTen())
                    .email(NguoiDatVe.getEmail())
                    .soDienThoai(NguoiDatVe.getSoDienThoai())
                    .cccd(NguoiDatVe.getCccd())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChiTietVeResponse {
        private VeTauResponse veTau;
        private HanhKhachResponse hanhKhach;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HanhKhachResponse {
        private Integer maHanhKhach;
        private String hoTen;
        private String soGiayTo;
        private String ngaySinh;
        private String tenLoaiKhach;

        public static HanhKhachResponse mapHanhKhachResponse(HanhKhach hanhKhach) {
            return HanhKhachResponse.builder()
                    .maHanhKhach(hanhKhach.getMaHanhKhach())
                    .hoTen(hanhKhach.getHoTen())
                    .soGiayTo(hanhKhach.getSoGiayTo())
                    .ngaySinh(hanhKhach.getNgaySinh().toString())
                    .tenLoaiKhach(hanhKhach.getLoaiKhach().getTenLoaiKhach())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VeTauResponse {
        private Integer maVe;
        private Integer chuyenTau;
        private String soGhe;
        private BigDecimal giaVe;
        private String trangThai;

        public static VeTauResponse fromVeTau(VeTau veTau) {
            return VeTauResponse.builder()
                    .maVe(veTau.getMaVe())
                    .chuyenTau(veTau.getChuyenTau().getMaChuyenTau())
                    .soGhe(veTau.getGhe().getTenGhe())
                    .trangThai(veTau.getTinhTrangVe().getTinhTrangVe())
                    .build();
        }
    }
}
