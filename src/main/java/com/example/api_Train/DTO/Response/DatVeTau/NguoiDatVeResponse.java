package com.example.api_Train.DTO.Response.DatVeTau;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.HanhKhach;
import com.example.api_Train.models.NguoiDatVe;
import com.example.api_Train.models.ToaTau;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.BangGia;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class NguoiDatVeResponse {
    private Integer maNguoiDat;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String cccd;

    public static NguoiDatVeResponse mapNguoiDatVeResponse(NguoiDatVe NguoiDatVe) {
        return NguoiDatVeResponse.builder()
                .maNguoiDat(NguoiDatVe.getMaNguoiDat())
                .hoTen(NguoiDatVe.getHoTen())
                .email(NguoiDatVe.getEmail())
                .soDienThoai(NguoiDatVe.getSoDienThoai())
                .cccd(NguoiDatVe.getCccd())
                .build();
    }
}
