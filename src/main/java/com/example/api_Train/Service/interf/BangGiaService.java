package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.Request.BangGiaRequest;
import com.example.api_Train.models.BangGia;

public interface BangGiaService {

    BangGia createBangGia(BangGiaRequest bangGiaRequest);

    BangGia updateBangGia(Integer maBangGia, BangGiaRequest bangGiaRequest);

    BangGiaRequest getBangGiaById(Integer maBangGia);

    void deleteBangGia(Integer maBangGia);
}
