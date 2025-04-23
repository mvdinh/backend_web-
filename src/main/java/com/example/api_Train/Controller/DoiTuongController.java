package com.example.api_Train.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.api_Train.DTO.Response.DoiTuongResponse;
import com.example.api_Train.Service.interf.DoiTuongService;

@RestController
@RequestMapping("/api/doi-tuong")
public class DoiTuongController {

    @Autowired
    private DoiTuongService doiTuongService;

    @GetMapping("/all")
    public List<DoiTuongResponse> getAllDoiTuong() {
        return doiTuongService.getAll();
    }
}
