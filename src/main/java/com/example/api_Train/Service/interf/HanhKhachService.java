package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.RequestDTO.DatVe.HanhKhachDTO;
import com.example.api_Train.DTO.Response.DatVeTau.HanhKhachResponse;

import java.util.List;

public interface HanhKhachService {
    HanhKhachResponse createHanhKhach(HanhKhachDTO hanhKhachDTO);

    HanhKhachResponse updateHanhKhach(Integer id, HanhKhachDTO hanhKhachDTO);

    void deleteHanhKhach(Integer id);

    HanhKhachResponse getHanhKhachById(Integer id);

    List<HanhKhachResponse> getAllHanhKhach();

    List<HanhKhachResponse> searchHanhKhach(Integer maHanhKhach, String hoTen);
}
