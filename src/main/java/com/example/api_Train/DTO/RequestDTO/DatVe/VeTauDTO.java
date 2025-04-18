package com.example.api_Train.DTO.RequestDTO.DatVe;

import java.math.BigDecimal;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VeTauDTO {
    private Integer maKhachHang;
    private Integer maLoaiKhach;
    private Integer maChuyenTau;;
    private Integer maGhe;
    private BigDecimal maGia;
    private BigDecimal giaVe;
    private String trangThai;
}
