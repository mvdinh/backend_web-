// ChuyenTauRequest.java
package com.example.api_Train.DTO.Request;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChuyenTauRequest {
    private Integer maTau;
    private Integer maTuyen;
    private LocalDateTime ngayGioKhoiHanh;
    private List<BangGiaRequest> danhSachBangGia;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BangGiaRequest {
        private Integer maLoaiCho;
        private java.math.BigDecimal giaTien;
    }
}
