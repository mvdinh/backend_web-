package com.example.api_Train.Controller;

import com.example.api_Train.DTO.Request.TuyenDuongRequest;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.Service.interf.TuyenDuongService;
import com.example.api_Train.models.TuyenDuong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tuyenDuong")
public class TuyenDuongController {

    @Autowired
    private TuyenDuongService tuyenDuongService;

    @PostMapping("/create")
    public ResponseEntity<TuyenDuong> createTuyenDuong(@RequestBody TuyenDuongRequest tuyenDuongDTO) {
        TuyenDuong tuyenDuong = tuyenDuongService.createTuyenDuong(tuyenDuongDTO);
        return ResponseEntity.ok(tuyenDuong);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TuyenDuong> updateTuyenDuong(@PathVariable Integer id,
            @RequestBody TuyenDuongRequest tuyenDuongDTO) {
        TuyenDuong tuyenDuong = tuyenDuongService.updateTuyenDuong(id, tuyenDuongDTO);
        return ResponseEntity.ok(tuyenDuong);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTuyenDuong(@PathVariable Integer id) {
        tuyenDuongService.deleteTuyenDuong(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTuyenDuongById(@PathVariable Integer id) {
        TuyenDuongResponse tuyenDuong = tuyenDuongService.getTuyenDuongById(id);
        return ResponseEntity.ok(tuyenDuong);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TuyenDuongResponse>> getAllTuyenDuong() {
        List<TuyenDuongResponse> tuyenDuongResponses = tuyenDuongService.getAllTuyenDuong();
        return ResponseEntity.ok(tuyenDuongResponses);
    }

}
