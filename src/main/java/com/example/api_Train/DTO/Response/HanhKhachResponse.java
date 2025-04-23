package com.example.api_Train.DTO.Response;

import com.example.api_Train.models.HanhKhach;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HanhKhachResponse {
    private Integer maHanhKhach;
    private String hoTen;
    private String soGiayTo;
    private String ngaySinh;
    private String tenLoaiKhach;

    public static HanhKhachResponse mapHanhKhachResponse(HanhKhach hanhKhach) {
        return HanhKhachResponse.builder()
                .maHanhKhach(hanhKhach.getMaHanhKhach())
                .hoTen(hanhKhach.getHoTen())
                .soGiayTo(hanhKhach.getSoGiayTo())
                .ngaySinh(hanhKhach.getNgaySinh().toString())
                .tenLoaiKhach(hanhKhach.getLoaiKhach().getTenLoaiKhach())
                .build();
    }
}
