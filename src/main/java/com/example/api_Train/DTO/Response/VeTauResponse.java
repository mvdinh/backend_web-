package com.example.api_Train.DTO.Response;

import java.math.BigDecimal;

import com.example.api_Train.models.VeTau;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeTauResponse {
    private Integer maVe;
    private Integer chuyenTau;
    private String soGhe;
    private String trangThai;

    public static VeTauResponse fromVeTau(VeTau veTau) {
        return VeTauResponse.builder()
                .maVe(veTau.getMaVe())
                .chuyenTau(veTau.getChuyenTau().getMaChuyenTau())
                .soGhe(veTau.getGhe().getTenGhe())
                .trangThai(veTau.getTinhTrangVe().getTinhTrangVe())
                .build();
    }
}
