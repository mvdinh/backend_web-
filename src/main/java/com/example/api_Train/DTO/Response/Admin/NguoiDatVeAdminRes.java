package com.example.api_Train.DTO.Response.Admin;

import com.example.api_Train.DTO.Response.NguoiDatVeResponse;
import com.example.api_Train.models.NguoiDatVe;

public class NguoiDatVeAdminRes {
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
