package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.HanhKhachRequest;
import com.example.api_Train.DTO.Response.HanhKhachResponse;
import com.example.api_Train.Repository.DoiTuongRepository;
import com.example.api_Train.Repository.HanhKhachRepository;
import com.example.api_Train.Service.interf.HanhKhachService;
import com.example.api_Train.models.DoiTuong;
import com.example.api_Train.models.HanhKhach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HanhKhachServiceImpl implements HanhKhachService {

    private final HanhKhachRepository hanhKhachRepository;
    private final DoiTuongRepository doiTuongRepository;

    @Override
    public HanhKhachResponse createHanhKhach(HanhKhachRequest request) {
        DoiTuong doiTuong = doiTuongRepository.findById(request.getMaLoaiKhach())
                .orElseThrow(
                        () -> new RuntimeException("Không tìm thấy loại khách có mã: " + request.getMaLoaiKhach()));

        HanhKhach hanhKhach = HanhKhach.builder()
                .hoTen(request.getHoTen())
                .soGiayTo(request.getSoGiayTo())
                .ngaySinh(request.getNgaySinh())
                .loaiKhach(doiTuong)
                .build();

        hanhKhach = hanhKhachRepository.save(hanhKhach);
        return HanhKhachResponse.mapHanhKhachResponse(hanhKhach);
    }

    @Override
    public HanhKhachResponse updateHanhKhach(Integer id, HanhKhachRequest request) {
        HanhKhach hanhKhach = hanhKhachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hành khách với mã: " + id));

        DoiTuong doiTuong = doiTuongRepository.findById(request.getMaLoaiKhach())
                .orElseThrow(
                        () -> new RuntimeException("Không tìm thấy loại khách có mã: " + request.getMaLoaiKhach()));

        hanhKhach.setHoTen(request.getHoTen());
        hanhKhach.setSoGiayTo(request.getSoGiayTo());
        hanhKhach.setNgaySinh(request.getNgaySinh());
        hanhKhach.setLoaiKhach(doiTuong);

        hanhKhach = hanhKhachRepository.save(hanhKhach);
        return HanhKhachResponse.mapHanhKhachResponse(hanhKhach);
    }

    @Override
    public void deleteHanhKhach(Integer id) {
        if (!hanhKhachRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy hành khách với mã: " + id);
        }
        hanhKhachRepository.deleteById(id);
    }

    @Override
    public HanhKhachResponse getHanhKhachById(Integer id) {
        return hanhKhachRepository.findById(id)
                .map(HanhKhachResponse::mapHanhKhachResponse)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hành khách với mã: " + id));
    }

    @Override
    public List<HanhKhachResponse> getAllHanhKhach() {
        return hanhKhachRepository.findAll().stream()
                .map(HanhKhachResponse::mapHanhKhachResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HanhKhachResponse> searchHanhKhach(Integer maHanhKhach, String hoTen) {
        if (maHanhKhach == null && (hoTen == null || hoTen.trim().isEmpty())) {
            throw new IllegalArgumentException("Phải cung cấp ít nhất mã hành khách hoặc họ tên để tìm kiếm.");
        }

        return hanhKhachRepository.searchByMaHanhKhachOrHoTen(maHanhKhach, hoTen).stream()
                .map(HanhKhachResponse::mapHanhKhachResponse)
                .collect(Collectors.toList());
    }
}
