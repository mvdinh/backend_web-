package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.TuyenDuongDTO;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.Repository.TuyenDuongRepository;
import com.example.api_Train.Service.interf.TuyenDuongService;
import com.example.api_Train.models.TuyenDuong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TuyenDuongImpl implements TuyenDuongService {

    @Autowired
    private TuyenDuongRepository tuyenDuongRepository;

    @Override
    public TuyenDuong createTuyenDuong(TuyenDuongDTO tuyenDuongDTO) {
        TuyenDuong tuyenDuong = TuyenDuong.builder()
                .gaDi(tuyenDuongDTO.getGaDi())
                .gaDen(tuyenDuongDTO.getGaDen())
                .khoangCach(tuyenDuongDTO.getKhoangCach())
                .thoiGianDuKien(tuyenDuongDTO.getThoiGianDuKien())
                .build();

        return tuyenDuongRepository.save(tuyenDuong);
    }

    @Override
    public TuyenDuong updateTuyenDuong(Integer id, TuyenDuongDTO tuyenDuongDTO) {
        Optional<TuyenDuong> optionalTuyenDuong = tuyenDuongRepository.findById(id);
        if (optionalTuyenDuong.isPresent()) {
            TuyenDuong tuyenDuong = optionalTuyenDuong.get();
            tuyenDuong.setGaDi(tuyenDuongDTO.getGaDi());
            tuyenDuong.setGaDen(tuyenDuongDTO.getGaDen());
            tuyenDuong.setKhoangCach(tuyenDuongDTO.getKhoangCach());
            tuyenDuong.setThoiGianDuKien(tuyenDuongDTO.getThoiGianDuKien());
            return tuyenDuongRepository.save(tuyenDuong);
        } else {
            throw new RuntimeException("Tuyến đường không tồn tại");
        }
    }

    @Override
    public void deleteTuyenDuong(Integer id) {
        if (tuyenDuongRepository.existsById(id)) {
            tuyenDuongRepository.deleteById(id);
        } else {
            throw new RuntimeException("Tuyến đường không tồn tại");
        }
    }

    @Override
    public TuyenDuongResponse getTuyenDuongById(Integer id) {
        TuyenDuong tuyenDuong = tuyenDuongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuyến đường không tồn tại"));

        return TuyenDuongResponse.fromTuyenDuongResponse(tuyenDuong); // Chuyển đổi sang DTO response
    }

    @Override
    public List<TuyenDuongResponse> getAllTuyenDuong() {
        List<TuyenDuong> tuyenDuongs = tuyenDuongRepository.findAll();
        return tuyenDuongs.stream()
                .map(TuyenDuongResponse::fromTuyenDuongResponse)
                .collect(Collectors.toList());
    }
}
