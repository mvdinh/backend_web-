package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.RequestDTO.DatVe.*;
import com.example.api_Train.DTO.Response.DatVeTau.DatVeResponse;
import com.example.api_Train.Exception.NotFound;
import com.example.api_Train.Repository.*;
import com.example.api_Train.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class DatVeServiceImpl {
    private static final Logger logger = Logger.getLogger(DatVeServiceImpl.class.getName());

    @Autowired
    private DatVeRepository datVeRepository;

    @Autowired
    private HanhKhachRepository hanhKhachRepository;

    @Autowired
    private ChuyenTauRepository chuyenTauRepository;

    @Autowired
    private GheRepository gheRepository;

    @Autowired
    private VeTauRepository veTauRepository;

    @Autowired
    private BangGiaRepository giaVeRepository;

    @Autowired
    private NguoiDatVeRepository nguoiDatVeRepository;

    @Autowired
    private TinhTrangVeRepository tinhTrangVeRepository;

    @Autowired
    private DoiTuongRepository doiTuongRepository;

    /**
     * Đặt vé tàu
     * 
     * @param datVeDTO Thông tin đặt vé
     * @return Kết quả đặt vé
     */
    @Transactional
    public DatVeResponse datVe(DatVeDTO datVeDTO) {
        try {
            // Validate input data
            if (datVeDTO == null) {
                throw new IllegalArgumentException("Dữ liệu đặt vé không được để trống");
            }

            if (datVeDTO.getNguoiDatVeDTO() == null) {
                throw new IllegalArgumentException("Thông tin người đặt vé không được để trống");
            }

            if (datVeDTO.getChiTietDatVeDTOs() == null || datVeDTO.getChiTietDatVeDTOs().isEmpty()) {
                throw new IllegalArgumentException("Danh sách vé không được để trống");
            }

            // Kiểm tra và tìm hoặc tạo đối tượng người đặt vé
            NguoiDatVe nguoiDatVe = findOrCreateNguoiDatVe(datVeDTO.getNguoiDatVeDTO());
            logger.info("Đã tìm/tạo người đặt vé: " + nguoiDatVe.getMaNguoiDat());

            BigDecimal tongTien = BigDecimal.ZERO;
            List<VeTau> danhSachVe = new ArrayList<>();

            // Tạo đối tượng đặt vé trước
            DatVe datVe = new DatVe();
            datVe.setMaNguoiDat(nguoiDatVe);
            datVe.setTrangThai("CONFIRMED");
            datVe.setNgayDat(LocalDateTime.now());
            // Khởi tạo tổng tiền = 0 để tránh lỗi null
            datVe.setTongTien(BigDecimal.ZERO);
            datVe = datVeRepository.save(datVe);
            logger.info("Đã tạo đặt vé với mã: " + datVe.getMaDatVe());

            // Lấy thông tin tình trạng vé "Đã đặt"
            TinhTrangVe tinhTrangVe = tinhTrangVeRepository.findById(1)
                    .orElseThrow(() -> new NotFound("Không tìm thấy tình trạng vé với id = 1"));

            // Xử lý tạo vé tàu cho từng hành khách
            for (ChiTietDatVeDTO chiTiet : datVeDTO.getChiTietDatVeDTOs()) {
                // Validate chi tiết đặt vé
                if (chiTiet.getHanhKhach() == null) {
                    throw new IllegalArgumentException("Thông tin hành khách không được để trống");
                }

                if (chiTiet.getVeTau() == null) {
                    throw new IllegalArgumentException("Thông tin vé tàu không được để trống");
                }

                if (chiTiet.getVeTau().getMaChuyenTau() == null) {
                    throw new IllegalArgumentException("Mã chuyến tàu không được để trống");
                }

                if (chiTiet.getVeTau().getMaGhe() == null) {
                    throw new IllegalArgumentException("Mã ghế không được để trống");
                }

                // Tìm hoặc tạo hành khách
                HanhKhach hanhKhach = findOrCreateHanhKhach(chiTiet.getHanhKhach());

                // Kiểm tra thông tin chuyến tàu
                ChuyenTau chuyenTau = chuyenTauRepository.findById(chiTiet.getVeTau().getMaChuyenTau())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy chuyến tàu với mã: " + chiTiet.getVeTau().getMaChuyenTau()));

                // Kiểm tra thông tin ghế
                Ghe ghe = gheRepository.findById(chiTiet.getVeTau().getMaGhe())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy ghế với mã: " + chiTiet.getVeTau().getMaGhe()));

                // Lấy thông tin giá vé
                BangGia giaVe = giaVeRepository
                        .findByChuyenTau_MaChuyenTauAndLoaiCho_MaLoaiCho(
                                chuyenTau.getMaChuyenTau(),
                                ghe.getToaTau().getLoaiCho().getMaLoaiCho())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy giá vé với mã chuyến tàu: " + chuyenTau.getMaChuyenTau() +
                                        " và mã loại chỗ: " + ghe.getToaTau().getLoaiCho().getMaLoaiCho()));

                // Tính giá vé theo loại khách
                BigDecimal giaVeChiTiet = tinhGiaVeTheoLoaiKhach(giaVe.getGiaTien(),
                        chiTiet.getHanhKhach().getMaloaiKhach());

                // Tạo đối tượng vé tàu
                VeTau veTau = createVeTau(hanhKhach, chuyenTau, ghe, giaVe, tinhTrangVe);

                // Liên kết vé tàu với đơn đặt vé
                veTau = veTauRepository.save(veTau);

                danhSachVe.add(veTau);
                logger.info("Đã tạo vé tàu với mã: " + veTau.getMaVe());

                // Cộng vào tổng tiền
                tongTien = tongTien.add(giaVeChiTiet);
            }

            // Cập nhật tổng tiền cho đặt vé
            datVe.setTongTien(tongTien);
            datVe = datVeRepository.save(datVe);
            logger.info("Đã cập nhật tổng tiền cho đặt vé: " + datVe.getMaDatVe() + " = " + tongTien);

            // Tạo response
            DatVeResponse response = createDatVeResponse(datVe, danhSachVe);
            logger.info("Hoàn thành đặt vé: " + response.getMaDatVe());
            return response;

        } catch (NotFound e) {
            logger.severe("Lỗi không tìm thấy: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.severe("Lỗi dữ liệu đầu vào: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Lỗi trong quá trình đặt vé: " + e.getMessage());
            throw new RuntimeException("Lỗi trong quá trình đặt vé: " + e.getMessage(), e);
        }
    }

    /**
     * Tạo response cho đặt vé
     */
    private DatVeResponse createDatVeResponse(DatVe datVe, List<VeTau> danhSachVe) {
        if (datVe == null) {
            throw new IllegalArgumentException("Thông tin đặt vé không được để trống");
        }

        // Map thông tin người đặt vé
        DatVeResponse.NguoiDatVeResponse nguoiDatVeResponse = DatVeResponse.NguoiDatVeResponse
                .mapNguoiDatVeResponse(datVe.getMaNguoiDat());

        // Map danh sách vé tàu
        List<DatVeResponse.ChiTietVeResponse> chiTietVeList = new ArrayList<>();
        for (VeTau veTau : danhSachVe) {
            DatVeResponse.VeTauResponse veTauResponse = DatVeResponse.VeTauResponse.fromVeTau(veTau);
            // Đảm bảo set giá vé vì phương thức fromVeTau trong class ví dụ không set giá
            // vé
            veTauResponse.setGiaVe(veTau.getBangGia().getGiaTien());

            DatVeResponse.HanhKhachResponse hanhKhachResponse = DatVeResponse.HanhKhachResponse
                    .mapHanhKhachResponse(veTau.getHanhKhach());

            DatVeResponse.ChiTietVeResponse chiTietVeResponse = DatVeResponse.ChiTietVeResponse.builder()
                    .veTau(veTauResponse)
                    .hanhKhach(hanhKhachResponse)
                    .build();

            chiTietVeList.add(chiTietVeResponse);
        }

        // Tạo response
        return DatVeResponse.builder()
                .maDatVe(datVe.getMaDatVe())
                .ngayDatVe(new java.sql.Date(datVe.getNgayDat().toLocalDate().toEpochDay() * 86400000L)) // Chuyển
                                                                                                         // LocalDateTime
                                                                                                         // sang
                                                                                                         // java.sql.Date
                .trangThai(datVe.getTrangThai())
                .nguoiDatVe(nguoiDatVeResponse)
                .chiTietVeList(chiTietVeList)
                .tongTien(datVe.getTongTien())
                .build();
    }

    /**
     * Tìm hoặc tạo người đặt vé
     */
    private NguoiDatVe findOrCreateNguoiDatVe(NguoiDatVeDTO nguoiDatVeDTO) {
        if (nguoiDatVeDTO == null) {
            throw new IllegalArgumentException("Thông tin người đặt vé không được để trống");
        }

        NguoiDatVe nguoiDatVe = null;
        // Tìm người đặt vé theo CCCD nếu có
        if (nguoiDatVeDTO.getCccd() != null && !nguoiDatVeDTO.getCccd().isEmpty()) {
            nguoiDatVe = nguoiDatVeRepository.findByCccd(nguoiDatVeDTO.getCccd());
        }

        if (nguoiDatVe == null) {
            nguoiDatVe = new NguoiDatVe();
            nguoiDatVe.setHoTen(nguoiDatVeDTO.getHoTen());
            nguoiDatVe.setCccd(nguoiDatVeDTO.getCccd());
            nguoiDatVe.setEmail(nguoiDatVeDTO.getEmail());
            nguoiDatVe.setSoDienThoai(nguoiDatVeDTO.getSoDienThoai());
            nguoiDatVe = nguoiDatVeRepository.save(nguoiDatVe);
        }
        return nguoiDatVe;
    }

    private HanhKhach findOrCreateHanhKhach(HanhKhachDTO hanhKhachDTO) {
        if (hanhKhachDTO == null) {
            throw new IllegalArgumentException("Thông tin hành khách không được để trống");
        }
        DoiTuong doiTuong = doiTuongRepository.findById(hanhKhachDTO.getMaloaiKhach())
                .orElseThrow(() -> new NotFound("Không tìm thấy đối tượng với mã: " + hanhKhachDTO.getMaloaiKhach()));

        HanhKhach hanhKhach = null;
        if (hanhKhachDTO.getSoGiayTo() != null && !hanhKhachDTO.getSoGiayTo().isEmpty()) {
            hanhKhach = hanhKhachRepository.findBySoGiayTo(hanhKhachDTO.getSoGiayTo());
        }

        if (hanhKhach == null) {
            hanhKhach = new HanhKhach();
            hanhKhach.setHoTen(hanhKhachDTO.getHoTen());
            hanhKhach.setSoGiayTo(hanhKhachDTO.getSoGiayTo());
            hanhKhach.setNgaySinh(hanhKhachDTO.getNgaySinh());
            hanhKhach.setLoaiKhach(doiTuong);
            hanhKhach = hanhKhachRepository.save(hanhKhach);
        }
        return hanhKhach;
    }

    /**
     * Tạo đối tượng vé tàu
     */
    private VeTau createVeTau(HanhKhach hanhKhach, ChuyenTau chuyenTau, Ghe ghe, BangGia giaVe,
            TinhTrangVe tinhTrangVe) {
        if (hanhKhach == null || chuyenTau == null || ghe == null || giaVe == null || tinhTrangVe == null) {
            throw new IllegalArgumentException("Thông tin tạo vé tàu không đầy đủ");
        }

        VeTau veTau = new VeTau();
        veTau.setHanhKhach(hanhKhach);
        veTau.setChuyenTau(chuyenTau);
        veTau.setGhe(ghe);
        veTau.setBangGia(giaVe);
        veTau.setTinhTrangVe(tinhTrangVe);

        // Lưu vé tàu
        return veTauRepository.save(veTau);
    }

    private BigDecimal tinhGiaVeTheoLoaiKhach(BigDecimal giaTien, Integer maLoaiKhach) {
        if (giaTien == null) {
            throw new IllegalArgumentException("Giá tiền không được để trống");
        }

        if (maLoaiKhach == null) {
            return giaTien;
        }

        switch (maLoaiKhach) {
            case 1:
                return giaTien.multiply(new BigDecimal("0.5")); // Giảm 50% cho trẻ em
            case 2:
                return giaTien.multiply(new BigDecimal("0.7")); // Giảm 30% cho người cao tuổi
            case 3:
                return giaTien.multiply(new BigDecimal("0.8")); // Giảm 20% cho sinh viên
            default:
                return giaTien;
        }
    }
}