package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.DatVe.HanhKhachDTO;
import com.example.api_Train.DTO.Response.DatVeTau.HanhKhachResponse;
import com.example.api_Train.Repository.DoiTuongRepository;
import com.example.api_Train.Repository.HanhKhachRepository;
import com.example.api_Train.Service.interf.HanhKhachService;
import com.example.api_Train.models.DoiTuong;
import com.example.api_Train.models.HanhKhach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HanhKhachImpl implements HanhKhachService {

    @Autowired
    private HanhKhachRepository hanhKhachRepository;

    @Autowired
    private DoiTuongRepository doiTuongRepository;

    @Override
    public HanhKhachResponse createHanhKhach(HanhKhachDTO hanhKhachDTO) {
        // Kiểm tra xem đối tượng (DoiTuong) có tồn tại hay không
        DoiTuong doiTuong = doiTuongRepository.findById(hanhKhachDTO.getMaloaiKhach())
                .orElseThrow(() -> new RuntimeException("Đối tượng không tồn tại"));

        // Sử dụng Builder để tạo đối tượng HanhKhach mới
        HanhKhach hanhKhach = HanhKhach.builder()
                .hoTen(hanhKhachDTO.getHoTen())
                .soGiayTo(hanhKhachDTO.getSoGiayTo())
                .ngaySinh(hanhKhachDTO.getNgaySinh())
                .loaiKhach(doiTuong)
                .build();

        // Lưu đối tượng mới mà không cần cung cấp ID (Spring sẽ tự động tạo ID)
        hanhKhach = hanhKhachRepository.save(hanhKhach);
        System.out.println(">>>> Mã hành khách: " + hanhKhach.getMaHanhKhach());

        // Chuyển đổi sang DTO và trả về
        return HanhKhachResponse.mapHanhKhachResponse(hanhKhach);
    }

    @Override
    public HanhKhachResponse updateHanhKhach(Integer id, HanhKhachDTO hanhKhachDTO) {
        // Kiểm tra xem đối tượng (DoiTuong) có tồn tại hay không
        DoiTuong doiTuong = doiTuongRepository.findById(hanhKhachDTO.getMaloaiKhach())
                .orElseThrow(() -> new RuntimeException("Đối tượng không tồn tại"));

        // Tìm đối tượng HanhKhach cũ theo ID
        HanhKhach hanhKhach = hanhKhachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hành khách không tồn tại"));

        // Cập nhật các trường của đối tượng HanhKhach cũ
        hanhKhach.setHoTen(hanhKhachDTO.getHoTen());
        hanhKhach.setSoGiayTo(hanhKhachDTO.getSoGiayTo());
        hanhKhach.setNgaySinh(hanhKhachDTO.getNgaySinh());
        hanhKhach.setLoaiKhach(doiTuong);

        // Lưu đối tượng đã cập nhật
        hanhKhach = hanhKhachRepository.save(hanhKhach);

        // Chuyển đổi sang DTO và trả về
        return HanhKhachResponse.mapHanhKhachResponse(hanhKhach);
    }

    @Override
    public void deleteHanhKhach(Integer id) {
        if (hanhKhachRepository.existsById(id)) {
            hanhKhachRepository.deleteById(id);
        } else {
            throw new RuntimeException("Hành khách không tồn tại");
        }
    }

    @Override
    public HanhKhachResponse getHanhKhachById(Integer id) {
        return hanhKhachRepository.findById(id)
                .map(HanhKhachResponse::mapHanhKhachResponse)
                .orElseThrow(() -> new RuntimeException("Hành khách không tồn tại"));
    }

    @Override
    public List<HanhKhachResponse> getAllHanhKhach() {
        List<HanhKhach> hanhKhachs = hanhKhachRepository.findAll();
        return hanhKhachs.stream()
                .map(HanhKhachResponse::mapHanhKhachResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HanhKhachResponse> searchHanhKhach(Integer maHanhKhach, String hoTen) {
        List<HanhKhach> hanhKhachs = hanhKhachRepository.searchByMaHanhKhachOrHoTen(maHanhKhach, hoTen);
        return hanhKhachs.stream()
                .map(HanhKhachResponse::mapHanhKhachResponse)
                .collect(Collectors.toList());
    }
}
