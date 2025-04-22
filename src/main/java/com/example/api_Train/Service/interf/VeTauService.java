package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Request.DatVe.HanhKhachDTO;
import com.example.api_Train.DTO.Request.DatVe.VeTauDTO;
import com.example.api_Train.models.VeTau;

public interface VeTauService {
    VeTau createVeTau(VeTauDTO veTauDTO, HanhKhachDTO hanhKhachDTO) throws Exception;

}
