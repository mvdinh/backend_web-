package com.example.api_Train.Controller;

import com.example.api_Train.DTO.Request.DatVeRequest;
import com.example.api_Train.DTO.Response.DatVeResponse;
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
    public ResponseEntity<?> datVe(@Valid @RequestBody DatVeRequest datVeRequest) {
        try {
            DatVeResponse result = datVeService.datVe(datVeRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
