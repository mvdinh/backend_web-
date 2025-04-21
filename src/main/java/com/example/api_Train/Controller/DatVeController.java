package com.example.api_Train.Controller;

import com.example.api_Train.DTO.RequestDTO.DatVe.DatVeDTO;
import com.example.api_Train.DTO.Response.DatVeTau.DatVeResponse;
import com.example.api_Train.Service.impl.DatVeServiceImpl;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/datve")
public class DatVeController {

    @Autowired
    private DatVeServiceImpl datVeService;

    /**
     * API đặt vé tàu
     */

    @PostMapping("/add")
    public ResponseEntity<?> datVe(@Valid @RequestBody DatVeDTO datVeDTO) {
        try {
            DatVeResponse result = datVeService.datVe(datVeDTO);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API lấy thông tin đặt vé theo mã
     */
    /**
     * API hủy đặt vé
     */
    @PutMapping("/{maDatVe}/huy")
    public ResponseEntity<?> huyDatVe(@PathVariable Integer maDatVe) {
        try {
            DatVeResponse result = datVeService.huyDatVe(maDatVe);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
