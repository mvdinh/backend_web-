package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Request.HanhKhachRequest;
import com.example.api_Train.DTO.Response.HanhKhachResponse;

import java.util.List;

public interface HanhKhachService {
    HanhKhachResponse createHanhKhach(HanhKhachRequest hanhKhachRequest);

    HanhKhachResponse updateHanhKhach(Integer id, HanhKhachRequest hanhKhachRequest);

    void deleteHanhKhach(Integer id);

    HanhKhachResponse getHanhKhachById(Integer id);

    List<HanhKhachResponse> getAllHanhKhach();

    List<HanhKhachResponse> searchHanhKhach(Integer maHanhKhach, String hoTen);
}
