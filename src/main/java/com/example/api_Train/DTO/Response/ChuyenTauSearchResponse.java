package com.example.api_Train.DTO.Response;

import com.example.api_Train.DTO.Request.TuyenDuongRequest;
import com.example.api_Train.Repository.TuyenDuongRepository;
import com.example.api_Train.models.ChuyenTau;

import lombok.Data;

@Data
public class ChuyenTauSearchResponse {
    private int maChuyenTau;
    private String tenTau;
    private String ngayGioKhoiHanh;
    private TuyenDuongResponse tuyenDuong;
    private int soGheTrong;

    public static ChuyenTauSearchResponse ChuyenTauSearchResponse(ChuyenTau chuyenTau) {
        ChuyenTauSearchResponse response = new ChuyenTauSearchResponse();

        response.setMaChuyenTau(chuyenTau.getMaChuyenTau());
        response.setTenTau(chuyenTau.getTau().getTenTau());
        response.setNgayGioKhoiHanh(chuyenTau.getNgayGioKhoiHanh().toString());

        TuyenDuongResponse tuyenDuongResponse = new TuyenDuongResponse();
        tuyenDuongResponse.setMaTuyenDuong(chuyenTau.getTuyenDuong().getMaTuyen());
        tuyenDuongResponse.setGaDi(chuyenTau.getTuyenDuong().getGaDi().getTenGa());
        tuyenDuongResponse.setGaDen(chuyenTau.getTuyenDuong().getGaDen().getTenGa());
        tuyenDuongResponse.setThoiGianDuKien(chuyenTau.getTuyenDuong().getThoiGianDuKien());
        tuyenDuongResponse.setKhoangCach(chuyenTau.getTuyenDuong().getKhoangCach());

        // Calculate total empty seats using GheChuyenTau intermediate table
        int soGheTrong = chuyenTau.getTau().getDanhSachToaTau().stream()
                .flatMap(toa -> toa.getDanhSachGhe().stream())
                .filter(ghe -> {
                    // Find the corresponding GheChuyenTau for this Ghe and this ChuyenTau
                    return ghe.getGheChuyenTaus().stream()
                            .filter(gheChuyenTau -> gheChuyenTau.getChuyenTau().getMaChuyenTau() == chuyenTau
                                    .getMaChuyenTau())
                            .anyMatch(gheChuyenTau -> "Trống".equalsIgnoreCase(gheChuyenTau.getTrangThai()));
                })
                .mapToInt(ghe -> 1)
                .sum();

        response.setTuyenDuong(tuyenDuongResponse);
        response.setSoGheTrong(soGheTrong);

        return response;
    }
}
