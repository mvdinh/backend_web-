package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Request.DatVeRequest;
import com.example.api_Train.DTO.Response.DatVeResponse;
import com.example.api_Train.models.DatVe;
import com.example.api_Train.models.LoaiCho;
import com.example.api_Train.models.NguoiDung;
import com.example.api_Train.models.VeTau;

public interface DatVeService {
    public DatVeResponse datVe(DatVeRequest datVeRequest);

    public DatVeResponse getByMaDatVe(Integer maDatVe);
}
