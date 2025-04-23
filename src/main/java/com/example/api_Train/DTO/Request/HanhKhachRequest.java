package com.example.api_Train.DTO.Request;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HanhKhachRequest {
    @NotBlank(message = "Họ tên hành khách không được để trống")
    private String hoTen;

    @NotBlank(message = "Số giấy tờ không được để trống")
    private String soGiayTo;

    @Past(message = "Ngày sinh phải trong quá khứ")
    private Date ngaySinh;

    @Min(value = 1, message = "Mã loại khách không hợp lệ")
    private Integer maLoaiKhach;
}
