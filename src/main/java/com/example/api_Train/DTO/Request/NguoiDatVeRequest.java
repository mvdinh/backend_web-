package com.example.api_Train.DTO.Request;

import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NguoiDatVeRequest {
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