package com.example.api_Train.Service.interf;

// ChuyenTauService.java

import com.example.api_Train.DTO.Request.ChuyenTauRequest;
import com.example.api_Train.DTO.Response.ChuyenTauResponse;

import java.util.List;

public interface ChuyenTauService {
    ChuyenTauResponse themChuyenTau(ChuyenTauRequest request);

    ChuyenTauResponse suaChuyenTau(Integer id, ChuyenTauRequest request);

    void xoaChuyenTau(Integer id);

    ChuyenTauResponse layChiTietChuyenTau(Integer id);

    List<ChuyenTauResponse> layTatCaChuyenTau();
}
