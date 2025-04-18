package com.example.api_Train.Controller;

import com.example.api_Train.Service.VeTauService;
import com.example.api_Train.models.VeTau;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vetau")
public class VeTauController {

    @Autowired
    private VeTauService veTauService;

    // Lấy tất cả các vé tàu
    @GetMapping
    public List<VeTau> getAllVeTau() {
        return veTauService.GetAllVeTau();
    }

    // Lấy vé tàu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<VeTau>> getVeTauById(@PathVariable("id") Integer id) {
        Optional<VeTau> veTau = veTauService.GetVeTauId(id);
        if (veTau != null) {
            return ResponseEntity.ok(veTau);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Tạo vé tàu mới
    @PostMapping
    public ResponseEntity<VeTau> createVeTau(@RequestBody VeTau veTau) {
        VeTau createdVeTau = veTauService.CreateVeTau(veTau);
        return ResponseEntity.ok(createdVeTau);
    }

    // Cập nhật vé tàu
    @PutMapping("/{id}")
    public ResponseEntity<VeTau> updateVeTau(@PathVariable("id") Integer id, @RequestBody VeTau veTau) {
        VeTau updatedVeTau = veTauService.UpdateVeTau(id, veTau);
        if (updatedVeTau != null) {
            return ResponseEntity.ok(updatedVeTau);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa vé tàu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeTau(@PathVariable("id") Integer id) {
        boolean isDeleted = veTauService.DeleteVeTau(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
