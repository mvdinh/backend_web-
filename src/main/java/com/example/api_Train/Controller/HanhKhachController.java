package com.example.api_Train.Controller;

import com.example.api_Train.DTO.Request.HanhKhachRequest;
import com.example.api_Train.DTO.Response.HanhKhachResponse;
import com.example.api_Train.Service.interf.HanhKhachService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hanhKhach")
public class HanhKhachController {

    @Autowired
    private HanhKhachService hanhKhachService;

    @PostMapping("/create")
    public ResponseEntity<HanhKhachResponse> createHanhKhach(@RequestBody HanhKhachRequest hanhKhachRequest) {
        HanhKhachResponse hanhKhachResponse = hanhKhachService.createHanhKhach(hanhKhachRequest);
        return ResponseEntity.ok(hanhKhachResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HanhKhachResponse> updateHanhKhach(@PathVariable Integer id,
            @RequestBody HanhKhachRequest hanhKhachRequest) {
        HanhKhachResponse hanhKhachResponse = hanhKhachService.updateHanhKhach(id, hanhKhachRequest);
        return ResponseEntity.ok(hanhKhachResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHanhKhach(@PathVariable Integer id) {
        hanhKhachService.deleteHanhKhach(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HanhKhachResponse> getHanhKhachById(@PathVariable Integer id) {
        HanhKhachResponse hanhKhachResponse = hanhKhachService.getHanhKhachById(id);
        return ResponseEntity.ok(hanhKhachResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<HanhKhachResponse>> getAllHanhKhach() {
        List<HanhKhachResponse> hanhKhachResponses = hanhKhachService.getAllHanhKhach();
        return ResponseEntity.ok(hanhKhachResponses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<HanhKhachResponse>> searchHanhKhach(@RequestParam(required = false) Integer maHanhKhach,
            @RequestParam(required = false) String hoTen) {
        List<HanhKhachResponse> hanhKhachResponses = hanhKhachService.searchHanhKhach(maHanhKhach, hoTen);
        return ResponseEntity.ok(hanhKhachResponses);
    }
}
