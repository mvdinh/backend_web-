package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.BangGiaDTO;
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

        // Constructor injection
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
        public BangGia createBangGia(BangGiaDTO dto) {
                // Tìm tàu từ repository
                Tau tau = tauRepository.findById(dto.getMaTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tàu"));

                // Tìm chuyến tàu từ repository
                ChuyenTau chuyenTau = chuyenTauRepository.findById(dto.getMaChuyenTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Chuyến Tàu"));

                // Tìm loại chỗ từ repository
                LoaiCho loaiCho = loaiChoRepository.findById(dto.getMaLoaiCho())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Loại Chỗ"));

                // Tạo đối tượng BangGia
                BangGia bangGia = BangGia.builder()
                                .tau(tau)
                                .chuyenTau(chuyenTau)
                                .loaiCho(loaiCho)
                                .giaTien(dto.getGiaTien())
                                .build();

                // Lưu bảng giá vào repository và trả về đối tượng đã lưu
                return bangGiaRepository.save(bangGia);
        }

        @Override
        @Transactional
        public BangGia updateBangGia(Integer maBangGia, BangGiaDTO dto) {
                // Tìm bảng giá cần cập nhật
                BangGia existing = bangGiaRepository.findById(maBangGia)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng giá"));

                // Tìm tàu từ repository
                Tau tau = tauRepository.findById(dto.getMaTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Tàu"));

                // Tìm chuyến tàu từ repository
                ChuyenTau chuyenTau = chuyenTauRepository.findById(dto.getMaChuyenTau())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Chuyến Tàu"));

                // Tìm loại chỗ từ repository
                LoaiCho loaiCho = loaiChoRepository.findById(dto.getMaLoaiCho())
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy Loại Chỗ"));

                // Cập nhật thông tin bảng giá
                existing.setTau(tau);
                existing.setChuyenTau(chuyenTau);
                existing.setLoaiCho(loaiCho);
                existing.setGiaTien(dto.getGiaTien());

                // Lưu và trả về bảng giá đã được cập nhật
                return bangGiaRepository.save(existing);
        }

        @Override
        public BangGia getBangGiaById(Integer maBangGia) {
                // Lấy bảng giá theo ID, nếu không tìm thấy sẽ ném ngoại lệ
                return bangGiaRepository.findById(maBangGia)
                                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng giá"));
        }

        @Override
        public void deleteBangGia(Integer maBangGia) {
                // Kiểm tra nếu bảng giá không tồn tại để xoá
                if (!bangGiaRepository.existsById(maBangGia)) {
                        throw new RuntimeException("Không tìm thấy bảng giá để xoá");
                }

                // Xoá bảng giá theo ID
                bangGiaRepository.deleteById(maBangGia);
        }
}
