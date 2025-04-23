package com.example.api_Train.Controller;

import com.example.api_Train.DTO.Response.NguoiDatVeResponse;
import com.example.api_Train.DTO.Response.VeTauResponse;
import com.example.api_Train.models.NguoiDatVe;
import com.example.api_Train.models.VeTau;
import com.example.api_Train.Repository.NguoiDatVeRepository;
import com.example.api_Train.Repository.VeTauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nguoi-dat-ve")
public class NguoiDatVeController {

    @Autowired
    private NguoiDatVeRepository nguoiDatVeRepository;

    @Autowired
    private VeTauRepository veTauRepository;

    // Get danh sách tất cả người đặt vé

    @GetMapping("/all")
    public ResponseEntity<?> getAllNguoiDatVe() {
        List<NguoiDatVe> danhSachNguoiDat = nguoiDatVeRepository.findAll();

        List<NguoiDatVeResponse> danhSachResponse = danhSachNguoiDat.stream()
                .map(NguoiDatVeResponse::mapNguoiDatVeResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(danhSachResponse, HttpStatus.OK);
    }

    // Get danh sách vé theo mã người đặt
    @GetMapping("/{maNguoiDat}")
    public ResponseEntity<?> getByMaNguoiDat(@PathVariable Integer maNguoiDat) {
        boolean exists = nguoiDatVeRepository.existsById(maNguoiDat);

        if (!exists) {
            return new ResponseEntity<>("Không tìm thấy người đặt vé", HttpStatus.NOT_FOUND);
        }

        List<VeTau> danhSachVe = veTauRepository.findVeTauByNguoiDatVeId(maNguoiDat);

        List<VeTauResponse> danhSachVeResponse = danhSachVe.stream()
                .map(VeTauResponse::fromVeTau)
                .collect(Collectors.toList());

        return new ResponseEntity<>(danhSachVeResponse, HttpStatus.OK);
    }
}
