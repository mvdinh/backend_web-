package com.example.api_Train.Service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_Train.DTO.RequestDTO.ChuyenTauDTO;
import com.example.api_Train.Repository.ChuyenTauRepository;
import com.example.api_Train.Repository.TauRepository;
import com.example.api_Train.Repository.TuyenDuongRepository;
import com.example.api_Train.Service.interf.ChuyenTauService;
import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.Tau;
import com.example.api_Train.models.TuyenDuong;

@Service
public class ChuyenTauImpl implements ChuyenTauService {

        private final TauRepository tauRepository;
        private final TuyenDuongRepository tuyenDuongRepository;
        private final ChuyenTauRepository chuyenTauRepository;

        @Autowired
        public ChuyenTauImpl(
                        TauRepository tauRepository,
                        TuyenDuongRepository tuyenDuongRepository,
                        ChuyenTauRepository chuyenTauRepository) {
                this.tauRepository = tauRepository;
                this.tuyenDuongRepository = tuyenDuongRepository;
                this.chuyenTauRepository = chuyenTauRepository;
        }

        @Override
        public ChuyenTau createChuyenTau(ChuyenTauDTO chuyenTauDTO) {
                Tau tau = tauRepository.findById(chuyenTauDTO.getMaTau())
                                .orElseThrow(() -> new RuntimeException("Tau not found"));

                TuyenDuong tuyenDuong = tuyenDuongRepository.findById(chuyenTauDTO.getMaTuyenDuong())
                                .orElseThrow(() -> new RuntimeException("TuyenDuong not found"));

                ChuyenTau chuyenTau = ChuyenTau.builder()
                                .tau(tau)
                                .ngayGioKhoiHanh(chuyenTauDTO.getNgayGioKhoiHanh())
                                .tuyenDuong(tuyenDuong)
                                .build();

                return chuyenTauRepository.save(chuyenTau);
        }

        @Override
        public ChuyenTau updateChuyenTau(Integer maChuyenTau, ChuyenTauDTO chuyenTauDTO) {
                ChuyenTau chuyenTau = chuyenTauRepository.findById(maChuyenTau)
                                .orElseThrow(() -> new RuntimeException("Chuyen tau not found"));

                Tau tau = tauRepository.findById(chuyenTauDTO.getMaTau())
                                .orElseThrow(() -> new RuntimeException("Tau not found"));

                TuyenDuong tuyenDuong = tuyenDuongRepository.findById(chuyenTauDTO.getMaTuyenDuong())
                                .orElseThrow(() -> new RuntimeException("Tuyen duong not found"));

                chuyenTau.setTau(tau);
                chuyenTau.setNgayGioKhoiHanh(chuyenTauDTO.getNgayGioKhoiHanh());
                chuyenTau.setTuyenDuong(tuyenDuong);

                return chuyenTauRepository.save(chuyenTau);
        }

        @Override
        public ChuyenTau getChuyenTauById(Integer maChuyenTau) {
                return chuyenTauRepository.findById(maChuyenTau)
                                .orElseThrow(() -> new RuntimeException("Chuyen tau not found"));
        }

        @Override
        public void deleteChuyenTau(Integer maChuyenTau) {
                ChuyenTau chuyenTau = chuyenTauRepository.findById(maChuyenTau)
                                .orElseThrow(() -> new RuntimeException("Chuyen tau not found"));
                chuyenTauRepository.delete(chuyenTau);
        }

        @Override
        public List<ChuyenTau> getAllChuyenTauByMaTuyen(Integer maTuyen) {
                return chuyenTauRepository.findAllByTuyenDuong_MaTuyen(maTuyen);
        }

        public List<ChuyenTau> getAllChuyenTau() {
                return chuyenTauRepository.findAll();
        }

        @Override
        public List<ChuyenTau> getChuyenTauByNgayGioKhoiHanh(LocalDateTime ngayGioKhoiHanh) {
                LocalDateTime startOfDay = ngayGioKhoiHanh.toLocalDate().atStartOfDay(); // 00:00
                LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1); // 23:59:59

                return chuyenTauRepository.findAllByNgayGioKhoiHanhBetween(startOfDay, endOfDay);
        }

}
