package com.example.api_Train.DTO.RequestDTO.DatVe;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatVeRequest {
    @NotNull(message = "Thông tin người đặt vé không được để trống")
    private NguoiDatVeRequest nguoiDatVe;

    @NotEmpty(message = "Danh sách vé đặt không được để trống")
    private List<ChiTietDatVeRequest> chiTietDatVe;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NguoiDatVeRequest {
        @NotBlank(message = "Họ tên không được để trống")
        @Size(max = 100, message = "Họ tên không quá 100 ký tự")
        private String hoTen;

        @Email(message = "Email không hợp lệ")
        private String email;

        @Pattern(regexp = "\\d{10,11}", message = "Số điện thoại phải có 10-11 chữ số")
        private String soDienThoai;

        @Pattern(regexp = "\\d{9,12}", message = "CCCD phải có 9-12 chữ số")
        private String cccd;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChiTietDatVeRequest {
        @NotNull(message = "Thông tin hành khách không được để trống")
        private HanhKhachRequest hanhKhach;

        @NotNull(message = "Thông tin vé tàu không được để trống")
        private VeTauRequest veTau;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HanhKhachRequest {
        @NotBlank(message = "Họ tên hành khách không được để trống")
        private String hoTen;

        @NotBlank(message = "Số giấy tờ không được để trống")
        private String soGiayTo;

        @Past(message = "Ngày sinh phải trong quá khứ")
        private LocalDate ngaySinh;

        @Min(value = 1, message = "Mã loại khách không hợp lệ")
        private Integer maLoaiKhach;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VeTauRequest {
        private Integer maKhachHang;

        @Min(value = 1, message = "Mã loại khách không hợp lệ")
        private Integer maLoaiKhach;

        @NotNull(message = "Mã chuyến tàu không được để trống")
        private Integer maChuyenTau;

        @NotNull(message = "Mã ghế không được để trống")
        private Integer maGhe;

        private BigDecimal maGia;

        @PositiveOrZero(message = "Giá vé phải là số dương")
        private BigDecimal giaVe;

        private String trangThai;
    }
}