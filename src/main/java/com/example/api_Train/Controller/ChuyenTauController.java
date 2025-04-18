package com.example.api_Train.Controller;

import com.example.api_Train.DTO.RequestDTO.ChuyenTauDTO;
import com.example.api_Train.DTO.Response.ChuyenTauResponse;
import com.example.api_Train.Service.interf.ChuyenTauService;
import com.example.api_Train.models.ChuyenTau;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chuyentau")
public class ChuyenTauController {

    private final ChuyenTauService chuyenTauService;

    @Autowired
    public ChuyenTauController(ChuyenTauService chuyenTauService) {
        this.chuyenTauService = chuyenTauService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addChuyenTau(@RequestBody ChuyenTauDTO chuyenTauRequest) {
        try {
            // Tạo đối tượng ChuyenTau từ request
            ChuyenTau newChuyenTau = chuyenTauService.createChuyenTau(chuyenTauRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ChuyenTauResponse.mapChuyenTauResponse(newChuyenTau));
        } catch (Exception e) {
            // Log lỗi chi tiết

            // Trả về thông tin lỗi trong body response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while adding ChuyenTau: " + e.getMessage());
        }
    }

    // ✅ Cập nhật chuyến tàu
    @PutMapping("/{id}")
    public ResponseEntity<ChuyenTau> updateChuyenTau(
            @PathVariable("id") Integer id,
            @RequestBody ChuyenTauDTO chuyenTauDTO) {
        ChuyenTau updated = chuyenTauService.updateChuyenTau(id, chuyenTauDTO);
        return ResponseEntity.ok(updated);
    }

    // ✅ Lấy chuyến tàu theo mã
    @GetMapping("/{id}")
    public ResponseEntity<?> getChuyenTauById(@PathVariable("id") Integer id) {
        try {
            ChuyenTau chuyenTau = chuyenTauService.getChuyenTauById(id);
            return ResponseEntity.ok().body(ChuyenTauResponse.mapChuyenTauResponse(chuyenTau));
        } catch (Exception e) {
            // In lỗi ra console
            e.printStackTrace();

            // Trả về lỗi cho client
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lấy chuyến tàu với ID = " + id + ": " + e.getMessage());
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ChuyenTauResponse>> getAll() {
        // Lấy danh sách chuyến tàu từ service
        List<ChuyenTau> chuyenTauList = chuyenTauService.getAllChuyenTau();

        // Chuyển đổi danh sách ChuyenTau thành danh sách ChuyenTauResponse
        List<ChuyenTauResponse> responseList = chuyenTauList.stream()
                .map(ChuyenTauResponse::mapChuyenTauResponse)
                .collect(Collectors.toList());

        // Trả về danh sách chuyến tàu kèm bảng giá
        return ResponseEntity.ok(responseList);
    }

    // ✅ Xóa chuyến tàu theo mã
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChuyenTau(@PathVariable("id") Integer id) {
        chuyenTauService.deleteChuyenTau(id);
        return ResponseEntity.ok().build();
    }

    // ✅ Lấy tất cả chuyến tàu theo mã tuyến
    @GetMapping("/tuyen/all/{maTuyenDuong}")
    public ResponseEntity<List<ChuyenTau>> getAllByMaTuyen(@PathVariable Integer maTuyenDuong) {
        List<ChuyenTau> results = chuyenTauService.getAllChuyenTauByMaTuyen(maTuyenDuong);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/get/today")
    public ResponseEntity<List<ChuyenTauResponse>> getTodayChuyenTau() {
        LocalDateTime now = LocalDateTime.now();
        // Call the service to get the list of trains departing today
        List<ChuyenTau> result = chuyenTauService.getChuyenTauByNgayGioKhoiHanh(now);

        // Convert the list of ChuyenTau to ChuyenTauResponse DTO
        List<ChuyenTauResponse> response = result.stream()
                .map(ChuyenTauResponse::mapChuyenTauResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
