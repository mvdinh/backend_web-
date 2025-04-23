package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Request.TuyenDuongRequest;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.TuyenDuong;

import java.util.List;

public interface TuyenDuongService {

    TuyenDuong createTuyenDuong(TuyenDuongRequest tuyenDuongDTO);

    TuyenDuong updateTuyenDuong(Integer id, TuyenDuongRequest tuyenDuongDTO);

    void deleteTuyenDuong(Integer id);

    TuyenDuongResponse getTuyenDuongById(Integer id);

    List<TuyenDuongResponse> getAllTuyenDuong();

}
