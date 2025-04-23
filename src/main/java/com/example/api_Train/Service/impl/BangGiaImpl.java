package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.BangGiaRequest;
import com.example.api_Train.Repository.BangGiaRepository;
import com.example.api_Train.Repository.ChuyenTauRepository;
import com.example.api_Train.Repository.TauRepository;
import com.example.api_Train.Repository.LoaiChoRepository;
import com.example.api_Train.Service.interf.BangGiaService;
import com.example.api_Train.models.BangGia;
import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.LoaiCho;
import com.example.api_Train.models.Tau;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BangGiaImpl implements BangGiaService {

        private final BangGiaRepository bangGiaRepository;
        private final TauRepository tauRepository;
        private final ChuyenTauRepository chuyenTauRepository;
        private final LoaiChoRepository loaiChoRepository;

        public BangGiaImpl(
                        BangGiaRepository bangGiaRepository,
                        TauRepository tauRepository,
                        ChuyenTauRepository chuyenTauRepository,
                        LoaiChoRepository loaiChoRepository) {
                this.bangGiaRepository = bangGiaRepository;
                this.tauRepository = tauRepository;
                this.chuyenTauRepository = chuyenTauRepository;
                this.loaiChoRepository = loaiChoRepository;
        }

        @Override
        @Transactional
        public BangGia createBangGia(BangGiaRequest request) {
                Tau tau = tauRepository.findById(request.getMaTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tàu"));

                ChuyenTau chuyenTau = chuyenTauRepository.findById(request.getMaChuyenTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Chuyến Tàu"));

                LoaiCho loaiCho = loaiChoRepository.findById(request.getMaLoaiCho())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Loại Chỗ"));

                BangGia bangGia = BangGia.builder()
                                .tau(tau)
                                .chuyenTau(chuyenTau)
                                .loaiCho(loaiCho)
                                .giaTien(request.getGiaTien())
                                .build();

                return bangGiaRepository.save(bangGia);
        }

        @Override
        @Transactional
        public BangGia updateBangGia(Integer maBangGia, BangGiaRequest request) {
                BangGia existing = bangGiaRepository.findById(maBangGia)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng giá"));

                Tau tau = tauRepository.findById(request.getMaTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tàu"));

                ChuyenTau chuyenTau = chuyenTauRepository.findById(request.getMaChuyenTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Chuyến Tàu"));

                LoaiCho loaiCho = loaiChoRepository.findById(request.getMaLoaiCho())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Loại Chỗ"));

                existing.setTau(tau);
                existing.setChuyenTau(chuyenTau);
                existing.setLoaiCho(loaiCho);
                existing.setGiaTien(request.getGiaTien());

                return bangGiaRepository.save(existing);
        }

        @Override
        public BangGiaRequest getBangGiaById(Integer maBangGia) {
                return bangGiaRepository.findById(maBangGia)
                                .map(bangGia -> BangGiaRequest.builder()
                                                .maTau(bangGia.getTau().getMaTau())
                                                .maChuyenTau(bangGia.getChuyenTau().getMaChuyenTau())
                                                .maLoaiCho(bangGia.getLoaiCho().getMaLoaiCho())
                                                .giaTien(bangGia.getGiaTien())
                                                .build())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng giá"));
        }

        @Override
        public void deleteBangGia(Integer maBangGia) {
                if (!bangGiaRepository.existsById(maBangGia)) {
                        throw new RuntimeException("Không tìm thấy bảng giá để xoá");
                }
                bangGiaRepository.deleteById(maBangGia);
        }
}
