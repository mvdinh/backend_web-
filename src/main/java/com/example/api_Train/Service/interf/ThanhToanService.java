package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Response.DatVeResponse;

public interface ThanhToanService {
    public DatVeResponse capNhatTrangThaiThanhToanThanhCong(Integer maDatVe);

    public DatVeResponse huyVe(Integer maDatVe);
}
