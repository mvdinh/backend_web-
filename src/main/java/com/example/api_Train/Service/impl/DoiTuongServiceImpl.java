package com.example.api_Train.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_Train.DTO.Response.DoiTuongResponse;
import com.example.api_Train.Repository.DoiTuongRepository;
import com.example.api_Train.Service.interf.DoiTuongService;
import com.example.api_Train.models.DoiTuong;

@Service
public class DoiTuongServiceImpl implements DoiTuongService {

    @Autowired
    private DoiTuongRepository doiTuongRepository;

    @Override
    public List<DoiTuongResponse> getAll() {
        List<DoiTuong> list = doiTuongRepository.findAll();
        return list.stream()
                .map(DoiTuongResponse::fromDoiTuongponse)
                .collect(Collectors.toList());
    }
}
