package com.example.api_Train.DTO.Response.DatVeTau;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.api_Train.models.ChuyenTau;
import com.example.api_Train.models.HanhKhach;
import com.example.api_Train.models.ToaTau;
import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.BangGia;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HanhKhachResponse {
    private Integer maHanhKhach;
    private String hoTen;
    private String soGiayTo;
    private String ngaySinh;

    public static HanhKhachResponse mapHanhKhachResponse(HanhKhach hanhKhach) {
        return HanhKhachResponse.builder()
                .hoTen(hanhKhach.getHoTen())
                .soGiayTo(hanhKhach.getSoGiayTo())
                .ngaySinh(hanhKhach.getNgaySinh().toString())
                .build();
    }
}
