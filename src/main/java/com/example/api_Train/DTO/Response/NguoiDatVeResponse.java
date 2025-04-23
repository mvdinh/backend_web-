package com.example.api_Train.DTO.Response;

import com.example.api_Train.models.NguoiDatVe;

import lombok.*;

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