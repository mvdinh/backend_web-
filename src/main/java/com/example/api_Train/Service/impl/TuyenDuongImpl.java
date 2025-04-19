package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.RequestDTO.TuyenDuongDTO;
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
        // Sử dụng Builder để tạo đối tượng TuyenDuong
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
            // Sử dụng Builder để tạo đối tượng TuyenDuong
            TuyenDuong tuyenDuong = TuyenDuong.builder()
                    .gaDi(tuyenDuongDTO.getGaDi())
                    .gaDen(tuyenDuongDTO.getGaDen())
                    .khoangCach(tuyenDuongDTO.getKhoangCach())
                    .thoiGianDuKien(tuyenDuongDTO.getThoiGianDuKien())
                    .build();

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
    public TuyenDuong getTuyenDuongById(Integer id) {
        return tuyenDuongRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tuyến đường không tồn tại"));
    }

    @Override
    public List<TuyenDuongResponse> getAllTuyenDuong() {
        List<TuyenDuong> tuyenDuongs = tuyenDuongRepository.findAll();
        return tuyenDuongs.stream()
                .map(TuyenDuongResponse::fromTuyenDuongResponse) // Use the static method for mapping
                .collect(Collectors.toList());
    }

    @Override
    public List<TuyenDuongResponse> searchTuyenDuong(Integer maTuyen) {
        Optional<TuyenDuong> tuyenDuongs = tuyenDuongRepository.findByMaTuyen(maTuyen);
        return tuyenDuongs.stream()
                .map(TuyenDuongResponse::fromTuyenDuongResponse) // Use the static method for mapping
                .collect(Collectors.toList());
    }

}