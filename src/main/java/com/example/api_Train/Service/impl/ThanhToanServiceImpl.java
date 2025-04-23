package com.example.api_Train.Service.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api_Train.DTO.Response.DatVeResponse;
import com.example.api_Train.DTO.Response.DatVeResponse.ChiTietVeResponse;
import com.example.api_Train.DTO.Response.HanhKhachResponse;
import com.example.api_Train.DTO.Response.NguoiDatVeResponse;
import com.example.api_Train.DTO.Response.VeTauResponse;
import com.example.api_Train.Exception.NotFound;
import com.example.api_Train.Repository.ChiTietDatVeRepository;
import com.example.api_Train.Repository.DatVeRepository;
import com.example.api_Train.Repository.GheChuyenTauRepository;
import com.example.api_Train.Repository.ThanhToanRepository;
import com.example.api_Train.Repository.TinhTrangVeRepository;
import com.example.api_Train.Repository.VeTauRepository;
import com.example.api_Train.Service.interf.ThanhToanService;
import com.example.api_Train.models.DatVe;
import com.example.api_Train.models.GheChuyenTau;
import com.example.api_Train.models.ThanhToan;
import com.example.api_Train.models.TinhTrangVe;
import com.example.api_Train.models.VeTau;

@Service
public class ThanhToanServiceImpl implements ThanhToanService {
    private static final Logger logger = Logger.getLogger(ThanhToanServiceImpl.class.getName());

    @Autowired
    private DatVeRepository datVeRepository;

    @Autowired
    private VeTauRepository veTauRepository;

    @Autowired
    private ThanhToanRepository thanhToanRepository;

    @Autowired
    private TinhTrangVeRepository tinhTrangVeRepository;

    @Autowired
    private GheChuyenTauRepository gheChuyenTauRepository;

    @Autowired
    private ChiTietDatVeRepository chiTietDatVeRepository;

    @Autowired
    private DatVeServiceImpl datVeService; // Để sử dụng phương thức createDatVeResponse

    @Transactional
    public DatVeResponse capNhatTrangThaiThanhToanThanhCong(Integer maDatVe) {
        try {
            // Tìm đơn đặt vé
            DatVe datVe = datVeRepository.findById(maDatVe)
                    .orElseThrow(() -> new NotFound("Không tìm thấy đơn đặt vé với mã: " + maDatVe));

            logger.info("Bắt đầu cập nhật trạng thái thanh toán cho đơn đặt vé: " + maDatVe);

            // Kiểm tra trạng thái hiện tại
            if (!"CHỜ THANH TOÁN".equals(datVe.getTrangThai())) {
                throw new IllegalStateException("Đơn đặt vé không ở trạng thái chờ thanh toán");
            }

            // Tìm tất cả vé tàu thuộc đơn đặt vé này
            List<VeTau> danhSachVe = chiTietDatVeRepository.findVeTauByMaDatVe(maDatVe);

            if (danhSachVe.isEmpty()) {
                throw new NotFound("Không tìm thấy vé tàu nào thuộc đơn đặt vé: " + maDatVe);
            }

            // Lấy thông tin tình trạng vé "Đã đặt"
            TinhTrangVe tinhTrangVeDaDat = tinhTrangVeRepository.findById(1) // Giả sử ID 1 là "Đã đặt"
                    .orElseThrow(() -> new NotFound("Không tìm thấy tình trạng vé với id = 1"));

            // Cập nhật trạng thái cho từng vé và ghế tương ứng
            for (VeTau veTau : danhSachVe) {
                // Cập nhật trạng thái vé tàu
                veTau.setTinhTrangVe(tinhTrangVeDaDat);
                veTauRepository.save(veTau);
                logger.info("Đã cập nhật trạng thái vé tàu: " + veTau.getMaVe());

                // Cập nhật trạng thái thanh toán cho vé tàu (quan hệ 1:1)
                if (veTau.getThanhToan() != null) {
                    ThanhToan thanhToan = veTau.getThanhToan();
                    thanhToan.setTrangThai("THANH TOÁN THÀNH CÔNG");
                    thanhToan.setNgayThanhToan(LocalDateTime.now());
                    thanhToanRepository.save(thanhToan);
                    logger.info("Đã cập nhật trạng thái thanh toán: " + thanhToan.getMaThanhToan());
                } else {
                    logger.warning("Vé tàu " + veTau.getMaVe() + " không có bản ghi thanh toán nào");
                }

                // Tìm và cập nhật trạng thái ghế trong chuyến tàu
                GheChuyenTau gheChuyenTau = gheChuyenTauRepository.findByChuyenTau_MaChuyenTauAndGhe_MaGhe(
                        veTau.getChuyenTau().getMaChuyenTau(),
                        veTau.getGhe().getMaGhe());

                if (gheChuyenTau == null) {
                    // Nếu chưa có bản ghi, tạo mới
                    gheChuyenTau = new GheChuyenTau();
                    gheChuyenTau.setGhe(veTau.getGhe());
                    gheChuyenTau.setChuyenTau(veTau.getChuyenTau());
                    logger.info("Tạo mới bản ghi ghế-chuyến tàu cho ghế " + veTau.getGhe().getMaGhe());
                }

                // Cập nhật trạng thái ghế
                gheChuyenTau.setTrangThai("ĐÃ ĐẶT");
                gheChuyenTauRepository.save(gheChuyenTau);
                logger.info("Đã cập nhật trạng thái ghế: " + veTau.getGhe().getMaGhe() +
                        " trong chuyến tàu: " + veTau.getChuyenTau().getMaChuyenTau());
            }

            // Cập nhật trạng thái đơn đặt vé
            datVe.setTrangThai("ĐÃ THANH TOÁN");
            datVe = datVeRepository.save(datVe);
            logger.info("Đã cập nhật trạng thái đơn đặt vé: " + datVe.getMaDatVe() + " thành ĐÃ THANH TOÁN");

            // Tạo response
            DatVeResponse response = datVeService.createDatVeResponse(datVe, danhSachVe);
            logger.info("Hoàn thành cập nhật trạng thái thanh toán cho đơn đặt vé: " + maDatVe);
            return response;

        } catch (NotFound e) {
            logger.severe("Lỗi không tìm thấy: " + e.getMessage());
            throw e;
        } catch (IllegalStateException e) {
            logger.severe("Lỗi trạng thái: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Lỗi trong quá trình cập nhật trạng thái thanh toán: " + e.getMessage());
            throw new RuntimeException("Lỗi trong quá trình cập nhật trạng thái thanh toán: " + e.getMessage(), e);
        }
    }

    @Transactional
    public DatVeResponse huyVe(Integer maDatVe) {
        try {
            // Tìm đơn đặt vé
            DatVe datVe = datVeRepository.findById(maDatVe)
                    .orElseThrow(() -> new NotFound("Không tìm thấy đơn đặt vé với mã: " + maDatVe));

            logger.info("Bắt đầu hủy vé cho đơn đặt vé: " + maDatVe);

            // Kiểm tra trạng thái hiện tại
            if (!"ĐÃ THANH TOÁN".equals(datVe.getTrangThai()) && !"CHỜ THANH TOÁN".equals(datVe.getTrangThai())) {
                throw new IllegalStateException("Không thể hủy đơn đặt vé với trạng thái: " + datVe.getTrangThai());
            }

            // Tìm tất cả vé tàu thuộc đơn đặt vé này
            List<VeTau> danhSachVe = chiTietDatVeRepository.findVeTauByMaDatVe(maDatVe);

            if (danhSachVe.isEmpty()) {
                throw new NotFound("Không tìm thấy vé tàu nào thuộc đơn đặt vé: " + maDatVe);
            }

            // Lấy thông tin tình trạng vé "Đã hủy"
            TinhTrangVe tinhTrangVeDaHuy = tinhTrangVeRepository.findById(3) // Giả sử ID 3 là "Đã hủy"
                    .orElseThrow(() -> new NotFound("Không tìm thấy tình trạng vé với id = 3"));

            // Cập nhật trạng thái cho từng vé và ghế tương ứng
            for (VeTau veTau : danhSachVe) {
                // Cập nhật trạng thái vé tàu
                veTau.setTinhTrangVe(tinhTrangVeDaHuy);
                veTauRepository.save(veTau);
                logger.info("Đã cập nhật trạng thái vé tàu: " + veTau.getMaVe() + " thành ĐÃ HỦY");

                // Cập nhật trạng thái thanh toán cho vé tàu (quan hệ 1:1)
                if (veTau.getThanhToan() != null) {
                    ThanhToan thanhToan = veTau.getThanhToan();
                    thanhToan.setTrangThai("ĐÃ HỦY");
                    thanhToanRepository.save(thanhToan);
                    logger.info("Đã cập nhật trạng thái thanh toán: " + thanhToan.getMaThanhToan() + " thành ĐÃ HỦY");
                }

                // Tìm và cập nhật trạng thái ghế trong chuyến tàu
                GheChuyenTau gheChuyenTau = gheChuyenTauRepository.findByChuyenTau_MaChuyenTauAndGhe_MaGhe(
                        veTau.getChuyenTau().getMaChuyenTau(),
                        veTau.getGhe().getMaGhe());

                if (gheChuyenTau != null) {
                    // Đặt lại trạng thái ghế thành TRỐNG
                    gheChuyenTau.setTrangThai("TRỐNG");
                    gheChuyenTauRepository.save(gheChuyenTau);
                    logger.info("Đã cập nhật trạng thái ghế: " + veTau.getGhe().getMaGhe() +
                            " trong chuyến tàu: " + veTau.getChuyenTau().getMaChuyenTau() + " thành TRỐNG");
                } else {
                    logger.warning("Không tìm thấy thông tin ghế-chuyến tàu cho ghế " +
                            veTau.getGhe().getMaGhe() + " trong chuyến tàu " +
                            veTau.getChuyenTau().getMaChuyenTau());
                }
            }

            // Cập nhật trạng thái đơn đặt vé
            datVe.setTrangThai("ĐÃ HỦY");
            datVe = datVeRepository.save(datVe);
            logger.info("Đã cập nhật trạng thái đơn đặt vé: " + datVe.getMaDatVe() + " thành ĐÃ HỦY");

            // Tạo response
            DatVeResponse response = datVeService.createDatVeResponse(datVe, danhSachVe);
            logger.info("Hoàn thành hủy vé cho đơn đặt vé: " + maDatVe);
            return response;

        } catch (NotFound e) {
            logger.severe("Lỗi không tìm thấy: " + e.getMessage());
            throw e;
        } catch (IllegalStateException e) {
            logger.severe("Lỗi trạng thái: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.severe("Lỗi trong quá trình hủy vé: " + e.getMessage());
            throw new RuntimeException("Lỗi trong quá trình hủy vé: " + e.getMessage(), e);
        }
    }
}