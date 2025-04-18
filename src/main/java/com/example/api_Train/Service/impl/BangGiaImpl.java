package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.RequestDTO.BangGiaDTO;
import com.example.api_Train.Repository.BangGiaRepository;
import com.example.api_Train.Repository.ChuyenTauRepository;
import com.example.api_Train.Repository.TauRepository;
import com.example.api_Train.Service.interf.BangGiaService;
import com.example.api_Train.models.BangGia;
import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.LoaiCho;
import com.example.api_Train.models.Tau;
import org.springframework.stereotype.Service;

@Service
public class BangGiaImpl implements BangGiaService {

    private final BangGiaRepository bangGiaRepository;
    private final TauRepository tauRepository;
    private final ChuyenTauRepository chuyenTauRepository;

    public BangGiaImpl(
            BangGiaRepository bangGiaRepository,
            TauRepository tauRepository,
            ChuyenTauRepository chuyenTauRepository) {
        this.bangGiaRepository = bangGiaRepository;
        this.tauRepository = tauRepository;
        this.chuyenTauRepository = chuyenTauRepository;
    }

    @Override
    public BangGia createBangGia(BangGiaDTO dto) {
        Tau tau = tauRepository.findById(dto.getMaTau())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tàu"));

        ChuyenTau chuyenTau = chuyenTauRepository.findById(dto.getMaChuyenTau())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Chuyến Tàu"));

        LoaiCho loaiCho = new LoaiCho(); // Nếu có LoaiChoRepository thì nên findById
        loaiCho.setMaLoaiCho(dto.getMaLoaiCho());

        BangGia bangGia = BangGia.builder()
                .tau(tau)
                .chuyenTau(chuyenTau)
                .loaiCho(loaiCho)
                .giaTien(dto.getGiaTien())
                .build();

        return bangGiaRepository.save(bangGia);
    }

    @Override
    public BangGia updateBangGia(Integer maBangGia, BangGiaDTO dto) {
        BangGia existing = bangGiaRepository.findById(maBangGia)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng giá"));

        Tau tau = tauRepository.findById(dto.getMaTau())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tàu"));

        ChuyenTau chuyenTau = chuyenTauRepository.findById(dto.getMaChuyenTau())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Chuyến Tàu"));

        LoaiCho loaiCho = new LoaiCho();
        loaiCho.setMaLoaiCho(dto.getMaLoaiCho());

        existing.setTau(tau);
        existing.setChuyenTau(chuyenTau);
        existing.setLoaiCho(loaiCho);
        existing.setGiaTien(dto.getGiaTien());

        return bangGiaRepository.save(existing);
    }

    @Override
    public BangGia getBangGiaById(Integer maBangGia) {
        return bangGiaRepository.findById(maBangGia)
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