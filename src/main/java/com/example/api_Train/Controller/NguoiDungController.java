package com.example.api_Train.Controller;

import com.example.api_Train.Service.NguoiDungService;
import com.example.api_Train.models.NguoiDung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nguoidung")
public class NguoiDungController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping
    public List<NguoiDung> getAllNguoiDung() {
        return nguoiDungService.getAllNguoiDung();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NguoiDung> getNguoiDungById(@PathVariable Integer id) {
        Optional<NguoiDung> optional = nguoiDungService.getNguoiDungById(id);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public NguoiDung createNguoiDung(@RequestBody NguoiDung nguoiDung) {
        return nguoiDungService.createNguoiDung(nguoiDung);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NguoiDung> updateNguoiDung(@PathVariable Integer id, @RequestBody NguoiDung nguoiDung) {
        NguoiDung updated = nguoiDungService.updateNguoiDung(id, nguoiDung);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNguoiDung(@PathVariable Integer id) {
        boolean deleted = nguoiDungService.deleteNguoiDung(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
