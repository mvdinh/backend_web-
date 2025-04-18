package com.example.api_Train.Service.impl;

import com.example.api_Train.DTO.RequestDTO.DatVe.*;
import com.example.api_Train.DTO.ResponseDTO.DatVeResponseDTO;
import com.example.api_Train.entity.*;
import com.example.api_Train.exception.ResourceNotFoundException;
import com.example.api_Train.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DatVeService {

    @Autowired
    private DatVeRepository datVeRepository;

    @Autowired
    private HanhKhachRepository hanhKhachRepository;

    @Autowired
    private VeTauRepository veTauRepository;

    @Autowired
    private ChuyenTauRepository chuyenTauRepository;

    @Autowired
    private GheRepository gheRepository;

    @Autowired
    private GiaVeRepository giaVeRepository;

    /**
     * Đặt vé tàu cho một hoặc nhiều hành khách
     * 
     * @param datVeDTO thông tin đặt vé
     * @return thông tin đặt vé đã được xử lý
     */
    @Transactional
    public DatVeResponseDTO datVe(DatVeDTO datVeDTO) {
        try {
            // Tạo đối tượng đặt vé
            DatVe datVe = new DatVe();
            datVe.setMaDatVe(UUID.randomUUID().toString());
            datVe.setHoTen(datVeDTO.getNguoiDatVeDTO().getHoTen());
            datVe.setCccd(datVeDTO.getNguoiDatVeDTO().getCccd());
            datVe.setEmail(datVeDTO.getNguoiDatVeDTO().getEmail());
            datVe.setSoDienThoai(datVeDTO.getNguoiDatVeDTO().getSoDienThoai());
            datVe.setNgayDat(new Date());
            datVe.setTrangThai("CREATED");

            // Lưu đối tượng đặt vé
            datVe = datVeRepository.save(datVe);

            // Tính tổng tiền
            BigDecimal tongTien = BigDecimal.ZERO;
            List<VeTau> danhSachVe = new ArrayList<>();

            // Xử lý chi tiết đặt vé cho từng hành khách
            for (ChiTietDatVeDTO chiTiet : datVeDTO.getChiTietDatVeDTOs()) {
                // Tạo đối tượng hành khách
                HanhKhach hanhKhach = new HanhKhach();
                hanhKhach.setHoTen(chiTiet.getHanhKhach().getHoTen());
                hanhKhach.setSoGiayTo(chiTiet.getHanhKhach().getSoGiayTo());
                hanhKhach.setNgaySinh(chiTiet.getHanhKhach().getNgaySinh());
                hanhKhach.setLoaiKhach(chiTiet.getHanhKhach().getLoaiKhach());
                hanhKhach.setDatVe(datVe);

                // Lưu hành khách
                hanhKhach = hanhKhachRepository.save(hanhKhach);

                // Kiểm tra thông tin chuyến tàu
                ChuyenTau chuyenTau = chuyenTauRepository.findById(chiTiet.getVeTau().getMaChuyenTau())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Không tìm thấy chuyến tàu với mã: " + chiTiet.getVeTau().getMaChuyenTau()));

                // Kiểm tra thông tin ghế
                Ghe ghe = gheRepository.findById(chiTiet.getVeTau().getMaGhe())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Không tìm thấy ghế với mã: " + chiTiet.getVeTau().getMaGhe()));

                // Kiểm tra ghế đã được đặt chưa
                if (veTauRepository.checkGheDaDat(chiTiet.getVeTau().getMaChuyenTau(), chiTiet.getVeTau().getMaGhe())) {
                    throw new RuntimeException("Ghế " + chiTiet.getVeTau().getMaGhe() + " đã được đặt cho chuyến tàu "
                            + chiTiet.getVeTau().getMaChuyenTau());
                }

                // Lấy thông tin giá vé
                GiaVe giaVe = giaVeRepository.findById(chiTiet.getVeTau().getMaGia())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Không tìm thấy giá vé với mã: " + chiTiet.getVeTau().getMaGia()));

                // Tính giá vé theo loại khách
                BigDecimal giaVeChiTiet = tinhGiaVeTheoLoaiKhach(giaVe.getGiaVe(),
                        chiTiet.getHanhKhach().getLoaiKhach());

                // Tạo đối tượng vé tàu
                VeTau veTau = new VeTau();
                veTau.setMaKhachHang(chiTiet.getVeTau().getMaKhachHang());
                veTau.setMaLoaiKhach(chiTiet.getVeTau().getMaLoaiKhach());
                veTau.setMaChuyenTau(chiTiet.getVeTau().getMaChuyenTau());
                veTau.setMaGhe(chiTiet.getVeTau().getMaGhe());
                veTau.setMaGia(chiTiet.getVeTau().getMaGia());
                veTau.setGiaVe(giaVeChiTiet);
                veTau.setTrangThai("CONFIRMED");
                veTau.setHanhKhach(hanhKhach);
                veTau.setDatVe(datVe);

                // Lưu vé tàu
                veTau = veTauRepository.save(veTau);
                danhSachVe.add(veTau);

                // Cộng vào tổng tiền
                tongTien = tongTien.add(giaVeChiTiet);
            }

            // Cập nhật trạng thái đặt vé
            datVe.setTongTien(tongTien);
            datVe.setTrangThai("CONFIRMED");
            datVe = datVeRepository.save(datVe);

            // Tạo response
            DatVeResponseDTO response = new DatVeResponseDTO();
            response.setMaDatVe(datVe.getMaDatVe());
            response.setTrangThai("SUCCESS");
            response.setThongBao("Đặt vé thành công");
            response.setTongTien(tongTien);

            return response;

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi trong quá trình đặt vé: " + e.getMessage(), e);
        }
    }

    /**
     * Tính giá vé theo loại khách
     * 
     * @param giaGoc    giá vé gốc
     * @param loaiKhach loại khách hàng
     * @return giá vé sau khi tính toán
     */
    private BigDecimal tinhGiaVeTheoLoaiKhach(BigDecimal giaGoc, DoiTuong loaiKhach) {
        // Áp dụng giảm giá theo loại khách
        switch (loaiKhach) {
            case NGUOI_LON:
                return giaGoc; // 100% giá
            case TRE_EM:
                return giaGoc.multiply(new BigDecimal("0.5")); // 50% giá
            case NGUOI_CAO_TUOI:
                return giaGoc.multiply(new BigDecimal("0.7")); // 70% giá
            case NGUOI_KHUYET_TAT:
                return giaGoc.multiply(new BigDecimal("0.65")); // 65% giá
            default:
                return giaGoc;
        }
    }

    /**
     * Lấy thông tin đặt vé theo mã
     * 
     * @param maDatVe mã đặt vé
     * @return thông tin đặt vé
     */
    public DatVeResponseDTO getThongTinDatVe(String maDatVe) {
        DatVe datVe = datVeRepository.findById(maDatVe)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin đặt vé với mã: " + maDatVe));

        DatVeResponseDTO response = new DatVeResponseDTO();
        response.setMaDatVe(datVe.getMaDatVe());
        response.setTrangThai(datVe.getTrangThai());
        response.setThongBao("Thông tin đặt vé");
        response.setTongTien(datVe.getTongTien());

        return response;
    }

    /**
     * Hủy đặt vé
     * 
     * @param maDatVe mã đặt vé
     * @return thông tin sau khi hủy
     */
    @Transactional
    public DatVeResponseDTO huyDatVe(String maDatVe) {
        DatVe datVe = datVeRepository.findById(maDatVe)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thông tin đặt vé với mã: " + maDatVe));

        // Kiểm tra có thể hủy không (ví dụ: chỉ hủy được trước khi tàu khởi hành)
        List<VeTau> danhSachVe = veTauRepository.findByDatVeId(maDatVe);
        for (VeTau veTau : danhSachVe) {
            ChuyenTau chuyenTau = chuyenTauRepository.findById(veTau.getMaChuyenTau())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Không tìm thấy chuyến tàu với mã: " + veTau.getMaChuyenTau()));

            Date now = new Date();
            if (now.after(chuyenTau.getThoiGianXuatPhat())) {
                throw new RuntimeException("Không thể hủy vé cho chuyến tàu đã khởi hành");
            }

            // Cập nhật trạng thái vé
            veTau.setTrangThai("CANCELLED");
            veTauRepository.save(veTau);
        }

        // Cập nhật trạng thái đặt vé
        datVe.setTrangThai("CANCELLED");
        datVeRepository.save(datVe);

        // Tạo response
        DatVeResponseDTO response = new DatVeResponseDTO();
        response.setMaDatVe(datVe.getMaDatVe());
        response.setTrangThai("SUCCESS");
        response.setThongBao("Hủy đặt vé thành công");
        response.setTongTien(datVe.getTongTien());

        return response;
    }

    /**
     * Thay đổi thông tin hành khách
     * 
     * @param maHanhKhach  mã hành khách
     * @param hanhKhachDTO thông tin hành khách mới
     * @return thông tin sau khi cập nhật
     */
    @Transactional
    public HanhKhach capNhatThongTinHanhKhach(Long maHanhKhach, HanhKhachDTO hanhKhachDTO) {
        HanhKhach hanhKhach = hanhKhachRepository.findById(maHanhKhach)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hành khách với mã: " + maHanhKhach));

        hanhKhach.setHoTen(hanhKhachDTO.getHoTen());
        hanhKhach.setSoGiayTo(hanhKhachDTO.getSoGiayTo());
        hanhKhach.setNgaySinh(hanhKhachDTO.getNgaySinh());
        hanhKhach.setLoaiKhach(hanhKhachDTO.getLoaiKhach());

        return hanhKhachRepository.save(hanhKhach);
    }
}