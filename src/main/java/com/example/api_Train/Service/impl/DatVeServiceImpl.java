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
     * Tạo chi tiết đặt vé
     * 
     * @param datVe        Đối tượng đặt vé
     * @param veTau        Đối tượng vé tàu
     * @param giaVeChiTiet Giá vé chi tiết
     * @return ChiTietDatVe đã được tạo
     * 
     *         Lấy thông tin đặt vé theo mã đặt vé
     * 
     * @param maDatVe Mã đặt vé
     * @return Thông tin đặt vé
     */

    /**
     * Hủy đặt vé
     * 
     * @param maDatVe Mã đặt vé
     * @return Kết quả hủy đặt vé
     */
    @Transactional
    public DatVeResponse huyDatVe(Integer maDatVe) {
        if (maDatVe == null) {
            throw new IllegalArgumentException("Mã đặt vé không được để trống");
        }

        DatVe datVe = datVeRepository.findById(maDatVe)
                .orElseThrow(() -> new NotFound("Không tìm thấy đặt vé với mã: " + maDatVe));

        // Kiểm tra nếu đặt vé đã bị hủy
        if ("CANCELLED".equals(datVe.getTrangThai())) {
            throw new RuntimeException("Đặt vé này đã bị hủy trước đó");
        }

        // Cập nhật trạng thái đặt vé
        datVe.setTrangThai("CANCELLED");
        datVe = datVeRepository.save(datVe);
        logger.info("Đã hủy đặt vé: " + maDatVe);

        // Cập nhật trạng thái các vé tàu
        List<VeTau> danhSachVe = new ArrayList<>();

        TinhTrangVe tinhTrangHuy = tinhTrangVeRepository.findById(2) // Giả sử id=2 là trạng thái hủy
                .orElseThrow(() -> new NotFound("Không tìm thấy tình trạng vé hủy"));

        DatVeResponse response = new DatVeResponse();
        response.setMaDatVe(datVe.getMaDatVe());
        response.setTrangThai("CANCELLED");

        return response;
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
        // Nếu không tìm thấy, tạo mới
        if (nguoiDatVe == null) {
            nguoiDatVe = new NguoiDatVe();
            nguoiDatVe.setHoTen(nguoiDatVeDTO.getHoTen());
            nguoiDatVe.setCccd(nguoiDatVeDTO.getCccd());
            nguoiDatVe.setEmail(nguoiDatVeDTO.getEmail());
            nguoiDatVe.setSoDienThoai(nguoiDatVeDTO.getSoDienThoai());
            // Lưu đối tượng đặt vé mới
            nguoiDatVe = nguoiDatVeRepository.save(nguoiDatVe);
        }
        return nguoiDatVe;
    }

    /**
     * Tìm hoặc tạo hành khách
     */
    private HanhKhach findOrCreateHanhKhach(HanhKhachDTO hanhKhachDTO) {
        if (hanhKhachDTO == null) {
            throw new IllegalArgumentException("Thông tin hành khách không được để trống");
        }

        HanhKhach hanhKhach = null;
        if (hanhKhachDTO.getSoGiayTo() != null && !hanhKhachDTO.getSoGiayTo().isEmpty()) {
            hanhKhach = hanhKhachRepository.findBySoGiayTo(hanhKhachDTO.getSoGiayTo());
        }

        // Nếu không tìm thấy hành khách, tạo mới
        if (hanhKhach == null) {
            hanhKhach = new HanhKhach();
            hanhKhach.setHoTen(hanhKhachDTO.getHoTen());
            hanhKhach.setSoGiayTo(hanhKhachDTO.getSoGiayTo());
            hanhKhach.setNgaySinh(hanhKhachDTO.getNgaySinh());
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

    /**
     * Tạo response cho đặt vé
     */
    private DatVeResponse createDatVeResponse(DatVe datVe, List<VeTau> danhSachVe) {
        if (datVe == null) {
            throw new IllegalArgumentException("Thông tin đặt vé không được để trống");
        }

        DatVeResponse response = new DatVeResponse();
        response.setMaDatVe(datVe.getMaDatVe());
        response.setTrangThai(datVe.getTrangThai());

        // Thêm thông tin về người đặt vé nếu có

        return response;
    }

    /**
     * Tính giá vé theo loại khách hàng
     * 
     * @param giaTien   Giá vé gốc
     * @param loaiKhach Loại khách hàng
     * @return Giá vé sau khi tính toán
     */
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
                return giaTien; // Giá gốc cho người lớn và các trường hợp khác
        }
    }
}