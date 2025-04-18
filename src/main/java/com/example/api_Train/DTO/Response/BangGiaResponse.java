package com.example.api_Train.DTO.Response;

import java.math.BigDecimal;
import lombok.*;
import com.example.api_Train.models.BangGia;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BangGiaResponse {
    private Integer maChuyenTau;
    private String tenLoaiCho;
    private String tenTau;
    private BigDecimal giaTien;

    public static BangGiaResponse fromBangGiaResponse(BangGia bangGia) {
        return BangGiaResponse.builder()
                .maChuyenTau(bangGia.getChuyenTau().getMaChuyenTau())
                .tenLoaiCho(bangGia.getLoaiCho().getTenLoaiCho())
                .tenTau(bangGia.getChuyenTau().getTau().getTenTau())
                .giaTien(bangGia.getGiaTien()).build();
    }
}
