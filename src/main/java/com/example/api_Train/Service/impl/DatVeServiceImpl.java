package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.Request.DatVeRequest;
import com.example.api_Train.DTO.Request.DatVeRequest.ChiTietDatVeRequest;
import com.example.api_Train.DTO.Request.HanhKhachRequest;
import com.example.api_Train.DTO.Request.NguoiDatVeRequest;
import com.example.api_Train.DTO.Response.DatVeResponse;
import com.example.api_Train.DTO.Response.DatVeResponse.ChiTietVeResponse;
import com.example.api_Train.DTO.Response.HanhKhachResponse;
import com.example.api_Train.DTO.Response.NguoiDatVeResponse;
import com.example.api_Train.DTO.Response.VeTauResponse;
import com.example.api_Train.Exception.NotFound;
import com.example.api_Train.Repository.*;
import com.example.api_Train.Service.interf.DatVeService;
import com.example.api_Train.models.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DatVeServiceImpl implements DatVeService {
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

    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private TauRepository tauRepository;

    @Autowired
    private GheChuyenTauRepository gheChuyenTauRepository;

    @Transactional
    public DatVeResponse datVe(DatVeRequest datVeRequest) {
        try {
            // Validate input data
            if (datVeRequest == null) {
                throw new IllegalArgumentException("Dữ liệu đặt vé không được để trống");
            }

            if (datVeRequest.getNguoiDatVe() == null) {
                throw new IllegalArgumentException("Thông tin người đặt vé không được để trống");
            }

            if (datVeRequest.getChiTietDatVe() == null || datVeRequest.getChiTietDatVe().isEmpty()) {
                throw new IllegalArgumentException("Danh sách vé không được để trống");
            }

            // Kiểm tra và tìm hoặc tạo đối tượng người đặt vé
            NguoiDatVe nguoiDatVe = findOrCreateNguoiDatVe(datVeRequest.getNguoiDatVe());
            logger.info("Đã tìm/tạo người đặt vé: " + nguoiDatVe.getMaNguoiDat());

            BigDecimal tongTien = BigDecimal.ZERO;
            List<VeTau> danhSachVe = new ArrayList<>();

            // Tạo đối tượng đặt vé trước
            DatVe datVe = new DatVe();
            datVe.setMaNguoiDat(nguoiDatVe);
            datVe.setTrangThai("CHỜ THANH TOÁN");
            datVe.setNgayDat(LocalDateTime.now());
            // Khởi tạo tổng tiền = 0 để tránh lỗi null
            datVe.setTongTien(BigDecimal.ZERO);
            datVe = datVeRepository.save(datVe);
            logger.info("Đã tạo đặt vé với mã: " + datVe.getMaDatVe());

            // Lấy thông tin tình trạng vé "Chờ thanh toán"
            TinhTrangVe tinhTrangVe = tinhTrangVeRepository.findById(2) // Giả sử ID 2 là "Chờ thanh toán"
                    .orElseThrow(() -> new NotFound("Không tìm thấy tình trạng vé với id = 2"));

            // Xử lý tạo vé tàu cho từng hành khách
            for (ChiTietDatVeRequest chiTiet : datVeRequest.getChiTietDatVe()) {
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

                Tau tau = tauRepository.findById(chuyenTau.getTau().getMaTau())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy  tàu với mã: " + chiTiet.getVeTau().getMaChuyenTau()));
                // Kiểm tra thông tin ghế
                Ghe ghe = gheRepository.findById(chiTiet.getVeTau().getMaGhe())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy ghế với mã: " + chiTiet.getVeTau().getMaGhe()));
                GheChuyenTau gheChuyenTau = gheChuyenTauRepository.findByChuyenTau_MaChuyenTauAndGhe_MaGhe(
                        chiTiet.getVeTau().getMaChuyenTau(),
                        chiTiet.getVeTau().getMaGhe());
                gheChuyenTau.setTrangThai("ĐANG ĐẶT");
                gheChuyenTauRepository.save(gheChuyenTau);

                // Lấy thông tin giá vé
                BangGia giaVe = giaVeRepository
                        .findByChuyenTau_MaChuyenTauAndLoaiCho_MaLoaiCho(
                                chuyenTau.getMaChuyenTau(),
                                ghe.getToaTau().getLoaiCho().getMaLoaiCho())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy giá vé với mã chuyến tàu: " + chuyenTau.getMaChuyenTau() +
                                        " và mã loại chỗ: " + ghe.getToaTau().getLoaiCho().getMaLoaiCho()));

                // Lấy thông tin đối tượng (loại khách)
                DoiTuong doiTuong = doiTuongRepository.findById(chiTiet.getHanhKhach().getMaLoaiKhach())
                        .orElseThrow(() -> new NotFound(
                                "Không tìm thấy đối tượng với mã: " + chiTiet.getHanhKhach().getMaLoaiKhach()));

                // Tạo đối tượng vé tàu
                VeTau veTau = new VeTau();
                veTau.setChuyenTau(chuyenTau);
                veTau.setHanhKhach(hanhKhach);
                veTau.setTau(tau);
                veTau.setGhe(ghe);
                veTau.setBangGia(giaVe);
                veTau.setDoiTuong(doiTuong);
                veTau.setTinhTrangVe(tinhTrangVe);

                // Lưu vé tàu
                veTau = veTauRepository.save(veTau);
                logger.info("Đã tạo vé tàu với mã: " + veTau.getMaVe());

                // Tính giá vé theo loại khách
                BigDecimal giaVeChiTiet = tinhGiaVeTheoLoaiKhach(giaVe.getGiaTien(),
                        chiTiet.getHanhKhach().getMaLoaiKhach());

                // Tạo thanh toán cho vé
                ThanhToan thanhToan = new ThanhToan();
                thanhToan.setVeTau(veTau);
                thanhToan.setSoTien(giaVeChiTiet);
                thanhToan.setTrangThai("CHỜ THANH TOÁN");
                thanhToan.setPhuongThucThanhToan("VNPay");
                thanhToan.setNgayThanhToan(LocalDateTime.now());
                thanhToan = thanhToanRepository.save(thanhToan);
                logger.info("Đã tạo thanh toán với mã: " + thanhToan.getMaThanhToan() + " cho vé: " + veTau.getMaVe());

                danhSachVe.add(veTau);

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

    private NguoiDatVe findOrCreateNguoiDatVe(NguoiDatVeRequest nguoiDatVeRequest) {
        if (nguoiDatVeRequest == null) {
            throw new IllegalArgumentException("Thông tin người đặt vé không được để trống");
        }

        NguoiDatVe nguoiDatVe = null;
        // Tìm người đặt vé theo CCCD nếu có
        if (nguoiDatVeRequest.getCccd() != null && !nguoiDatVeRequest.getCccd().isEmpty()) {
            nguoiDatVe = nguoiDatVeRepository.findByCccd(nguoiDatVeRequest.getCccd());
        }

        if (nguoiDatVe == null) {
            nguoiDatVe = new NguoiDatVe();
            nguoiDatVe.setHoTen(nguoiDatVeRequest.getHoTen());
            nguoiDatVe.setCccd(nguoiDatVeRequest.getCccd());
            nguoiDatVe.setEmail(nguoiDatVeRequest.getEmail());
            nguoiDatVe.setSoDienThoai(nguoiDatVeRequest.getSoDienThoai());
            nguoiDatVe = nguoiDatVeRepository.save(nguoiDatVe);
        }
        return nguoiDatVe;
    }

    private HanhKhach findOrCreateHanhKhach(HanhKhachRequest hanhKhachRequest) {
        if (hanhKhachRequest == null) {
            throw new IllegalArgumentException("Thông tin hành khách không được để trống");
        }

        HanhKhach hanhKhach = null;
        if (hanhKhachRequest.getSoGiayTo() != null && !hanhKhachRequest.getSoGiayTo().isEmpty()) {
            hanhKhach = hanhKhachRepository.findBySoGiayTo(hanhKhachRequest.getSoGiayTo());
        }

        // Get the DoiTuong (passenger type) first
        DoiTuong loaiKhach = null;
        if (hanhKhachRequest.getMaLoaiKhach() != null) {
            loaiKhach = doiTuongRepository.findById(hanhKhachRequest.getMaLoaiKhach())
                    .orElseThrow(() -> new NotFound(
                            "Không tìm thấy đối tượng với mã: " + hanhKhachRequest.getMaLoaiKhach()));
        }

        if (hanhKhach == null) {
            hanhKhach = new HanhKhach();
            hanhKhach.setHoTen(hanhKhachRequest.getHoTen());
            hanhKhach.setSoGiayTo(hanhKhachRequest.getSoGiayTo());
            hanhKhach.setNgaySinh(hanhKhachRequest.getNgaySinh());

            // Set the loaiKhach field
            if (loaiKhach != null) {
                hanhKhach.setLoaiKhach(loaiKhach);
            }

            hanhKhach = hanhKhachRepository.save(hanhKhach);
        } else if (loaiKhach != null && hanhKhach.getLoaiKhach() == null) {
            // Also update the loaiKhach if the passenger exists but doesn't have a type set
            hanhKhach.setLoaiKhach(loaiKhach);
            hanhKhach = hanhKhachRepository.save(hanhKhach);
        }

        return hanhKhach;
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

    /**
     * Tạo đối tượng DatVeResponse từ đối tượng DatVe và danh sách VeTau
     */
    public static DatVeResponse createDatVeResponse(DatVe datVe, List<VeTau> danhSachVe) {
        if (datVe == null) {
            throw new IllegalArgumentException("Thông tin đặt vé không được để trống");
        }

        // Tạo đối tượng response cho người đặt vé
        NguoiDatVeResponse nguoiDatVeResponse = NguoiDatVeResponse.mapNguoiDatVeResponse(datVe.getMaNguoiDat());

        // Tạo danh sách chi tiết vé
        List<ChiTietVeResponse> chiTietVeList = new ArrayList<>();
        for (VeTau veTau : danhSachVe) {
            // Tạo đối tượng response cho vé tàu
            VeTauResponse veTauResponse = VeTauResponse.fromVeTau(veTau);

            // Tạo đối tượng response cho hành khách
            HanhKhachResponse hanhKhachResponse = HanhKhachResponse.mapHanhKhachResponse(veTau.getHanhKhach());

            // Tạo đối tượng response cho chi tiết vé
            ChiTietVeResponse chiTietVeResponse = new ChiTietVeResponse();
            chiTietVeResponse.setVeTau(veTauResponse);
            chiTietVeResponse.setHanhKhach(hanhKhachResponse);

            chiTietVeList.add(chiTietVeResponse);
        }

        // Tạo đối tượng response cho đặt vé
        DatVeResponse datVeResponse = new DatVeResponse();
        datVeResponse.setMaDatVe(datVe.getMaDatVe());
        datVeResponse.setNgayDatVe(Date.valueOf(datVe.getNgayDat().toLocalDate()));
        datVeResponse.setTrangThai(datVe.getTrangThai());
        datVeResponse.setNguoiDatVe(nguoiDatVeResponse);
        datVeResponse.setChiTietVeList(chiTietVeList);
        datVeResponse.setTongTien(datVe.getTongTien());

        return datVeResponse;
    }
}