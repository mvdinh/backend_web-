package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.RequestDTO.TuyenDuongDTO;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.TuyenDuong;

import java.util.List;

public interface TuyenDuongService {

    TuyenDuong createTuyenDuong(TuyenDuongDTO tuyenDuongDTO);

    TuyenDuong updateTuyenDuong(Integer id, TuyenDuongDTO tuyenDuongDTO);

    void deleteTuyenDuong(Integer id);

    TuyenDuong getTuyenDuongById(Integer id);

    List<TuyenDuongResponse> getAllTuyenDuong();

    List<TuyenDuongResponse> searchTuyenDuong(Integer maTuyenDuong);
}
