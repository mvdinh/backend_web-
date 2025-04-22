package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.ChuyenTauRequest;
import com.example.api_Train.DTO.Response.ChuyenTauResponse;
import com.example.api_Train.Repository.*;
import com.example.api_Train.Service.interf.ChuyenTauService;
import com.example.api_Train.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChuyenTauImpl implements ChuyenTauService {

        private final ChuyenTauRepository chuyenTauRepo;
        private final TauRepository tauRepo;
        private final TuyenDuongRepository tuyenDuongRepo;
        private final BangGiaRepository bangGiaRepo;
        private final LoaiChoRepository loaiChoRepo;

        private static final Logger logger = LoggerFactory.getLogger(ChuyenTauImpl.class);

        @Override
        @Transactional
        public ChuyenTauResponse themChuyenTau(ChuyenTauRequest request) {
                try {
                        ChuyenTau chuyenTau = ChuyenTau.builder()
                                        .tau(tauRepo.findById(request.getMaTau())
                                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy tàu.")))
                                        .tuyenDuong(tuyenDuongRepo.findById(request.getMaTuyen()).orElseThrow(
                                                        () -> new RuntimeException("Không tìm thấy tuyến đường.")))
                                        .ngayGioKhoiHanh(request.getNgayGioKhoiHanh())
                                        .build();

                        ChuyenTau savedChuyenTau = chuyenTauRepo.save(chuyenTau);

                        List<BangGia> bangGiaList = request.getDanhSachBangGia().stream().map(bgReq -> BangGia.builder()
                                        .chuyenTau(savedChuyenTau)
                                        .tau(savedChuyenTau.getTau())
                                        .loaiCho(loaiChoRepo.findById(bgReq.getMaLoaiCho()).orElseThrow(
                                                        () -> new RuntimeException("Không tìm thấy loại chỗ.")))
                                        .giaTien(bgReq.getGiaTien())
                                        .build()).collect(Collectors.toList());

                        bangGiaRepo.saveAll(bangGiaList);

                        chuyenTau.setDanhSachBangGia(bangGiaList);

                        return ChuyenTauResponse.mapChuyenTauResponse(chuyenTau);
                } catch (Exception e) {
                        logger.error("Lỗi khi thêm chuyến tàu: ", e);
                        throw new RuntimeException("Lỗi khi thêm chuyến tàu: " + e.getMessage());
                }
        }

        @Override
        @Transactional
        public ChuyenTauResponse suaChuyenTau(Integer id, ChuyenTauRequest request) {
                try {
                        ChuyenTau chuyenTau = chuyenTauRepo.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến tàu"));

                        chuyenTau.setTau(tauRepo.findById(request.getMaTau())
                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy tàu.")));
                        chuyenTau.setTuyenDuong(tuyenDuongRepo.findById(request.getMaTuyen())
                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy tuyến đường.")));
                        chuyenTau.setNgayGioKhoiHanh(request.getNgayGioKhoiHanh());

                        bangGiaRepo.deleteAllByChuyenTau_MaChuyenTau(id);

                        List<BangGia> bangGiaList = request.getDanhSachBangGia().stream().map(bgReq -> BangGia.builder()
                                        .chuyenTau(chuyenTau)
                                        .tau(chuyenTau.getTau())
                                        .loaiCho(loaiChoRepo.findById(bgReq.getMaLoaiCho()).orElseThrow(
                                                        () -> new RuntimeException("Không tìm thấy loại chỗ.")))
                                        .giaTien(bgReq.getGiaTien())
                                        .build()).collect(Collectors.toList());

                        bangGiaRepo.saveAll(bangGiaList);
                        chuyenTau.setDanhSachBangGia(bangGiaList);

                        return ChuyenTauResponse.mapChuyenTauResponse(chuyenTauRepo.save(chuyenTau));
                } catch (Exception e) {
                        logger.error("Lỗi khi sửa chuyến tàu với ID {}: ", id, e);
                        throw new RuntimeException("Lỗi khi sửa chuyến tàu: " + e.getMessage());
                }
        }

        @Override
        @Transactional
        public void xoaChuyenTau(Integer id) {
                try {
                        if (!chuyenTauRepo.existsById(id)) {
                                throw new RuntimeException("Không tìm thấy chuyến tàu.");
                        }

                        bangGiaRepo.deleteAllByChuyenTau_MaChuyenTau(id);
                        chuyenTauRepo.deleteById(id);

                } catch (Exception e) {
                        logger.error("Lỗi khi xóa chuyến tàu với ID {}: ", id, e);
                        throw new RuntimeException("Lỗi khi xóa chuyến tàu: " + e.getMessage());
                }
        }

        @Override
        public ChuyenTauResponse layChiTietChuyenTau(Integer id) {
                try {
                        return ChuyenTauResponse.mapChuyenTauResponse(chuyenTauRepo.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến tàu")));
                } catch (Exception e) {
                        logger.error("Lỗi khi lấy chi tiết chuyến tàu với ID {}: ", id, e);
                        throw new RuntimeException("Lỗi khi lấy chi tiết chuyến tàu: " + e.getMessage());
                }
        }

        @Override
        public List<ChuyenTauResponse> layTatCaChuyenTau() {
                try {
                        return chuyenTauRepo.findAll().stream()
                                        .map(ChuyenTauResponse::mapChuyenTauResponse)
                                        .collect(Collectors.toList());
                } catch (Exception e) {
                        logger.error("Lỗi khi lấy tất cả chuyến tàu: ", e);
                        throw new RuntimeException("Lỗi khi lấy tất cả chuyến tàu: " + e.getMessage());
                }
        }
}
