package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Request.DatVe.DatVeDTO;
import com.example.api_Train.models.DatVe;
import com.example.api_Train.models.LoaiCho;
import com.example.api_Train.models.NguoiDung;
import com.example.api_Train.models.VeTau;

public interface DatVeService {
    DatVe datVe(DatVeDTO datVeDTO, NguoiDung nguoiDung, VeTau veTau, LoaiCho loaiCho) throws Exception;

    void getAllVeTauByNguoiDat(int maNguoiDat) throws Exception;
}
