package com.example.api_Train.Service.interf;

import java.time.LocalDateTime;
import java.util.List;

import com.example.api_Train.DTO.RequestDTO.ChuyenTauDTO;
import com.example.api_Train.models.ChuyenTau;

public interface ChuyenTauService {
    ChuyenTau createChuyenTau(ChuyenTauDTO chuyenTauDTO);

    ChuyenTau updateChuyenTau(Integer maChuyenTau, ChuyenTauDTO chuyenTauDTO);

    ChuyenTau getChuyenTauById(Integer maChuyenTau);

    void deleteChuyenTau(Integer maChuyenTau);

    List<ChuyenTau> getAllChuyenTauByMaTuyen(Integer maTuyenDuong);

    List<ChuyenTau> getAllChuyenTau();

    List<ChuyenTau> getChuyenTauByNgayGioKhoiHanh(LocalDateTime ngayGioKhoiHanh);
}
