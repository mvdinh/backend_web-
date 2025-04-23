package com.example.api_Train.Controller;

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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nguoi-dat-ve")
public class NguoiDatVeController {

    @Autowired
    private NguoiDatVeRepository nguoiDatVeRepository;

    @Autowired
    private VeTauRepository veTauRepository;

    @GetMapping("/{maNguoiDat}")
    public ResponseEntity<?> getByMaNguoiDat(@PathVariable Integer maNguoiDat) {
        // Check if nguoi dat ve exists
        boolean exists = nguoiDatVeRepository.existsById(maNguoiDat);

        if (!exists) {
            return new ResponseEntity<>("Không tìm thấy người đặt vé", HttpStatus.NOT_FOUND);
        }

        // Get tickets directly using SQL query
        List<VeTau> danhSachVe = veTauRepository.findVeTauByNguoiDatVeId(maNguoiDat);

        // Convert to response objects
        List<VeTauResponse> danhSachVeResponse = danhSachVe.stream()
                .map(VeTauResponse::fromVeTau)
                .collect(Collectors.toList());

        return new ResponseEntity<>(danhSachVeResponse, HttpStatus.OK);
    }
}