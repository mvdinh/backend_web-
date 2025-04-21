package com.example.api_Train.DTO.Response;

import com.example.api_Train.models.ChuyenTau;

import lombok.Data;

@Data
public class ChuyenTauSearchResponse {
    private int maChuyenTau;
    private String tenTau;
    private String ngayGioKhoiHanh;
    private TuyenDuongDTO tuyenDuong;
    private int soGheTrong;

    @Data
    public static class TuyenDuongDTO {
        private int maTuyenDuong;
        private String gaDi;
        private String gaDen;
        private String thoiGianDuKien;
        private int khoangCach;
    }

    public static ChuyenTauSearchResponse ChuyenTauSearchResponse(ChuyenTau chuyenTau) {
        ChuyenTauSearchResponse response = new ChuyenTauSearchResponse();

        response.setMaChuyenTau(chuyenTau.getMaChuyenTau());
        response.setTenTau(chuyenTau.getTau().getTenTau());
        response.setNgayGioKhoiHanh(chuyenTau.getNgayGioKhoiHanh().toString());

        TuyenDuongDTO tuyenDuongDTO = new TuyenDuongDTO();
        tuyenDuongDTO.setMaTuyenDuong(chuyenTau.getTuyenDuong().getMaTuyen());
        // tuyenDuongDTO.setGaDi(chuyenTau.getTuyenDuong().getGaDi());
        // tuyenDuongDTO.setGaDen(chuyenTau.getTuyenDuong().getGaDen());
        tuyenDuongDTO.setThoiGianDuKien(chuyenTau.getTuyenDuong().getThoiGianDuKien());
        tuyenDuongDTO.setKhoangCach(chuyenTau.getTuyenDuong().getKhoangCach());

        // Calculate total empty seats
        int soGheTrong = chuyenTau.getTau().getDanhSachToaTau().stream()
                .flatMap(toa -> toa.getDanhSachGhe().stream())
                .filter(ghe -> "Trống".equalsIgnoreCase(ghe.getTrangThai()))
                .mapToInt(ghe -> 1)
                .sum();

        response.setTuyenDuong(tuyenDuongDTO);
        response.setSoGheTrong(soGheTrong);

        return response;
    }
}
