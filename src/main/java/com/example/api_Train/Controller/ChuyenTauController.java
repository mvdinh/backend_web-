package com.example.api_Train.Controller;

import com.example.api_Train.DTO.Request.ChuyenTauRequest;
import com.example.api_Train.DTO.Response.ChuyenTauResponse;
import com.example.api_Train.Service.interf.ChuyenTauService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chuyentau")
@RequiredArgsConstructor
public class ChuyenTauController {

    private final ChuyenTauService chuyenTauService;

    @PostMapping("/add")
    public ResponseEntity<ChuyenTauResponse> themChuyenTau(@RequestBody ChuyenTauRequest request) {
        ChuyenTauResponse response = chuyenTauService.themChuyenTau(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChuyenTauResponse> suaChuyenTau(@PathVariable Integer id,
            @RequestBody ChuyenTauRequest request) {
        ChuyenTauResponse response = chuyenTauService.suaChuyenTau(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> xoaChuyenTau(@PathVariable Integer id) {
        chuyenTauService.xoaChuyenTau(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChuyenTauResponse> layChiTietChuyenTau(@PathVariable Integer id) {
        ChuyenTauResponse response = chuyenTauService.layChiTietChuyenTau(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChuyenTauResponse>> layTatCaChuyenTau() {
        List<ChuyenTauResponse> danhSach = chuyenTauService.layTatCaChuyenTau();
        return ResponseEntity.ok(danhSach);
    }

}
