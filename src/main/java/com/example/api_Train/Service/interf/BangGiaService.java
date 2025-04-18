package com.example.api_Train.Service.interf;

import com.example.api_Train.DTO.RequestDTO.BangGiaDTO;
import com.example.api_Train.models.BangGia;

public interface BangGiaService {

    BangGia createBangGia(BangGiaDTO bangGiaDTO);

    BangGia updateBangGia(Integer maBangGia, BangGiaDTO bangGiaDTO);

    BangGia getBangGiaById(Integer maBangGia);

    void deleteBangGia(Integer maBangGia);
}
