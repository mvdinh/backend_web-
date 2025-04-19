package com.example.api_Train.DTO.Response.DatVeTau;

import java.math.BigDecimal;

import com.example.api_Train.DTO.Response.TuyenDuongResponse;
import com.example.api_Train.models.VeTau;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeTauResponse {
    private Integer maVe;
    private ChuyenTauRes chuyenTau;
    private HanhKhachResponse hanhKhach;
    private String soGhe;
    private BigDecimal giaVe;
    private String trangThai;

    public static VeTauResponse fromTuyenDuongResponse(VeTau veTau) {
        return VeTauResponse.builder()
                .maVe(veTau.getMaVe())
                .chuyenTau(ChuyenTauRes.mapChuyenTauResponse(veTau.getChuyenTau()))
                .hanhKhach(HanhKhachResponse.mapHanhKhachResponse(veTau.getHanhKhach()))
                .soGhe(veTau.getGhe().getTenGhe())
                .trangThai(veTau.getTinhTrangVe().getTinhTrangVe())
                .build();
    }
}
