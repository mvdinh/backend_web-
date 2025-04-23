package com.example.api_Train.Service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_Train.Repository.ChuyenTauRepository;
import com.example.api_Train.models.ChuyenTau;

@Service
public class SearchChuyenTauService {

    private final ChuyenTauRepository chuyenTauRepository;

    @Autowired
    public SearchChuyenTauService(ChuyenTauRepository chuyenTauRepository) {
        this.chuyenTauRepository = chuyenTauRepository;
    }

    public List<ChuyenTau> searchChuyenTau(String tenGaDi, String tenGaDen, LocalDate ngayKhoiHanh) {
        // Validate input
        if (tenGaDi == null || tenGaDen == null || ngayKhoiHanh == null) {
            throw new IllegalArgumentException("Thiếu thông tin tìm kiếm (ga đi, ga đến hoặc ngày khởi hành)");
        }

        // Xác định khoảng thời gian trong ngày được chọn
        LocalDateTime startOfDay = ngayKhoiHanh.atStartOfDay(); // 00:00:00 của ngày đó
        LocalDateTime endOfDay = ngayKhoiHanh.atTime(LocalTime.MAX); // 23:59:59.999999999 của ngày đó

        // Chuẩn hóa tên ga (bỏ khoảng trắng thừa, không phân biệt hoa thường)
        String formattedGaDi = tenGaDi.trim().toLowerCase();
        String formattedGaDen = tenGaDen.trim().toLowerCase();

        return chuyenTauRepository.findChuyenTauInDay(
                formattedGaDi,
                formattedGaDen,
                startOfDay,
                endOfDay);
    }

    public List<ChuyenTau> getAllChuyenTauForToday() {
        // Get today's date
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00 của ngày hôm nay
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 23:59:59.999999999 của ngày hôm nay

        // Fetch top 8 train journeys for today
        List<ChuyenTau> chuyenTaus = chuyenTauRepository.findTop8ChuyenTauForToday(startOfDay, endOfDay);

        // Limit to 8 results
        return chuyenTaus.size() > 8 ? chuyenTaus.subList(0, 8) : chuyenTaus;
    }
}